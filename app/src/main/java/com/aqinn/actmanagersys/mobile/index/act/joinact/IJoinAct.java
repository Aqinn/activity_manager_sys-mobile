package com.aqinn.actmanagersys.mobile.index.act.joinact;

import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.base.BaseNetworkService;
import com.aqinn.actmanagersys.mobile.index.act.createact.ICreateAct;
import com.aqinn.actmanagersys.mobile.model.ActShow;

import java.util.List;

/**
 * 参与的活动 - MVP接口
 * @author Aqinn
 * @date 2021/4/15 9:54 PM
 */
public interface IJoinAct {

    interface View {

    }

    interface Presenter {
        void initData(BaseFragmentActivity context, RecyclerView rv);
    }

    interface Model extends BaseNetworkService {
        void getJoinAct(List<ActShow> data, Callback callback);

        interface Callback {
            void onSuccess();
            void onError(ICreateAct.ErrorCode errorCode);
        }
    }

    // 请求数据失败错误码
    enum ErrorCode {

        UNKNOWN_RESPONSE_ERROR("未知响应错误"),
        UNKNOWN_NETWORK_ERROR("未知网络错误");

        public String desc;

        ErrorCode(String desc) {
            this.desc = desc;
        }

    }

}
