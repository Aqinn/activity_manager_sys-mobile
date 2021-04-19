package com.aqinn.actmanagersys.mobile.index.actcenter.mycreateact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.model.ActShow;

import java.util.List;

/**
 * 创建的活动 Adapter
 *
 * @author Aqinn
 * @date 2021/4/15 8:21 PM
 */
public class MyCreateActAdapter extends RecyclerView.Adapter<MyCreateActViewHolder> {

    private Context mContext;
    private List<ActShow> mData;

    public MyCreateActAdapter(Context context, List<ActShow> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public MyCreateActViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_create_act, parent, false);
        return new MyCreateActViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCreateActViewHolder holder, int position) {
        ActShow item = mData.get(position);
        holder.tvName.setText(item.getName());
        holder.tvTime.setText(item.getStartTime() + " -> " + item.getEndTime());
        holder.tvLoc.setText(item.getLocation());
        holder.tvIntro.setText(item.getDesc());
        // item.setStatus(new Random().nextInt(3) + 1);
        String statusText = "";
        int statusTextColor = R.color.thing_default;
        if (2 == item.getStatus()) {
            statusText = mContext.getResources().getString(R.string.act_ing);
            statusTextColor = R.color.thing_ing;
        }
        if (1 == item.getStatus()) {
            statusText = mContext.getResources().getString(R.string.act_not_begin);
            statusTextColor = R.color.thing_not_begin;
        }
        if (3 == item.getStatus()) {
            statusText = mContext.getResources().getString(R.string.act_end);
            statusTextColor = R.color.thing_finish;
        }
        holder.tvStatus.setText(statusText);
        holder.tvStatus.setTextColor(mContext.getResources().getColor(statusTextColor));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
