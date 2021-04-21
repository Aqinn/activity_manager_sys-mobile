package com.aqinn.actmanagersys.mobile.facecollect;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 人脸采集 - Model
 *
 * @author Aqinn
 * @date 2021/4/21 8:29 PM
 */
public class FaceCollectModel implements IFaceCollect.Model {

    @Override
    public void faceCollect(String f1, String f2, String f3, String f4, FaceCollectCallback callback) {
        getUserFeatureService().faceCollect(f1, f2, f3, f4)
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
