package com.aqinn.actmanagersys.mobile.facecollect;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseApplication;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.myview.AutoFitTextureView;
import com.aqinn.actmanagersys.mobile.utils.FileUtil;
import com.aqinn.actmanagersys.mobile.utils.ParseUtil;
import com.aqinn.facerecognizencnn.FaceInfo;
import com.aqinn.facerecognizencnn.FaceRecognize;
import com.qmuiteam.qmui.widget.QMUIProgressBar;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * 人脸采集 - Presenter
 *
 * @author Aqinn
 * @date 2021/4/6 11:43 AM
 */
public class FaceCollectPresenter implements IFaceCollect.Presenter {

    private static final String TAG = "FaceCollectPresenter";

    private IFaceCollect.View mView;
    private IFaceCollect.Model mModel;

    private Handler mHandler = new Handler();

    // 人脸识别模型
    private FaceRecognize mFaceRecognize;
    private Integer recognizeCount = 0;
    private float faceFeatures[][] = new float[4][128];
    private float nowFaceFeature[];

    // 推理线程相关
    private HandlerThread mInferThread;
    private Handler mInferHandler;
    private final Object lock = new Object();  // 开关推理线程 - 同步变量
    private boolean isInfering = false;

    // 圆形进度条相关
    private QMUIProgressBar mProgressBar;
    private ProgressHandler progressHandler = new ProgressHandler();
    protected static final int STOP = 1 << 0;
    protected static final int NEXT = 1 << 1;
    private static int progressCount = 0;

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

    public FaceCollectPresenter(IFaceCollect.View view) {
        mView = view;
        mModel = new FaceCollectModel();
        mTextureView = mView.getTextureView();
    }

