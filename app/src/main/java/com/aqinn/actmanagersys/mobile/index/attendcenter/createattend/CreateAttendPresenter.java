package com.aqinn.actmanagersys.mobile.index.attendcenter.createattend;

import android.widget.TextView;

import com.aqinn.actmanagersys.mobile.myview.CustomDatePicker;
import com.aqinn.actmanagersys.mobile.utils.FormatUtil;

/**
 * 创建签到 - Presenter
 *
 * @author Aqinn
 * @date 2021/4/16 9:02 PM
 */
@Deprecated
public class CreateAttendPresenter implements ICreateAttend.Presenter {

    private ICreateAttend.View mView;

    public CreateAttendPresenter(ICreateAttend.View view) {
        mView = view;
    }

    @Override
    public void initTimePicker(CreateAttendFragment context, TextView tvStartTime, TextView tvEndTime) {
        initStartTimePicker(context, tvStartTime);
        initEndTimePicker(context, tvEndTime);
    }

    /**
     * 初始化签到开始时间选择器
     */
    private void initStartTimePicker(CreateAttendFragment context, TextView tvStartTime) {
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
    private void initEndTimePicker(CreateAttendFragment context, TextView tvEndTime) {
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
