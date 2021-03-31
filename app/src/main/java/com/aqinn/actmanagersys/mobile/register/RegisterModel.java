package com.aqinn.actmanagersys.mobile.register;

import com.aqinn.actmanagersys.mobile.model.User;

/**
 * 注册功能 - Model
 * @author Aqinn
 * @date 2021/3/29 4:55 PM
 */
public class RegisterModel implements IRegister.Model {

    @Override
    public void register(User user, Callback callback) {
        // TODO 发起网络请求，回调callback
        callback.onSuccess();
    }

}
