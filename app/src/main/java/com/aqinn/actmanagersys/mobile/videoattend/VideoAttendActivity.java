package com.aqinn.actmanagersys.mobile.videoattend;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.camera.CameraOperatorPresenter;
import com.aqinn.actmanagersys.mobile.camera.ICameraOperator;
import com.aqinn.actmanagersys.mobile.myview.AutoFitTextureView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 视频签到 - View
 *
 * @author Aqinn
 * @date 2021/4/7 10:58 AM
 */
public class VideoAttendActivity extends BaseFragmentActivity implements IVideoAttend.View, ICameraOperator.View {

    private static final String TAG = "VideoAttendActivity";

    @BindView(R.id.texture_view)
    AutoFitTextureView textureView;
    @BindView(R.id.surfaceview)
    SurfaceView surfaceview;
    @BindView(R.id.preview_layout)
    RelativeLayout previewLayout;
    @BindView(R.id.tv_checked_list_text)
    TextView tvCheckedListText;
    @BindView(R.id.rv_attended)
    RecyclerView rvAttended;
    @BindView(R.id.bt_switch_camera)
    Button btSwitchCamera;

    private IVideoAttend.Presenter mVideoAttendPresenter;
    private ICameraOperator.Presenter mCameraPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_attend);
        ButterKnife.bind(this);
        mCameraPresenter = new CameraOperatorPresenter(this);
        mVideoAttendPresenter = new VideoAttendPresenter(this);
        initView();
        initModel();
    }

    private void initView() {
        mVideoAttendPresenter.initSurfaceView(surfaceview);
        mVideoAttendPresenter.initRecyclerView(this, rvAttended);
    }

    private void initModel() {
        mVideoAttendPresenter.initModel(this);
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
        mVideoAttendPresenter.startInferThread();
        mCameraPresenter.openCamera();
    }

    private void stopAttend() {
        mCameraPresenter.closeCamera();
        mVideoAttendPresenter.stopInferThread();
        mCameraPresenter.stopCaptureThread();
    }

    @OnClick(value = R.id.bt_switch_camera)
    public void setBt_switch_camera(View view) {
        mVideoAttendPresenter.stopInferThread();
        mCameraPresenter.setCameraFont(!mCameraPresenter.isFont());
        mVideoAttendPresenter.startInferThread();
    }

    @Override
    public boolean isCameraPrepare() {
        if (mVideoAttendPresenter == null || mCameraPresenter == null)
            return false;
        return mVideoAttendPresenter.isInferAble() && mCameraPresenter.isCaptureAble();
    }

    @Override
    public boolean isAttendAble() {
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
