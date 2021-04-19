package com.aqinn.actmanagersys.mobile.index.attendcenter;

import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.BaseNetworkService;

/**
 * 签到中心 - MVP接口
 *
 * @author Aqinn
 * @date 2021/4/7 1:42 PM
 */
public interface IAttendCenter {

    interface View {

    }

    interface Presenter {
        void initRecyclerView(BaseFragment context, RecyclerView recyclerView);
    }

    interface Model extends BaseNetworkService {

    }

}
