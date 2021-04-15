package com.aqinn.actmanagersys.mobile.index.act.joinact;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 参与的活动列表 Adapter
 *
 * @author Aqinn
 * @date 2021/4/15 9:53 PM
 */
public class JoinActViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_loc)
    TextView tvLoc;
    @BindView(R.id.tv_intro_text)
    TextView tvIntroText;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.cl_item_act_intro)
    ConstraintLayout clItemActIntro;

    public JoinActViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
