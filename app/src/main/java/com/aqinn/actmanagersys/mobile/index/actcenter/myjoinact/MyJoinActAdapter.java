package com.aqinn.actmanagersys.mobile.index.actcenter.myjoinact;

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
 * 参与的活动列表 MyJoinActAdapter
 *
 * @author Aqinn
 * @date 2021/4/15 9:52 PM
 */
@Deprecated
public class MyJoinActAdapter extends RecyclerView.Adapter<MyJoinActViewHolder> {

    private Context mContext;
    private List<ActShow> mData;

    public MyJoinActAdapter(Context context, List<ActShow> testData) {
        mContext = context;
        mData = testData;
    }

    @NonNull
    @Override
    public MyJoinActViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_create_act, parent, false);
        return new MyJoinActViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MyJoinActViewHolder holder, int position) {
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

    /**
     * 删除item
     *
     * @param act
     */
    public void removeItem(ActShow act) {
        removeItem(mData.indexOf(act));
    }

    /**
     * 删除item
     *
     * @param position
     */
    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

}
