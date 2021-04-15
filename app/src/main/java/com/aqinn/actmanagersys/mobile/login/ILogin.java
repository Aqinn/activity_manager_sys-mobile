package com.aqinn.actmanagersys.mobile.login;

import com.aqinn.actmanagersys.mobile.base.BaseNetworkService;

/**
 * 登录功能 - MVP接口
 *
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
        void login(String account, String pwd, boolean isRemember);

        // 校验账户格式
        boolean verifyAccount(String account);

        // 校验密码格式
        boolean verifyPwd(String pwd);
    }

    interface Model extends BaseNetworkService {
        // 向后台发起校验
        void login(String account, String pwd, Callback callback);

        interface Callback {
            void onSuccess(Long id, String name, String token);

            void onError(ErrorCode errCode);
        }
    }

    // 登录失败状态码
    enum ErrorCode {
        NOT_FOUND_USER("账号不存在"),
        WRONG_PWD("密码错误"),
        WRONG_FORMAT("账号或密码格式错误"),
        UNKNOWN_RESPONSE_ERROR("未知响应错误"),
        UNKNOWN_NETWORK_ERROR("未知网络错误");
        public String desc;

        ErrorCode(String desc) {
            this.desc = desc;
        }
    }

}
