package com.aqinn.actmanagersys.mobile.login;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.model.LoginResult;
import com.aqinn.actmanagersys.mobile.utils.FormatUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录功能 - Model
 *
 * @author Aqinn
 * @date 2021/3/29 1:01 PM
 */
public class LoginModel implements ILogin.Model {

    @Override
    public void login(String account, String pwd, Callback callback) {
        if (!FormatUtil.verifyAccount(account) || !FormatUtil.verifyPwd(pwd)) {
            callback.onError(ILogin.ErrorCode.WRONG_FORMAT);
            return;
        }
        getUserService().login(account, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult<LoginResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ApiResult<LoginResult> result) {
                        if (result.success) {
                            callback.onSuccess(result.data.getId(), result.data.getName(), result.data.getToken());
                        } else {
                            // TODO 这个判断有点笨，设计对接的时候有点遗漏，但是前端这种状态码的机制可以保留
                            //      服务端返回的可以是登录失败状态码，前端自行判断，可以防止数据被窥探后解析
                            if ("用户不存在".equals(result.errMsg))
                                callback.onError(ILogin.ErrorCode.NOT_FOUND_USER);
                            else if ("密码错误".equals(result.errMsg))
                                callback.onError(ILogin.ErrorCode.WRONG_PWD);
                            else
                                callback.onError(ILogin.ErrorCode.UNKNOWN_RESPONSE_ERROR);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        callback.onError(ILogin.ErrorCode.UNKNOWN_NETWORK_ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
