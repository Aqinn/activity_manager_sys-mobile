package com.aqinn.actmanagersys.mobile.selfattend;

import android.app.Activity;
import android.view.SurfaceView;

import com.aqinn.actmanagersys.mobile.base.BaseNetworkService;
import com.aqinn.actmanagersys.mobile.myview.AutoFitTextureView;

/**
 * 自助签到 - MVP接口
 *
 * @author Aqinn
 * @date 2021/4/7 10:49 AM
 */
public interface ISelfAttend {

    interface View {
        void setResult(String msg);

        boolean isCameraPrepare();

        boolean isAttendAble();

        AutoFitTextureView getTextureView();
    }

    interface Presenter {
        void initModel(Activity activity);

        void initSurfaceView(SurfaceView surfaceView);

        void startInferThread();

        void stopInferThread();

        boolean isInferAble();
    }

    interface Model extends BaseNetworkService {

    }

}
