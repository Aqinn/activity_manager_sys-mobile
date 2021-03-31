package com.aqinn.actmanagersys.mobile.register;

import com.aqinn.actmanagersys.mobile.model.User;

/**
 * 注册功能 - Presenter
 * @author Aqinn
 * @date 2021/3/29 4:48 PM
 */
public class RegisterPresenter implements IRegister.Presenter {

    private IRegister.View mView;
    private IRegister.Model mModel;

    public RegisterPresenter(IRegister.View view) {
        mView = view;
        mModel = new RegisterModel();
    }

    @Override
    public void register(User user) {
        mModel.register(user, new IRegister.Model.Callback() {
            @Override
            public void onSuccess() {
                mView.registerSuccess();
            }

            @Override
            public void onError(IRegister.ErrorCode errCode) {
                mView.registerError(errCode);
            }
        });
    }

}
