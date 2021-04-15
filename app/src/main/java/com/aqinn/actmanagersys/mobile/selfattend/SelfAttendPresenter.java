package com.aqinn.actmanagersys.mobile.selfattend;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.facecollect.IFaceCollect;
import com.aqinn.actmanagersys.mobile.myview.AutoFitTextureView;
import com.aqinn.actmanagersys.mobile.utils.FileUtil;
import com.aqinn.actmanagersys.mobile.utils.ParseUtil;
import com.aqinn.facerecognizencnn.FaceInfo;
import com.aqinn.facerecognizencnn.FaceRecognize;

import java.io.File;

/**
 * 自助签到 - Presenter
 *
 * @author Aqinn
 * @date 2021/4/7 11:19 AM
 */
public class SelfAttendPresenter implements ISelfAttend.Presenter {

    private static final String TAG = "VideoAttendPresenter";

    private ISelfAttend.View mView;

    // 人脸识别模型
    private FaceRecognize mFaceRecognize;

    // 推理线程相关
    private HandlerThread mInferThread;
    private Handler mInferHandler;
    private final Object lock = new Object();  // 开关推理线程 - 同步变量
    private boolean isInfering = false;
    private float[] nowFaceFeature;

    // 画面呈现相关
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private AutoFitTextureView mTextureView;

    public SelfAttendPresenter(ISelfAttend.View view) {
        mView = view;
        mTextureView = mView.getTextureView();
    }

    @Override
    public void initModel(Activity activity) {
        // 人脸检测模型初始化
        mFaceRecognize = new FaceRecognize();
        mFaceRecognize.initRetainFace(activity.getAssets());
        // 拷贝人脸识别模型到sd卡
        String sdPath = activity.getCacheDir().getAbsolutePath() + "/facem/";
        FileUtil.copyFileFromAsset(activity, "mobilefacenet.bin", sdPath + File.separator + "mobilefacenet.bin");
        FileUtil.copyFileFromAsset(activity, "mobilefacenet.param", sdPath + File.separator + "mobilefacenet.param");
        // 人脸识别模型初始化
        mFaceRecognize.initMobileFacenet(sdPath);
    }

    @Override
    public void initSurfaceView(SurfaceView surfaceView) {
        mSurfaceView = surfaceView;
        // 设置 SurfaceView 处于顶层以及设为透明
        mSurfaceView.setZOrderOnTop(true);  // 处于顶层
        mSurfaceView.getHolder().setFormat(PixelFormat.TRANSPARENT);  // 设置surface为透明
        // 获取 SurfaceView 的 Handler
        mSurfaceHolder = mSurfaceView.getHolder();
    }

    @Override
    public void startInferThread() {
        mInferThread = new HandlerThread("inference");
        mInferThread.start();
        mInferHandler = new Handler(mInferThread.getLooper());
        synchronized (lock) {
            isInfering = true;
        }
        mInferHandler.post(periodicInfer);
    }

    // 推理线程需要做的事情
    private Runnable periodicInfer =
            new Runnable() {
                @Override
                public void run() {
                    synchronized (lock) {
                        if (isInfering) {
                            // 开始预测前要判断相机是否已经准备好
                            if (mView.isCameraPrepare()) {
                                predict();
                            }
                        }
                    }
                    if (mView.isAttendAble()) {
                        mInferHandler.post(periodicInfer);  // 相当于回调自身，递归调用自身
                    }
                }
            };

    // 推理
    private void predict() {
        // 获取相机捕获的图像
        Bitmap bitmap = mTextureView.getBitmap();
        try {
            if (PublicConfig.isDebug)
                Log.d(TAG, "predict: 用以预测的 Bitmap 尺寸（未Resize） w:" + bitmap.getWidth() + ", h:" + bitmap.getHeight());
            float[][] result = mFaceRecognize.detectTest(bitmap, bitmap.getWidth(), bitmap.getHeight(), 3);
            if (result == null) {
                return;
            }
            if (PublicConfig.isDebug)
                Log.d(TAG, "predict: 检测到几张人脸？result.length => " + result.length);
            if (result.length != 0) {
                FaceInfo faceInfos[] = new FaceInfo[result.length];
                for (int i = 0; i < faceInfos.length; i++) {
                    FaceInfo faceInfo = ParseUtil.floatArr2FaceInfo(result[i]);
                    faceInfos[i] = faceInfo;
                }
                nowFaceFeature = mFaceRecognize.recognize(ParseUtil.getPixelsRGBA(bitmap), mTextureView.getWidth(), mTextureView.getHeight(), ParseUtil.getUsefulLandmarksFromFaceInfo(faceInfos[0]));
                // TODO 网络请求，签到业务，回调 mView.setResult("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭推理线程
     */
    @Override
    public void stopInferThread() {
        try {
            if (mInferThread != null) {
                mInferThread.quitSafely();
                mInferThread.join();
            }
            mInferThread = null;
            mInferHandler = null;
            synchronized (lock) {
                isInfering = false;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isInferAble() {
        return mInferThread != null && mInferHandler != null;
    }

}
