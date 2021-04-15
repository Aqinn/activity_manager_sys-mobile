package com.aqinn.actmanagersys.mobile.videoattend;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.myview.AutoFitTextureView;
import com.aqinn.actmanagersys.mobile.utils.FileUtil;
import com.aqinn.actmanagersys.mobile.utils.ParseUtil;
import com.aqinn.facerecognizencnn.FaceInfo;
import com.aqinn.facerecognizencnn.FaceRecognize;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 视频签到 - Presenter
 *
 * @author Aqinn
 * @date 2021/4/7 11:19 AM
 */
public class VideoAttendPresenter implements IVideoAttend.Presenter {

    private static final String TAG = "VideoAttendPresenter";

    private IVideoAttend.View mView;

    // 人脸识别模型
    private FaceRecognize mFaceRecognize;
    private volatile float preFinishRecognizeFeature[] = null;  // 每一帧人脸特征向量与上一条成功识别的人脸特征向量对比，不是同一个人的才发送使用，降低网络压力
    private double threshold = 0.5;

    // 推理线程相关
    private HandlerThread mInferThread;
    private Handler mInferHandler;
    private final Object lock = new Object();  // 开关推理线程 - 同步变量
    private boolean isInfering = false;

    // 画面呈现相关
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private AutoFitTextureView mTextureView;

    // 签到结果相关
    private RecyclerView mRecyclerView;
    private AttendedAdapter mAdapter;
    private List<String> mData;

    public VideoAttendPresenter(IVideoAttend.View view) {
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
    public void initRecyclerView(Activity context, RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mData = new ArrayList<>();
        mAdapter = new AttendedAdapter(context, mData);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
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
                float[] nowFaceFeature = mFaceRecognize.recognize(ParseUtil.getPixelsRGBA(bitmap), mTextureView.getWidth(), mTextureView.getHeight(), ParseUtil.getUsefulLandmarksFromFaceInfo(faceInfos[0]));
                // 多次发起请求，验证检测到的人脸身份。制造条件判断以减缓网络压力
                if (preFinishRecognizeFeature == null || threshold > mFaceRecognize.compare(nowFaceFeature, preFinishRecognizeFeature)) {
                    Message msg = Message.obtain();
                    msg.obj = ParseUtil.arr2String(nowFaceFeature);
                    // TODO 通过网络请求发送到后台 mNetworkHandler.sendMessage(msg); 并通过 ParseUtil.string2Arr(tempFeature); 设置 preFinishRecognizeFeature 然后添加到 RecyclerView 中展示
                }
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
