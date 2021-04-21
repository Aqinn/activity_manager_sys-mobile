package com.aqinn.actmanagersys.mobile.selfattend;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 自助签到 - Model
 *
 * @author Aqinn
 * @date 2021/4/21 10:34 PM
 */
public class SelfAttendModel implements ISelfAttend.Model {

    @Override
    public void selfAttend(Long attendId, String feature, SelfAttendCallback callback) {
        getUserAttendService().selfAttend(attendId, feature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ApiResult result) {
                        if (result.success) {
                            callback.onSuccess();
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
