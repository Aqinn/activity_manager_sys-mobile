package com.aqinn.actmanagersys.mobile.facecollect;

import android.app.Activity;
import android.content.Context;
import android.view.SurfaceView;

import com.aqinn.actmanagersys.mobile.base.BaseNetworkService;
import com.aqinn.actmanagersys.mobile.myview.AutoFitTextureView;
import com.qmuiteam.qmui.widget.QMUIProgressBar;

/**
 * 人脸采集 - MVP接口
 *
 * @author Aqinn
 * @date 2021/4/6 11:39 AM
 */
public interface IFaceCollect {

    interface View {
        void setResult(String msg);

        boolean isCollectAble();

        boolean isCameraPrepare();

        AutoFitTextureView getTextureView();
    }

    interface Presenter {
        void initProgressBar(Activity activity, QMUIProgressBar progressBar);

        void initModel(Activity activity);

        void initSurfaceView(SurfaceView surfaceView);

        void startInferThread();

        void stopInferThread();

        boolean isInferAble();
    }

    interface Model extends BaseNetworkService {

    }

}
