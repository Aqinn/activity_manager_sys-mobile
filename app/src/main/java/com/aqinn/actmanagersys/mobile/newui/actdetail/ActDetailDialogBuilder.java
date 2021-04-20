package com.aqinn.actmanagersys.mobile.newui.actdetail;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheetBaseBuilder;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheetRootLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 活动详情Dialog Builder
 *
 * @author Aqinn
 * @date 2021/4/19 4:06 PM
 */
public class ActDetailDialogBuilder extends QMUIBottomSheetBaseBuilder<ActDetailDialogBuilder> {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_creator_text)
    TextView tvCreatorText;
    @BindView(R.id.tv_creator)
    TextView tvCreator;
    @BindView(R.id.tv_start_time_text)
    TextView tvStartTimeText;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time_text)
    TextView tvEndTimeText;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_loc_text)
    TextView tvLocText;
    @BindView(R.id.tv_loc)
    TextView tvLoc;
    @BindView(R.id.tv_intro_text)
    TextView tvIntroText;
    @BindView(R.id.tv_intro)
    TextView tvIntro;

    private ActShow mAct;
    private DialogInterface.OnShowListener mOnShowListener;
    private DialogInterface.OnDismissListener mOnDismissListener;

    public ActDetailDialogBuilder(Context context, ActShow act) {
        super(context);
        this.mAct = act;
    }

    public ActDetailDialogBuilder(Context context, DialogInterface.OnShowListener mOnShowListener, DialogInterface.OnDismissListener mOnDismissListener) {
        super(context);
        this.mOnShowListener = mOnShowListener;
        this.mOnDismissListener = mOnDismissListener;
    }

    public ActDetailDialogBuilder(Context context) {
        super(context);
    }

    @Nullable
    @Override
    protected View onCreateContentView(@NonNull QMUIBottomSheet bottomSheet, @NonNull QMUIBottomSheetRootLayout rootLayout, @NonNull Context context) {
        View root = bottomSheet.getLayoutInflater().inflate(R.layout.dialog_act_detail, null);
        ButterKnife.bind(this, root);
        bottomSheet.setOnShowListener(mOnShowListener);
        bottomSheet.setOnDismissListener(mOnDismissListener);
        tvName.setText(mAct.getName());
        tvIntro.setText(mAct.getDesc());
        tvCreator.setText(mAct.getCreatorAccount());
        tvStartTime.setText(mAct.getStartTime());
        tvEndTime.setText(mAct.getEndTime());
        tvLoc.setText(mAct.getLocation());
        return root;
    }

    public void setOnShowListener(DialogInterface.OnShowListener OnShowListener) {
        mOnShowListener = OnShowListener;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

}
