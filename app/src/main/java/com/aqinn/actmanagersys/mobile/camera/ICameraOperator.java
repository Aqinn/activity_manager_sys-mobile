package com.aqinn.actmanagersys.mobile.camera;

import android.app.Activity;
import android.content.Context;
import android.view.TextureView;

import com.aqinn.actmanagersys.mobile.myview.AutoFitTextureView;

/**
 * 相机操作 - MVP接口
 * 基于Camera2 API
 * @author Aqinn
 * @date 2021/4/6 3:01 PM
 */
public interface ICameraOperator {

    interface View{
        AutoFitTextureView getTextureView();
        Activity getContext();
    }
    interface Presenter{
        void startCaptureThread();
        void stopCaptureThread();
        boolean isCaptureAble();
        void openCamera();
        void closeCamera();
        boolean isCameraPrepare(Context context);
        boolean isFont();
        void setCameraFont(boolean isFont);
    }
    interface Model{

    }
}
