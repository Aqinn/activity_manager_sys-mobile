package com.aqinn.actmanagersys.mobile.index.attendcenter.editattend;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.myview.CustomDatePicker;
import com.aqinn.actmanagersys.mobile.utils.FormatUtil;
import com.aqinn.actmanagersys.mobile.utils.ParseUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogView;

/**
 * 编辑签到类型的 DialogBuilder
 *
 * @author Aqinn
 * @date 2021/4/16 10:14 PM
 */
public class EditAttendTypeDialogBuilder extends QMUIDialogBuilder<EditAttendTypeDialogBuilder> {

    private CheckBox cbSelf;
    private CheckBox cbVideo;

    private final Integer[] typeArr;

    public EditAttendTypeDialogBuilder(Context context, Integer typeDec) {
        super(context);
        typeArr = ParseUtil.dec2typeArr(typeDec);
    }

    @Nullable
    @Override
    protected View onCreateContent(@NonNull QMUIDialog dialog, @NonNull QMUIDialogView parent, @NonNull Context context) {
        View content = dialog.getLayoutInflater().inflate(R.layout.fragment_edit_attend_type, null);
        findView(content);
        initView(content);
        return wrapWithScroll(content);
    }

    private void findView(View content) {
        cbSelf = content.findViewById(R.id.cb_self);
        cbVideo = content.findViewById(R.id.cb_video);
    }

    private void initView(View content) {
        if (typeArr == null)
            return;
        for (Integer i : typeArr) {
            if (i == 1)
                cbSelf.setChecked(true);
            else if (i == 2)
                cbVideo.setChecked(true);
        }
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

    public Integer getAttendType() {
        int size = 0;
        if (cbSelf.isChecked())
            size++;
        if (cbVideo.isChecked())
            size++;
        if (size == 0)
            return -1;
        Integer[] res = new Integer[size];
        int idx = 0;
        if (cbSelf.isChecked())
            res[idx++] = 1;
        if (cbVideo.isChecked())
            res[idx] = 2;
        return ParseUtil.typeArr2dec(res);
    }

}
