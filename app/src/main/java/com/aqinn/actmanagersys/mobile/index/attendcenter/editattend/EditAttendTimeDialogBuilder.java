package com.aqinn.actmanagersys.mobile.index.attendcenter.editattend;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.myview.CustomDatePicker;
import com.aqinn.actmanagersys.mobile.utils.FormatUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogView;

/**
 * 编辑签到时间的 DialogBuilder
 *
 * @author Aqinn
 * @date 2021/4/16 10:14 PM
 */
public class EditAttendTimeDialogBuilder extends QMUIDialogBuilder<EditAttendTimeDialogBuilder> {

    private TextView tvStartTime;
    private TextView tvEndTime;

    public CustomDatePicker startTimePicker;
    public CustomDatePicker endTimePicker;

    public EditAttendTimeDialogBuilder(Context context) {
        super(context);
    }

    @Nullable
    @Override
    protected View onCreateContent(@NonNull QMUIDialog dialog, @NonNull QMUIDialogView parent, @NonNull Context context) {
        View content = dialog.getLayoutInflater().inflate(R.layout.fragment_edit_attend_time, null);
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
        return wrapWithScroll(content);
    }

    @Nullable
    @Override
    protected View onCreateTitle(@NonNull QMUIDialog dialog, @NonNull QMUIDialogView parent, @NonNull Context context) {
        View tv = super.onCreateTitle(dialog, parent, context);
        if (tv != null) {
            TypedArray a = context.obtainStyledAttributes(null,
                    com.qmuiteam.qmui.R.styleable.QMUIDialogTitleTvCustomDef, com.qmuiteam.qmui.R.attr.qmui_dialog_title_style, 0);
            int count = a.getIndexCount();
            for (int i = 0; i < count; i++) {
                int attr = a.getIndex(i);
                if (attr == com.qmuiteam.qmui.R.styleable.QMUIDialogTitleTvCustomDef_qmui_paddingBottomWhenNotContent) {
                    tv.setPadding(
                            tv.getPaddingLeft(),
                            tv.getPaddingTop(),
                            tv.getPaddingRight(),
                            a.getDimensionPixelSize(attr, tv.getPaddingBottom())
                    );
                }
            }
            a.recycle();
        }
        return tv;
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
