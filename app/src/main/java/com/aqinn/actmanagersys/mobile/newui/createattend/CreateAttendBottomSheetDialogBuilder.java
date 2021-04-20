package com.aqinn.actmanagersys.mobile.newui.createattend;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.myview.CustomDatePicker;
import com.aqinn.actmanagersys.mobile.utils.FormatUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheetBaseBuilder;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheetRootLayout;

/**
 * 创建签到BottomSheetDialog Builder
 *
 * @author Aqinn
 * @date 2021/4/19 5:19 PM
 */
public class CreateAttendBottomSheetDialogBuilder extends QMUIBottomSheetBaseBuilder<CreateAttendBottomSheetDialogBuilder> {

    private TextView tvStartTime;
    private TextView tvEndTime;

    public CustomDatePicker startTimePicker;
    public CustomDatePicker endTimePicker;

    public CreateAttendBottomSheetDialogBuilder(Context context) {
        super(context);
    }

    @Nullable
    @Override
    protected View onCreateContentView(@NonNull QMUIBottomSheet bottomSheet, @NonNull QMUIBottomSheetRootLayout rootLayout, @NonNull Context context) {
        View content = bottomSheet.getLayoutInflater().inflate(R.layout.fragment_create_attend, null);
        tvStartTime = content.findViewById(R.id.tv_start_time);
        tvEndTime = content.findViewById(R.id.tv_end_time);
        initStartTimePicker(context, tvStartTime);
        initEndTimePicker(context, tvEndTime);
        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimePicker.show(tvStartTime.getText().toString());
            }
        });
        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTimePicker.show(tvEndTime.getText().toString());
            }
        });
        return content;
    }

    /**
     * 初始化签到开始时间选择器
     */
    private void initStartTimePicker(Context context, TextView tvStartTime) {
        String beginTime = "1999-12-09 03:00";
        String endTime = FormatUtil.long2Str(1893427199000L, true);
        String currentTime = FormatUtil.long2Str(System.currentTimeMillis(), true);

        tvStartTime.setText(currentTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        startTimePicker = new CustomDatePicker(context, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                tvStartTime.setText(FormatUtil.long2Str(timestamp, true));
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        startTimePicker.setCancelable(true);
        // 显示时和分
        startTimePicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        startTimePicker.setScrollLoop(true);
        // 允许滚动动画
        startTimePicker.setCanShowAnim(true);
    }

    /**
     * 初始化签到结束时间选择器
     */
    private void initEndTimePicker(Context context, TextView tvEndTime) {
        String beginTime = "1999-12-09 03:00";
        String endTime = FormatUtil.long2Str(1893427199000L, true);
        String currentTime = FormatUtil.long2Str(System.currentTimeMillis(), true);

        tvEndTime.setText(currentTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        endTimePicker = new CustomDatePicker(context, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                tvEndTime.setText(FormatUtil.long2Str(timestamp, true));
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        endTimePicker.setCancelable(true);
        // 显示时和分
        endTimePicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        endTimePicker.setScrollLoop(true);
        // 允许滚动动画
        endTimePicker.setCanShowAnim(true);
    }

}
