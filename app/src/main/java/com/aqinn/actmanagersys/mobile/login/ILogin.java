package com.aqinn.actmanagersys.mobile.login;

/**
 * 登录功能 - MVP接口
 * @author Aqinn
 * @date 2021/3/29 11:43 AM
 */
public interface ILogin {

    interface View {
        // 登录成功
        void loginSuccess();
        // 登录失败
        void loginError(ErrorCode errCode);
    }

    interface Presenter {
        // 登录
        void login(String account, String pwd);
        // 校验账户格式
        boolean verifyAccount(String account);
        // 校验密码格式
        boolean verifyPwd(String pwd);
    }

    interface Model {
        // 向后台发起校验
        void login(String account, String pwd, Callback callback);
        interface Callback{
            void onSuccess();
            void onError(ErrorCode errCode);
        }
    }

    // 登录失败状态码
    enum ErrorCode {
        NOT_FOUND_USER,
        WRONG_PWD,
        WRONG_FORMAT
    }

}
