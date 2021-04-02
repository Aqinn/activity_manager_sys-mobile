package com.aqinn.actmanagersys.mobile.index.act.joinact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.OnRecyclerItemClickListener;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.index.act.createact.CreateActFragment;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.popup.QMUIQuickAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 参加的活动 - View
 *
 * @author Aqinn
 * @date 2021/3/30 10:20 AM
 */
public class JoinActFragment extends BaseFragment {


    @BindView(R.id.rv_join)
    RecyclerView rvJoin;

    private List<ActShow> mData;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_join_act, null);
        ButterKnife.bind(this, root);
        initView();
        return root;
    }

    private void initView() {
        // 测试用，填充测试数据
        if (PublicConfig.isDebug) {
            mData = new ArrayList<>();
            mData.add(new ActShow("参加活动的阿巴阿巴"));
            mData.add(new ActShow("参加活动的古卡古卡"));
            mData.add(new ActShow("参加活动的唔西迪西"));
            mData.add(new ActShow("参加活动的玛卡巴卡"));
            MyAdapter adapter = new MyAdapter(getActivity(), mData);
            rvJoin.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            rvJoin.setLayoutManager(layoutManager);
            rvJoin.addOnItemTouchListener(new OnRecyclerItemClickListener(rvJoin) {
                @Override
                public void onItemClick(RecyclerView.ViewHolder vh) {
                    final ActShow item = mData.get(vh.getAdapterPosition());
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
                    qqa.addAction(new QMUIQuickAction.Action().text("活动代码").onClick(
                            new QMUIQuickAction.OnClickListener() {
                                @Override
                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                    quickAction.dismiss();
                                }
                            }
                    ));
                    qqa.addAction(new QMUIQuickAction.Action().text("退出活动").onClick(
                            new QMUIQuickAction.OnClickListener() {
                                @Override
                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                    quickAction.dismiss();
                                }
                            }
                    ));
                    qqa.show(vh.itemView);
                }
            });
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
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
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private Context mContext;
        private List<ActShow> mData;

        public MyAdapter(Context context, List<ActShow> testData) {
            mContext = context;
            mData = testData;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(mContext).inflate(R.layout.item_create_act, parent, false);
            return new MyViewHolder(root);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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

}
