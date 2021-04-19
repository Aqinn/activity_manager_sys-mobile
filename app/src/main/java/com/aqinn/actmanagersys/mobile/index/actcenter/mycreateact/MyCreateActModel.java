package com.aqinn.actmanagersys.mobile.index.actcenter.mycreateact;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.model.ActShow;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 创建的活动 - Model
 *
 * @author Aqinn
 * @date 2021/4/15 8:29 PM
 */
public class MyCreateActModel implements IMyCreateAct.Model {

    private static final String TAG = "MyCreateActModel";

    @Override
    public void getCreateAct(List<ActShow> data, Callback callback) {
        getSetupService().getCreateActs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult<List<ActShow>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ApiResult<List<ActShow>> result) {
                        if (result.success) {
                            data.addAll(result.data);
                            callback.onSuccess();
                        } else {
                            callback.onError(IMyCreateAct.ErrorCode.UNKNOWN_RESPONSE_ERROR);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        callback.onError(IMyCreateAct.ErrorCode.UNKNOWN_NETWORK_ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void deleteCreateAct(Long actId, Callback callback) {
        getActService().deleteAct(actId)
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
                            callback.onError(IMyCreateAct.ErrorCode.UNKNOWN_RESPONSE_ERROR);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        callback.onError(IMyCreateAct.ErrorCode.UNKNOWN_NETWORK_ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
