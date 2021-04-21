package com.aqinn.actmanagersys.mobile.index;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.model.JoinActResult;
import com.aqinn.actmanagersys.mobile.utils.FormatUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页 - Model
 *
 * @author Aqinn
 * @date 2021/4/21 5:39 PM
 */
public class IndexModel implements IIndex.Model {

    @Override
    public void joinAct(String code, String pwd, IIndex.JoinActCallback callback) {
        if (!FormatUtil.verifyActCode(code) || !FormatUtil.verifyActPwd(pwd)) {
            callback.onError();
            return;
        }
        getUserActService().joinAct(code, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult<JoinActResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ApiResult<JoinActResult> result) {
                        if (result.success) {
                            callback.onSuccess(result.data);
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        callback.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
