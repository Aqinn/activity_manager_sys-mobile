package com.aqinn.actmanagersys.mobile.register;

import com.aqinn.actmanagersys.mobile.model.User;

/**
 * 注册功能 - MVP接口
 *
 * @author Aqinn
 * @date 2021/3/29 1:30 PM
 */
public interface IRegister {

    interface View {
        // 注册成功
        void registerSuccess();
        // 注册失败
        void registerError(ErrorCode errorCode);
    }

    interface Presenter {
        /**
         * 注册账号
         * @param user 包含一个用户的所有信息
         */
        void register(User user);
    }

    interface Model {
        // 向后台发起校验
        void register(User user, Callback callback);
        interface Callback{
            void onSuccess();
            void onError(ErrorCode errCode);
        }
    }

    // 注册失败错误码
    enum ErrorCode {
        ACCOUNT_REPEAT("账号重复"),
        UNKNOWN_ERROR("未知错误");
        public String desc;
        ErrorCode(String desc) {
            this.desc = desc;
        }
    }

}
