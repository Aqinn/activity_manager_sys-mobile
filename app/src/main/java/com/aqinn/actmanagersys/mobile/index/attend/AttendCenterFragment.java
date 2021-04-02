package com.aqinn.actmanagersys.mobile.index.attend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.OnRecyclerItemClickListener;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.popup.QMUIQuickAction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 签到中心 - View
 *
 * @author Aqinn
 * @date 2021/4/1 10:19 AM
 */
public class AttendCenterFragment extends BaseFragment {

    @BindView(R.id.topbar)
    QMUITopBarLayout topbar;
    @BindView(R.id.rv)
    RecyclerView rv;

    private List<TestAttend> mData;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_attend_center, null);
        ButterKnife.bind(this, root);
        initView();
        return root;
    }

    private void initView() {
        topbar.setTitle(getResources().getString(R.string.attend_center));
        if (PublicConfig.isDebug) {
            mData = new ArrayList<>();
            mData.add(new TestAttend("活动1", "视频签到", "需签到/已签到/未签到: 10/3/7", "2021-4-2 12:00 -> 2021-4-2 12:00", "进行中"));
            mData.add(new TestAttend("活动2", "自助签到", "需签到/已签到/未签到: 1000/3/997", "2021-4-2 12:00 -> 2021-4-2 12:00", "进行中"));
            mData.add(new TestAttend("活动3", "视频签到", "需签到/已签到/未签到: 10/3/7", "2021-4-2 12:00 -> 2021-4-2 12:00", "未开始"));
            mData.add(new TestAttend("活动4", "自助签到 视频签到", "需签到/已签到/未签到: 10/3/7", "2021-4-2 12:00 -> 2021-4-2 12:00", "已结束"));
            mData.add(new TestAttend("活动5", "视频签到", "需签到/已签到/未签到: 10/3/7", "2021-4-2 12:00 -> 2021-4-2 12:00", "进行中"));
            mData.add(new TestAttend("活动6", "视频签到", "需签到/已签到/未签到: 10/3/7", "2021-4-2 12:00 -> 2021-4-2 12:00", "进行中"));
            mData.add(new TestAttend("活动7", "自助签到", "需签到/已签到/未签到: 1000/3/997", "2021-4-2 12:00 -> 2021-4-2 12:00", "进行中"));
            mData.add(new TestAttend("活动8", "视频签到", "需签到/已签到/未签到: 10/3/7", "2021-4-2 12:00 -> 2021-4-2 12:00", "未开始"));
            mData.add(new TestAttend("活动9", "自助签到 视频签到", "需签到/已签到/未签到: 10/3/7", "2021-4-2 12:00 -> 2021-4-2 12:00", "已结束"));
            mData.add(new TestAttend("活动10", "视频签到", "需签到/已签到/未签到: 10/3/7", "2021-4-2 12:00 -> 2021-4-2 12:00", "进行中"));
            MyAdapter adapter = new MyAdapter(getActivity(), mData);
            rv.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            rv.setLayoutManager(layoutManager);
            rv.addOnItemTouchListener(new OnRecyclerItemClickListener(rv) {
                @Override
                public void onItemClick(RecyclerView.ViewHolder vh) {
                    final TestAttend item = mData.get(vh.getAdapterPosition());
                    QMUIQuickAction qqa = QMUIPopups.quickAction(getContext(),
                            QMUIDisplayHelper.dp2px(getContext(), 56),
                            QMUIDisplayHelper.dp2px(getContext(), 56))
                            .shadow(true)
                            .skinManager(QMUISkinManager.defaultInstance(getContext()))
                            .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20));
                    qqa.addAction(new QMUIQuickAction.Action().text("查看详情").onClick(
                            new QMUIQuickAction.OnClickListener() {
                                @Override
                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                    quickAction.dismiss();
                                }
                            }
                    ));
                    qqa.addAction(new QMUIQuickAction.Action().text("自助签到").onClick(
                            new QMUIQuickAction.OnClickListener() {
                                @Override
                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                    quickAction.dismiss();
                                }
                            }
                    ));
                    qqa.addAction(new QMUIQuickAction.Action().text("视频签到").onClick(
                            new QMUIQuickAction.OnClickListener() {
                                @Override
                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                    quickAction.dismiss();
                                }
                            }
                    ));
                    qqa.show(((MyViewHolder)vh).tvTime);
                }
            });
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private Context mContext;
        private List<TestAttend> mData;

        public MyAdapter(Context context, List<TestAttend> testData) {
            mContext = context;
            mData = testData;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(mContext).inflate(R.layout.item_timeline_attend, parent, false);
            return new MyViewHolder(root);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            TestAttend item = mData.get(position);
            holder.tvName.setText(item.name);
            holder.tvType.setText(item.type);
            holder.tvCount.setText(item.count);
            holder.tvStatus.setText(item.status);
            holder.tvTime.setText(item.time);
            int statusTextColor = R.color.thing_default;
            if ("未开始".equals(item.status)) {
                statusTextColor = R.color.thing_not_begin;
                holder.ivCircle.setImageDrawable(mContext.getResources().getDrawable(R.drawable.circle_not_begin));
            }
            else if ("进行中".equals(item.status)) {
                statusTextColor = R.color.thing_ing;
                holder.ivCircle.setImageDrawable(mContext.getResources().getDrawable(R.drawable.circle_ing));
            }
            else if ("已结束".equals(item.status)) {
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

    private static class TestAttend {
        public String name;
        public String type;
        public String count;
        public String time;
        public String status;

        public TestAttend(String name, String type, String count, String time, String status) {
            this.name = name;
            this.type = type;
            this.count = count;
            this.time = time;
            this.status = status;
        }
    }

}
