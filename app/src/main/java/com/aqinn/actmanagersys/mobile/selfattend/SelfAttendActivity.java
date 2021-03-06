package com.aqinn.actmanagersys.mobile.selfattend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.camera.CameraOperatorPresenter;
import com.aqinn.actmanagersys.mobile.camera.ICameraOperator;
import com.aqinn.actmanagersys.mobile.myview.AutoFitTextureView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自助签到 - View
 *
 * @author Aqinn
 * @date 2021/4/7 10:58 AM
 */
public class SelfAttendActivity extends BaseFragmentActivity implements ISelfAttend.View, ICameraOperator.View {

    @BindView(R.id.texture_view)
    AutoFitTextureView textureView;
    @BindView(R.id.surfaceview)
    SurfaceView surfaceview;
    @BindView(R.id.preview_layout)
    RelativeLayout previewLayout;
    @BindView(R.id.tv_res)
    TextView tvRes;
    @BindView(R.id.tv_tips)
    TextView tvTips;

    private ISelfAttend.Presenter mSelfAttendPresenter;
    private ICameraOperator.Presenter mCameraPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_attend);
        Intent intent = getIntent();
        long attendId = intent.getLongExtra("attendId", -1L);
        if (attendId == -1) {
            if (PublicConfig.isDebug)
                Toast.makeText(this, "Test: 进入自助签到失败，没有传入签到ID", Toast.LENGTH_SHORT).show();
            finish();
        }
        ButterKnife.bind(this);
        mCameraPresenter = new CameraOperatorPresenter(this);
        mSelfAttendPresenter = new SelfAttendPresenter(this, attendId);
        initView();
        initModel();
    }

    private void initView() {
        mSelfAttendPresenter.initSurfaceView(surfaceview);
    }

    private void initModel() {
        mSelfAttendPresenter.initModel(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        prepareAttend();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAttend();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopAttend();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAttend();
    }


    private void prepareAttend() {
        mCameraPresenter.startCaptureThread();
        mSelfAttendPresenter.startInferThread();
        mCameraPresenter.openCamera();
    }

    private void stopAttend() {
        mCameraPresenter.closeCamera();
        mSelfAttendPresenter.stopInferThread();
        mCameraPresenter.stopCaptureThread();
    }

    @Override
    public void setResult(String msg) {
        tvRes.setText(msg);
    }

    @Override
    public boolean isCameraPrepare() {
        return mCameraPresenter.isCameraPrepare(getApplicationContext());
    }

    @Override
    public boolean isAttendAble() {
        if (mSelfAttendPresenter == null || mCameraPresenter == null)
            return false;
        return mSelfAttendPresenter.isInferAble() && mCameraPresenter.isCaptureAble();
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
