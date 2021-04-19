package com.aqinn.actmanagersys.mobile.index.actcenter.joinact;

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
 * 加入活动的 DialogBuilder
 *
 * @author Aqinn
 * @date 2021/4/16 10:14 PM
 */
public class JoinActDialogBuilder extends QMUIDialogBuilder<JoinActDialogBuilder> {

    private TextView etCode;
    private TextView etPwd;

    public JoinActDialogBuilder(Context context) {
        super(context);
    }

    @Nullable
    @Override
    protected View onCreateContent(@NonNull QMUIDialog dialog, @NonNull QMUIDialogView parent, @NonNull Context context) {
        View content = dialog.getLayoutInflater().inflate(R.layout.dialog_join_act, null);
        etCode = content.findViewById(R.id.et_code);
        etPwd = content.findViewById(R.id.et_pwd);
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

    public String getCode() {
        return etCode.getText().toString();
    }

    public String getPwd() {
        return etPwd.getText().toString();
    }

}
