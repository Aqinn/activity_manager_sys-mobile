package com.aqinn.actmanagersys.mobile.login;

/**
 * 登录功能 - Model
 * @author Aqinn
 * @date 2021/3/29 1:01 PM
 */
public class LoginModel implements ILogin.Model {

    @Override
    public void login(String account, String pwd, Callback callback) {
        // TODO 发起网络请求，回调callback
        callback.onSuccess();
    }

}
