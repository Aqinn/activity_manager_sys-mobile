package com.aqinn.actmanagersys.mobile.index.actcenter.actdetail;

import android.widget.TextView;

import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.myview.CustomDatePicker;
import com.aqinn.actmanagersys.mobile.utils.FormatUtil;

/**
 * 活动详情 - Presenter
 *
 * @author Aqinn
 * @date 2021/4/16 10:55 AM
 */
public class ActDetailPresenter implements IActDetail.Presenter {

    private IActDetail.View mView;
    private IActDetail.Model mModel;

    public ActDetailPresenter(IActDetail.View view) {
        mView = view;
        mModel = new ActDetailModel();
    }

    @Override
    public void initTimePicker(ActDetailFragment context, TextView tvStartTime, TextView tvEndTime) {
        initStartTimePicker(context, tvStartTime);
        initEndTimePicker(context, tvEndTime);
    }

    @Override
    public void createAct(ActShow act, Callback callback) {
        mModel.insertAct(act, new IActDetail.Model.Callback() {
            @Override
            public void onSuccess(ActShow resAct) {
                act.setActId(resAct.getActId());
                act.setCode(resAct.getCode());
                act.setPwd(resAct.getPwd());
                callback.onSuccess();
            }

            @Override
            public void onError(IActDetail.ErrorCode errorCode) {
                callback.onError(errorCode);
            }
        });
    }

    @Override
    public void editAct(ActShow act, Callback callback) {
        mModel.updateAct(act, new IActDetail.Model.Callback() {
            @Override
            public void onSuccess(ActShow resAct) {
                act.copyOther(resAct);
                callback.onSuccess();
            }

            @Override
            public void onError(IActDetail.ErrorCode errorCode) {
                callback.onError(errorCode);
            }
        });
    }

    /**
     * 初始化签到开始时间选择器
     */
    private void initStartTimePicker(ActDetailFragment context, TextView tvStartTime) {
        String beginTime = "1999-12-09 03:00";
        String endTime = FormatUtil.long2Str(1893427199000L, true);
        String currentTime = FormatUtil.long2Str(System.currentTimeMillis(), true);

        tvStartTime.setText(currentTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        context.startTimePicker = new CustomDatePicker(context.getContext(), new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                tvStartTime.setText(FormatUtil.long2Str(timestamp, true));
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        context.startTimePicker.setCancelable(true);
        // 显示时和分
        context.startTimePicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        context.startTimePicker.setScrollLoop(true);
        // 允许滚动动画
        context.startTimePicker.setCanShowAnim(true);
    }

    /**
     * 初始化签到结束时间选择器
     */
    private void initEndTimePicker(ActDetailFragment context, TextView tvEndTime) {
        String beginTime = "1999-12-09 03:00";
        String endTime = FormatUtil.long2Str(1893427199000L, true);
        String currentTime = FormatUtil.long2Str(System.currentTimeMillis(), true);

        tvEndTime.setText(currentTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        context.endTimePicker = new CustomDatePicker(context.getContext(), new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                tvEndTime.setText(FormatUtil.long2Str(timestamp, true));
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        context.endTimePicker.setCancelable(true);
        // 显示时和分
        context.endTimePicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        context.endTimePicker.setScrollLoop(true);
        // 允许滚动动画
        context.endTimePicker.setCanShowAnim(true);
    }

}
