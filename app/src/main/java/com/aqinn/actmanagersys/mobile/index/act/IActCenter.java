package com.aqinn.actmanagersys.mobile.index.act;

import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.BaseNetworkService;
import com.youth.banner.Banner;

/**
 * 活动中心 - MVP接口
 *
 * @author Aqinn
 * @date 2021/3/29 5:31 PM
 */
public interface IActCenter {

    interface View {
    }

    interface Presenter {
        void initBanner(BaseFragment context, Banner banner);
    }

    interface Model extends BaseNetworkService {
    }

}
