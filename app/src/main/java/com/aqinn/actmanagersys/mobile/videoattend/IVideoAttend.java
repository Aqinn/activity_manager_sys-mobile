package com.aqinn.actmanagersys.mobile.videoattend;

import android.app.Activity;
import android.view.SurfaceView;

import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.base.BaseNetworkService;
import com.aqinn.actmanagersys.mobile.myview.AutoFitTextureView;

/**
 * 视频签到 - MVP接口
 *
 * @author Aqinn
 * @date 2021/4/7 10:49 AM
 */
public interface IVideoAttend {

    interface View {
        boolean isCameraPrepare();

        boolean isAttendAble();

        AutoFitTextureView getTextureView();
    }

    interface Presenter {
        void initModel(Activity activity);

        void initSurfaceView(SurfaceView surfaceView);

        void initRecyclerView(Activity context, RecyclerView recyclerView);

        void startInferThread();

        void stopInferThread();

        boolean isInferAble();
    }

    interface Model extends BaseNetworkService {

    }

}
