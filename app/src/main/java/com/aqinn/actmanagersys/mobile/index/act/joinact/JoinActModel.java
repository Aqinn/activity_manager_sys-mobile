package com.aqinn.actmanagersys.mobile.index.act.joinact;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.index.act.createact.ICreateAct;
import com.aqinn.actmanagersys.mobile.model.ActShow;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 参与的活动 - Model
 *
 * @author Aqinn
 * @date 2021/4/15 10:03 PM
 */
public class JoinActModel implements IJoinAct.Model {

    @Override
    public void getJoinAct(List<ActShow> data, Callback callback) {
        getSetupService().getJoinActs()
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
