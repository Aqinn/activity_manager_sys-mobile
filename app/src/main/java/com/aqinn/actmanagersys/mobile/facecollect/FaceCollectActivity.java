package com.aqinn.actmanagersys.mobile.facecollect;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.camera.CameraOperatorPresenter;
import com.aqinn.actmanagersys.mobile.camera.ICameraOperator;
import com.aqinn.actmanagersys.mobile.myview.AutoFitTextureView;
import com.aqinn.facerecognizencnn.FaceRecognize;
import com.qmuiteam.qmui.widget.QMUIProgressBar;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 人脸采集 - View
 *
 * @author Aqinn
 * @date 2021/4/6 11:08 AM
 */
public class FaceCollectActivity extends BaseFragmentActivity implements IFaceCollect.View, ICameraOperator.View {

    @BindView(R.id.texture_view)
    AutoFitTextureView textureView;
    @BindView(R.id.surfaceview)
    SurfaceView surfaceview;
    @BindView(R.id.circleProgressBar)
    QMUIProgressBar circleProgressBar;
    @BindView(R.id.preview_layout)
    RelativeLayout previewLayout;
    @BindView(R.id.tv_res)
    TextView tvRes;
    @BindView(R.id.tv_tips)
    TextView tvTips;

    private IFaceCollect.Presenter mFaceCollectPresenter;
    private ICameraOperator.Presenter mCameraPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_collect);
        ButterKnife.bind(this);
        mFaceCollectPresenter = new FaceCollectPresenter(this);
        mCameraPresenter = new CameraOperatorPresenter(this);
        initView();
        initModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        prepareCollect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopCollect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopCollect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopCollect();
    }

    private void initView() {
        mFaceCollectPresenter.initProgressBar(this, circleProgressBar);
        mFaceCollectPresenter.initSurfaceView(surfaceview);
    }

    private void initModel() {
        mFaceCollectPresenter.initModel(this);
    }

    private void prepareCollect(){
        mCameraPresenter.startCaptureThread();
        mFaceCollectPresenter.startInferThread();
        mCameraPresenter.openCamera();
    }

    private void stopCollect() {
        mCameraPresenter.closeCamera();
        mFaceCollectPresenter.stopInferThread();
        mCameraPresenter.stopCaptureThread();
    }

    @Override
    public void setResult(String msg) {
        tvRes.setText(msg);
    }

    @Override
    public boolean isCollectAble() {
        if (mFaceCollectPresenter == null || mCameraPresenter == null)
            return false;
        return mFaceCollectPresenter.isInferAble() && mCameraPresenter.isCaptureAble();
    }

    @Override
    public boolean isCameraPrepare() {
        return mCameraPresenter.isCameraPrepare(getApplicationContext());
    }

    @Override
    public AutoFitTextureView getTextureView() {
        return textureView;
    }

    @Override
    public Activity getContext() {
        return this;
    }

}
