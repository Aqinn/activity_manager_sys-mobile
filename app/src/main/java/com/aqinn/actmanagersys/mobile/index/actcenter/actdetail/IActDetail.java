package com.aqinn.actmanagersys.mobile.index.actcenter.actdetail;

import android.widget.TextView;

import com.aqinn.actmanagersys.mobile.base.BaseNetworkService;
import com.aqinn.actmanagersys.mobile.model.ActShow;

/**
 * 活动详情 - MVP接口
 *
 * @author Aqinn
 * @date 2021/4/5 4:29 PM
 */
public interface IActDetail {

    interface View {
//        void showCloseDialog(Callback callback);
//
//        interface Callback {
//            void onConfirm();
//
//            void onCancel();
//        }
    }

    interface Presenter {
        void initTimePicker(ActDetailFragment context, TextView tvStartTime, TextView tvEndTime);

        void createAct(ActShow act, Callback callback);

        void editAct(ActShow act, Callback callback);

        interface Callback {
            void onSuccess();

            void onError(ErrorCode errorCode);
        }
    }

    interface Model extends BaseNetworkService {
        void insertAct(ActShow act, Callback callback);

        void updateAct(ActShow act, Callback callback);

        interface Callback {
            void onSuccess(ActShow act);

            void onError(ErrorCode errorCode);
        }
    }

    enum ErrorCode {
        ACT_REPEAT("活动重复"),
        INFO_ILLEGAL("活动信息非法"),
        UNKNOWN_RESPONSE_ERROR("未知响应错误"),
        UNKNOWN_NETWORK_ERROR("未知网络错误");
        private String msg;

        ErrorCode(String msg) {
            this.msg = msg;
        }
    }

}
