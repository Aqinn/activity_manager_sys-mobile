package com.aqinn.actmanagersys.mobile.videoattend;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.R;

/**
 * 已签到的RecyclerView ViewHolder
 * @author Aqinn
 * @date 2021/1/16 12:56 AM
 */
public class AttendedViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_item;

    public AttendedViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_item = itemView.findViewById(R.id.tv_item);
    }

}
