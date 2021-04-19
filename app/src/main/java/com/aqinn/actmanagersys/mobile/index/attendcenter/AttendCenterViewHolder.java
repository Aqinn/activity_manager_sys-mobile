package com.aqinn.actmanagersys.mobile.index.attendcenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 签到中心 - ViewHolder
 *
 * @author Aqinn
 * @date 2021/4/7 1:45 PM
 */
public class AttendCenterViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_line_up)
    ImageView ivLineUp;
    @BindView(R.id.iv_circle)
    ImageView ivCircle;
    @BindView(R.id.iv_line_down)
    ImageView ivLineDown;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.cl_attend_info)
    ConstraintLayout clAttendInfo;
    @BindView(R.id.tv_time)
    TextView tvTime;

    public AttendCenterViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