    @Override
    public void initProgressBar(Activity activity, QMUIProgressBar circleProgressBar) {
        progressCount = 0;
        mProgressBar = circleProgressBar;
        progressHandler.setProgressBar(mProgressBar);
        circleProgressBar.setOnProgressChangeListener(new QMUIProgressBar.OnProgressChangeListener() {
            @Override
            public void onProgressChange(QMUIProgressBar progressBar, int currentValue, int maxValue) {
                if (progressCount == 0) {
                    mView.setResult(activity.getResources().getString(R.string.tips_face_collect_ing_1));
                    synchronized (recognizeCount) {
                        recognizeCount = 0;
                        faceFeatures = new float[4][128];
                    }
                    return;
                }
                if (currentValue < 40) {
                    mView.setResult(activity.getResources().getString(R.string.tips_face_collect_ing_2));
                } else if (currentValue < 75) {
                    mView.setResult(activity.getResources().getString(R.string.tips_face_collect_ing_3));
                } else if (currentValue >= 100) {
                    mView.setResult(activity.getResources().getString(R.string.tips_face_collect_finish));
                    onFaceCollectFinish();
                }

                if (currentValue >= 100 || recognizeCount >= 4)
                    return;
                synchronized (recognizeCount) {
                    if (currentValue == 20 || currentValue == 40 || currentValue == 60 || currentValue == 80) {
                        // 人脸特征向量存起来
                        faceFeatures[recognizeCount++] = nowFaceFeature;
                    }
                }
            }
        });
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

    private void onFaceCollectFinish() {
        Toast.makeText(BaseApplication.getContext(), "人脸采集完成，开始发送网络请求", Toast.LENGTH_SHORT).show();
        mModel.faceCollect(
                ParseUtil.arr2String(faceFeatures[0]),
                ParseUtil.arr2String(faceFeatures[1]),
                ParseUtil.arr2String(faceFeatures[2]),
                ParseUtil.arr2String(faceFeatures[3]),
                new IFaceCollect.Model.FaceCollectCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(BaseApplication.getContext(), "人脸采集成功，两秒后退出", Toast.LENGTH_SHORT).show();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mView.popbackstack();
                            }
                        }, 2000);
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(BaseApplication.getContext(), "人脸采集失败，两秒后退出", Toast.LENGTH_SHORT).show();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mView.popbackstack();
                            }
                        }, 2000);
                    }
                });
        // 停止推理线程
        stopInferThread();
        // 清理人脸检测框
        Canvas canvas = mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); //清楚掉上一次的画框。
        mSurfaceHolder.unlockCanvasAndPost(canvas);
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

    /**
     * 启动预测线程
     */
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
                    if (mView.isCollectAble()) {
                        mInferHandler.post(periodicInfer);  // 相当于回调自身，递归调用自身
                    }
                }
            };

    private void predict() {
        // 获取相机捕获的图像
        Bitmap bitmap = mTextureView.getBitmap();
        try {
            if (PublicConfig.isDebug)
                Log.d(TAG, "predict: 用以预测的 Bitmap 尺寸（未Resize） w:" + bitmap.getWidth() + ", h:" + bitmap.getHeight());
            float[][] result = mFaceRecognize.detectTest(bitmap, bitmap.getWidth(), bitmap.getHeight(), 3);
            if (result == null) {
                drawRectBySurface(null);
                if (progressCount < 100) {
                    mProgressBar.setProgress(0);
                    progressCount = 0;
                }
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
                if (PublicConfig.isDebug)
                    drawRectBySurface(faceInfos);
                nowFaceFeature = mFaceRecognize.recognize(ParseUtil.getPixelsRGBA(bitmap), mTextureView.getWidth(), mTextureView.getHeight(), ParseUtil.getUsefulLandmarksFromFaceInfo(faceInfos[0]));
                if (progressCount < 100) {
                    boolean isRightLocation = verifyFaceLocation(faceInfos[0]);
                    if (isRightLocation) {
                        mProgressBar.setProgress(++progressCount);
                    } else {
                        mProgressBar.setProgress(0);
                        progressCount = 0;
                    }
                }
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
     * 验证检测出的人脸是否在限制的区域内
     *
     * @param faceInfo
     * @return
     */
    private boolean verifyFaceLocation(FaceInfo faceInfo) {
        int left = (int) faceInfo.x1, top = (int) faceInfo.y1, right = (int) faceInfo.x2, bottom = (int) faceInfo.y2;
        float sqrt2 = 1.4142135f;
        int stroke = 18;
        int radius = mProgressBar.getRight() - mProgressBar.getLeft() - 2 * stroke;
        int squareSide = (int) (radius * sqrt2);
        int errorValue = (mProgressBar.getRight() - mProgressBar.getLeft() - squareSide) / 2;

        int diagonalLeft = mProgressBar.getLeft() - errorValue;
        int diagonalRight = mProgressBar.getRight() + errorValue;
        int diagonalTop = mProgressBar.getTop() - errorValue;
        int diagonalBottom = mProgressBar.getBottom() + errorValue;

        int hopeMinBottom = (diagonalBottom + mProgressBar.getBottom()) / 2 - 120;
        int hopeMaxBottom = mProgressBar.getBottom() + 5;

        int hopeMinTop = diagonalTop + 80;
        int hopeMaxTop = (int) ((mProgressBar.getTop() + diagonalTop) / 2 - (diagonalTop - mProgressBar.getTop()) / 6);

        int hopeMinLeft = diagonalLeft + (diagonalLeft - mProgressBar.getLeft()) / 6 + 80;
        int hopeMaxLeft = diagonalLeft - 30;

        int hopeMinRight = diagonalRight - (mProgressBar.getRight() - diagonalRight) / 6 - 80;
        int hopeMaxRight = diagonalRight + 30;

        boolean isVerify = true;

        if (!(hopeMaxLeft <= left && left <= hopeMinLeft))
            isVerify = false;
        if (!(hopeMaxTop <= top && top <= hopeMinTop))
            isVerify = false;
        if (!(hopeMinRight <= right && right <= hopeMaxRight))
            isVerify = false;
        if (!(hopeMinBottom <= bottom && bottom <= hopeMaxBottom))
            isVerify = false;

        return isVerify;
    }

    /**
     * 关闭预测线程
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

    // 处理圆形进度条
    private static class ProgressHandler extends Handler {
        private WeakReference<QMUIProgressBar> weakCircleProgressBar;

        void setProgressBar(QMUIProgressBar circleProgressBar) {
            weakCircleProgressBar = new WeakReference<>(circleProgressBar);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case STOP:
                    break;
                case NEXT:
                    if (!Thread.currentThread().isInterrupted()) {
                        if (weakCircleProgressBar.get() != null) {
                            weakCircleProgressBar.get().setProgress(msg.arg1);
                        }
                    }
            }
        }
    }

}
