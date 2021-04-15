package com.aqinn.actmanagersys.mobile.register;

import com.aqinn.actmanagersys.mobile.base.BaseApplication;
import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.login.ILogin;
import com.aqinn.actmanagersys.mobile.model.RegisterResult;
import com.aqinn.actmanagersys.mobile.model.User;
import com.aqinn.actmanagersys.mobile.utils.FormatUtil;
import com.aqinn.actmanagersys.mobile.utils.SPUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 注册功能 - Model
 * @author Aqinn
 * @date 2021/3/29 4:55 PM
 */
public class RegisterModel implements IRegister.Model {

    @Override
    public void register(User user, Callback callback) {
        if (!FormatUtil.verifyAccount(user.getAccount()) || !FormatUtil.verifyPwd(user.getPwd())) {
            callback.onError(IRegister.ErrorCode.WRONG_FORMAT);
            return;
        }
        getUserService().register(user.getAccount(), user.getPwd(), user.getName(),
                user.getContact(), user.getSex(), user.getIntro())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult<RegisterResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ApiResult<RegisterResult> result) {
                        if (result.success) {
                            // result.data.getId()
                            callback.onSuccess();
                        } else {
                            // TODO 这个判断有点笨，设计对接的时候有点遗漏，但是前端这种状态码的机制可以保留
                            //      服务端返回的可以是登录失败状态码，前端自行判断，可以防止数据被窥探后解析
                            if ("账号重复".equals(result.errMsg))
                                callback.onError(IRegister.ErrorCode.ACCOUNT_REPEAT);
                            else
                                callback.onError(IRegister.ErrorCode.UNKNOWN_ERROR);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        callback.onError(IRegister.ErrorCode.UNKNOWN_NETWORK_ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
