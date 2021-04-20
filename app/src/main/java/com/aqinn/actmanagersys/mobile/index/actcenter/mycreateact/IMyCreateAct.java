package com.aqinn.actmanagersys.mobile.index.actcenter.mycreateact;

import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.base.BaseNetworkService;
import com.aqinn.actmanagersys.mobile.model.ActShow;

import java.util.List;

/**
 * 创建的活动 - MVP
 *
 * @author Aqinn
 * @date 2021/4/15 8:22 PM
 */
@Deprecated
public interface IMyCreateAct {

    interface View {

    }

    interface Presenter {
        void initData(BaseFragmentActivity context, RecyclerView rv);
    }

    interface Model extends BaseNetworkService {
        void getCreateAct(List<ActShow> data, Callback callback);
        void deleteCreateAct(Long actId, Callback callback);

        interface Callback {
            void onSuccess();
            void onError(ErrorCode errorCode);
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
