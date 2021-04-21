package com.aqinn.actmanagersys.mobile.selfattend;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
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

    private static final String TAG = "SelfAttendPresenter";

    private ISelfAttend.View mView;
    private ISelfAttend.Model mModel;

    private Long mAttendId;

    // 人脸识别模型
    private FaceRecognize mFaceRecognize;

    // 推理线程相关
    private HandlerThread mInferThread;
    private Handler mInferHandler;
    private HandlerThread mNetworkThread;
    private Handler mNetworkHandler;
    private final Object lock = new Object();  // 开关推理线程 - 同步变量
    private boolean isInfering = false;
    private float[] nowFaceFeature;
    private float[] preFaceFeature = null;

    // 画面呈现相关
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private AutoFitTextureView mTextureView;

    // 画笔相关 - 绘制人脸检测框
    private static Paint rectPaint = new Paint();
    private static Paint pointPaint = new Paint();
    private static Paint minRecPaint = new Paint();
    private static Paint maxRecPaint = new Paint();

    // 画笔相关 - 绘制人脸检测框
    static {
        rectPaint.setColor(Color.GREEN);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(4);
        pointPaint.setColor(Color.GREEN);
        pointPaint.setStyle(Paint.Style.FILL);

        minRecPaint.setColor(Color.YELLOW);
        minRecPaint.setStrokeWidth(4);
        minRecPaint.setStyle(Paint.Style.STROKE);

        maxRecPaint.setColor(Color.RED);
        maxRecPaint.setStrokeWidth(4);
        maxRecPaint.setStyle(Paint.Style.STROKE);
    }

    public SelfAttendPresenter(ISelfAttend.View view, Long attendId) {
        mView = view;
        mAttendId = attendId;
        mModel = new SelfAttendModel();
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
        // TODO 人脸识别和网络请求线程也放这里好了。但是为了最高性能，人脸识别与网络请求应该分开两个线程
        mNetworkThread = new HandlerThread("recognizeAndNetwork");
        mNetworkThread.start();
        mNetworkHandler = new Handler(mNetworkThread.getLooper());
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
                drawRectBySurface(null);
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
                drawRectBySurface(faceInfos);
                mNetworkHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // TODO 人脸识别、比对、网络请求，签到业务，回调 mView.setResult("");
                        nowFaceFeature = mFaceRecognize.recognize(ParseUtil.getPixelsRGBA(bitmap), mTextureView.getWidth(), mTextureView.getHeight(), ParseUtil.getUsefulLandmarksFromFaceInfo(faceInfos[0]));
                        if (preFaceFeature != null) {
                            double distance = mFaceRecognize.compare(preFaceFeature, nowFaceFeature);
                            // 判定为同一个人
                            if (distance >= PublicConfig.SAME_FACE_THRESHOLD) {
                                preFaceFeature = nowFaceFeature;
                                return;
                            }
                        }
                        mModel.selfAttend(mAttendId, ParseUtil.arr2String(nowFaceFeature), new ISelfAttend.Model.SelfAttendCallback() {
                            @Override
                            public void onSuccess() {
                                // TODO 显示结果
                            }

                            @Override
                            public void onError() {

                            }
                        });
                        preFaceFeature = nowFaceFeature;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绘制人脸检测框
     *
     * @param faceInfos
     */
    private void drawRectBySurface(FaceInfo[] faceInfos) {
        if (faceInfos == null) {
            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); //清楚掉上一次的画框。
            mSurfaceHolder.unlockCanvasAndPost(canvas);
            return;
        }
        Canvas canvas = mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); //清楚掉上一次的画框。
        for (int i = 0; i < faceInfos.length; i++) {
            canvas.drawRect(faceInfos[i].x1, faceInfos[i].y1, faceInfos[i].x2, faceInfos[i].y2, rectPaint);
            for (int j = 0; j < 5; j++) {
                canvas.drawCircle(faceInfos[i].keypoints[j][0], faceInfos[i].keypoints[j][1], 6f, pointPaint);
            }
        }
        mSurfaceHolder.unlockCanvasAndPost(canvas);
        return;
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
