package com.aqinn.actmanagersys.mobile.index.act.joinact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.model.ActShow;

import java.util.List;
import java.util.Random;

/**
 * 参与的活动列表 JoinActAdapter
 *
 * @author Aqinn
 * @date 2021/4/15 9:52 PM
 */
public class JoinActAdapter extends RecyclerView.Adapter<JoinActViewHolder> {

    private Context mContext;
    private List<ActShow> mData;

    public JoinActAdapter(Context context, List<ActShow> testData) {
        mContext = context;
        mData = testData;
    }

    @NonNull
    @Override
    public JoinActViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_create_act, parent, false);
        return new JoinActViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull JoinActViewHolder holder, int position) {
        holder.tvName.setText(mData.get(position).getName());
        holder.tvTime.setText(mData.get(position).getName());
        holder.tvLoc.setText(mData.get(position).getName());
        ActShow item = new ActShow();
        item.setStatus(new Random().nextInt(3) + 1);
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
