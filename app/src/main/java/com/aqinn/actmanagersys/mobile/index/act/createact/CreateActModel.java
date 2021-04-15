package com.aqinn.actmanagersys.mobile.index.act.createact;

import android.util.Log;

import com.aqinn.actmanagersys.mobile.base.PublicConfig;
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
public class CreateActModel implements ICreateAct.Model {

    private static final String TAG = "CreateActModel";

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
                            callback.onError(ICreateAct.ErrorCode.UNKNOWN_RESPONSE_ERROR);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        callback.onError(ICreateAct.ErrorCode.UNKNOWN_NETWORK_ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
