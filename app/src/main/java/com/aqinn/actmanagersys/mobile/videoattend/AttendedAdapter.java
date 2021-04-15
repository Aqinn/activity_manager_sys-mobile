package com.aqinn.actmanagersys.mobile.videoattend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.R;

import java.util.List;

/**
 * 已签到的RecyclerView适配器
 * @author Aqinn
 * @date 2021/1/16 12:52 AM
 */
public class AttendedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<String> mData;

    public AttendedAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_attend_attended,parent, false);
        return new AttendedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AttendedViewHolder myHolder = (AttendedViewHolder) holder;
        myHolder.tv_item.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addAttended(String info) {
        mData.add(0, info);
        notifyItemInserted(0);
    }

}
