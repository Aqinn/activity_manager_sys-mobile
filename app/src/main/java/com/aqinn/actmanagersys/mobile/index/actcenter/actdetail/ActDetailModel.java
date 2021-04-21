package com.aqinn.actmanagersys.mobile.index.actcenter.actdetail;

import com.aqinn.actmanagersys.mobile.dto.ApiResult;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.utils.FormatUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 活动详情 - Model
 *
 * @author Aqinn
 * @date 2021/4/16 10:56 AM
 */
public class ActDetailModel implements IActDetail.Model {

    @Override
    public void insertAct(ActShow act, Callback callback) {
        if (!FormatUtil.verifyAct(act)) {
            callback.onError(IActDetail.ErrorCode.INFO_ILLEGAL);
            return;
        }
        getActService().createAct(act.getName(), act.getDesc(), act.getLocation(), act.getStartTime(), act.getEndTime())
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
                            callback.onError(IActDetail.ErrorCode.UNKNOWN_RESPONSE_ERROR);
                            // callback.onError(IActDetail.ErrorCode.ACT_REPEAT);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        callback.onError(IActDetail.ErrorCode.UNKNOWN_NETWORK_ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void updateAct(ActShow act, Callback callback) {
        if (!FormatUtil.verifyAct(act)) {
            callback.onError(IActDetail.ErrorCode.INFO_ILLEGAL);
            return;
        }

        getActService().updateAct(act.getActId(), act.getName(), act.getDesc(), act.getLocation(), act.getStartTime(), act.getEndTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ApiResult result) {
                        if (result.success) {
                            callback.onSuccess(act);
                        } else {
                            callback.onError(IActDetail.ErrorCode.UNKNOWN_RESPONSE_ERROR);
                            // callback.onError(IActDetail.ErrorCode.ACT_REPEAT);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        callback.onError(IActDetail.ErrorCode.UNKNOWN_NETWORK_ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
