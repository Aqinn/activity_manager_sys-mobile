package com.aqinn.actmanagersys.mobile.index.attendcenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.model.AttendShow;
import com.aqinn.actmanagersys.mobile.utils.ParseUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 签到中心 - Adapter
 * @author Aqinn
 * @date 2021/4/7 1:45 PM
 */
@Deprecated
public class AttendCenterAdapter extends RecyclerView.Adapter<AttendCenterViewHolder> {

    private Context mContext;
    private List<AttendShow> mData;
    private static Map<Integer, String> mMap;

    static {
        mMap = new HashMap<>();
        mMap.put(0, "未开始");
        mMap.put(1, "进行中");
        mMap.put(2, "已结束");
    }

    public AttendCenterAdapter(Context context, List<AttendShow> testData) {
        mContext = context;
        mData = testData;
    }

    @NonNull
    @Override
    public AttendCenterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_timeline_attend, parent, false);
        return new AttendCenterViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendCenterViewHolder holder, int position) {
        AttendShow item = mData.get(position);
        holder.tvName.setText(item.getName());
        holder.tvType.setText(ParseUtil.getAttendType(item.getAttendType()));
        holder.tvCount.setText(item.getShouldAttendCount() + "/" + item.getHaveAttendCount() + "/" + (item.getShouldAttendCount() - item.getHaveAttendCount()));
        String status = mMap.get(new Random().nextInt(3));
        holder.tvStatus.setText(status);
        holder.tvTime.setText(item.getStartTime() + " -> " + item.getEndTime());
        int statusTextColor = R.color.thing_default;
        if ("未开始".equals(status)) {
            statusTextColor = R.color.thing_not_begin;
            holder.ivCircle.setImageDrawable(mContext.getResources().getDrawable(R.drawable.circle_not_begin));
        } else if ("进行中".equals(status)) {
            statusTextColor = R.color.thing_ing;
            holder.ivCircle.setImageDrawable(mContext.getResources().getDrawable(R.drawable.circle_ing));
        } else if ("已结束".equals(status)) {
            statusTextColor = R.color.thing_finish;
            holder.ivCircle.setImageDrawable(mContext.getResources().getDrawable(R.drawable.circle_finish));
        }
        holder.tvStatus.setTextColor(mContext.getResources().getColor(statusTextColor));
        if (position == 0) {
            holder.ivLineUp.setVisibility(View.INVISIBLE);
        } else if (position == getItemCount() - 1) {
            holder.ivLineDown.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
