package com.aqinn.actmanagersys.mobile.login;

import com.aqinn.actmanagersys.mobile.utils.FormatUtil;

/**
 * 登录功能 - Presenter
 * @author Aqinn
 * @date 2021/3/29 12:56 PM
 */
public class LoginPresenter implements ILogin.Presenter {

    private ILogin.View mView;
    private ILogin.Model mModel;

    public LoginPresenter(ILogin.View view) {
        this.mView = view;
        this.mModel = new LoginModel();
    }

    @Override
    public void login(String account, String pwd) {
        if (!verifyAccount(account) || !verifyPwd(pwd)){
            mView.loginError(ILogin.ErrorCode.WRONG_FORMAT);
            return;
        }
        mModel.login(account, pwd, new ILogin.Model.Callback() {
            @Override
            public void onSuccess() {
                mView.loginSuccess();
            }

            @Override
            public void onError(ILogin.ErrorCode errCode) {
                mView.loginError(errCode);
            }
        });
    }

    @Override
    public boolean verifyAccount(String account) {
        return FormatUtil.verifyAccount(account);
    }

    @Override
    public boolean verifyPwd(String pwd) {
        return FormatUtil.verifyPwd(pwd);
    }

}
