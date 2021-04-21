package com.aqinn.actmanagersys.mobile.index.actcenter;

import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.base.BaseNetworkService;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.model.InsertActMessage;
import com.aqinn.actmanagersys.mobile.model.JoinActResult;
import com.youth.banner.Banner;

/**
 * 活动中心 - MVP接口
 *
 * @author Aqinn
 * @date 2021/3/29 5:31 PM
 */
@Deprecated
public interface IActCenter {

    interface View {
    }

    interface Presenter {
        void initBanner(BaseFragment context, Banner banner);

        void showCreateMenuDialog(ActCenterFragment context, android.view.View view);
    }

    interface Model extends BaseNetworkService {

        void joinAct(String code, String pwd, Callback callback);

        interface Callback {
            void onSuccess(JoinActResult joinActResult);

            void onError(ErrorCode errorCode);
        }
    }

    enum ErrorCode {
        NOT_FOUND_ACT("活动不存在"),
        WRONG_PWD("密码错误"),
        WRONG_FORMAT("活动代码或密码格式错误"),
        UNKNOWN_RESPONSE_ERROR("未知响应错误"),
        UNKNOWN_NETWORK_ERROR("未知网络错误");
        public String desc;

        ErrorCode(String desc) {
            this.desc = desc;
        }
    }

}
