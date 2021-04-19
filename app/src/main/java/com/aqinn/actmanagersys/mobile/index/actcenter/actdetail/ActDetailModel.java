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
        if (!verifyAct(act)) {
            callback.onError(IActDetail.ErrorCode.INFO_ILLEGAL);
            return;
        }
        // 发送数据前一定要将时间转换成long格式时间戳
        act.setStartTime(String.valueOf(FormatUtil.str2Long(act.getStartTime(), true)));
        act.setEndTime(String.valueOf(FormatUtil.str2Long(act.getEndTime(), true)));
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
                            // 设置数据前要将数据转回字符串形式
                            result.data.setStartTime(FormatUtil.long2Str(Long.parseLong(result.data.getStartTime()), true));
                            result.data.setEndTime(FormatUtil.long2Str(Long.parseLong(result.data.getEndTime()), true));
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
        if (!verifyAct(act)) {
            callback.onError(IActDetail.ErrorCode.INFO_ILLEGAL);
            return;
        }
        // 发送数据前一定要将时间转换成long格式时间戳
        act.setStartTime(String.valueOf(FormatUtil.str2Long(act.getStartTime(), true)));
        act.setEndTime(String.valueOf(FormatUtil.str2Long(act.getEndTime(), true)));
        getActService().updateAct(act.getName(), act.getDesc(), act.getLocation(), act.getStartTime(), act.getEndTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ApiResult result) {
                        if (result.success) {
                            // 设置数据前要将数据转回字符串形式
                            act.setStartTime(FormatUtil.long2Str(Long.parseLong(act.getStartTime()), true));
                            act.setEndTime(FormatUtil.long2Str(Long.parseLong(act.getEndTime()), true));
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

    private boolean verifyAct(ActShow act) {
        if (act == null)
            return false;
        if (FormatUtil.verifyActName(act.getName()) && FormatUtil.verifyActLocation(act.getLocation()) &&
                FormatUtil.verifyActDesc(act.getDesc()) && FormatUtil.verifyActTime(act.getStartTime(), act.getEndTime())) {
            return true;
        }
        return false;
    }

}
