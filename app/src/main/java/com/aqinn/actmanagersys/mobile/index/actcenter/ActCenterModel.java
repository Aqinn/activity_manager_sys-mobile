package com.aqinn.actmanagersys.mobile.index.actcenter;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.model.ActShow;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 活动中心 - Model
 *
 * @author Aqinn
 * @date 2021/4/16 12:25 PM
 */
public class ActCenterModel implements IActCenter.Model {

    @Override
    public void joinAct(String code, String pwd, Callback callback) {
        if (!verify(code, pwd)) {
            callback.onError(IActCenter.ErrorCode.WRONG_FORMAT);
            return;
        }
        getUserActService().joinAct(code, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult<ActShow>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ApiResult<ActShow> result) {
                        if (result.success) {
                            callback.onSuccess(result.data);
                        } else {
                            callback.onError(IActCenter.ErrorCode.UNKNOWN_RESPONSE_ERROR);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        callback.onError(IActCenter.ErrorCode.UNKNOWN_NETWORK_ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 验证活动代码和密码是否符合格式
     *
     * @param code
     * @param pwd
     * @return
     */
    private boolean verify(String code, String pwd) {
        if (code == null || pwd == null)
            return false;
        if (code.length() != 6 || pwd.length() != 6)
            return false;
        for (int i = 0; i < code.length(); i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return false;
            }
        }
        for (int i = 0; i < pwd.length(); i++) {
            if (!Character.isDigit(pwd.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
