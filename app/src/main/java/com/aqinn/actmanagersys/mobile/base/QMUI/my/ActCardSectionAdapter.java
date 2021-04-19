package com.aqinn.actmanagersys.mobile.base.QMUI.my;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseApplication;
import com.aqinn.actmanagersys.mobile.base.QMUI.QDLoadingItemView;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.model.AttendShow;
import com.aqinn.actmanagersys.mobile.myview.AnimationImageView;
import com.aqinn.actmanagersys.mobile.utils.ParseUtil;
import com.qmuiteam.qmui.widget.section.QMUIDefaultStickySectionAdapter;
import com.qmuiteam.qmui.widget.section.QMUISection;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 活动卡片SectionAdapter
 * 包含了一级列表项、二级列表项
 *
 * @author Aqinn
 * @date 2021/4/17 4:46 PM
 */
public class ActCardSectionAdapter extends QMUIDefaultStickySectionAdapter<SectionHeader_Act, SectionItem_Attend> {

    private static Map<Integer, String> mMap;

    static {
        mMap = new HashMap<>();
        mMap.put(0, "未开始");
        mMap.put(1, "进行中");
        mMap.put(2, "已结束");
    }

    public List<ActShow> actList;
    public Map<ActShow, List<AttendShow>> actAttendMap;
    public boolean isInCardDismiss = true;

    public ActCardSectionAdapter(List<ActShow> actList, Map<ActShow, List<AttendShow>> actAttendMap) {
        this.actList = actList;
        this.actAttendMap = actAttendMap;
    }

    public ActCardSectionAdapter(boolean removeSectionTitleIfOnlyOneSection) {
        super(removeSectionTitleIfOnlyOneSection);
    }

    @NonNull
    @Override
    protected SectionHeaderViewHolder onCreateSectionHeaderViewHolder(@NonNull ViewGroup viewGroup) {
        View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_create_act, viewGroup, false);
        return new SectionHeaderViewHolder(root);
    }

    @NonNull
    @Override
    protected SectionItemViewHolder onCreateSectionItemViewHolder(@NonNull ViewGroup viewGroup) {
        View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_timeline_attend_new, viewGroup, false);
        return new SectionItemViewHolder(root);
    }

    @NonNull
    @Override
    protected ViewHolder onCreateSectionLoadingViewHolder(@NonNull ViewGroup viewGroup) {
        return new ViewHolder(new QDLoadingItemView(viewGroup.getContext()));
    }

    @Override
    protected void onBindSectionHeader(ViewHolder holder, final int position, QMUISection<SectionHeader_Act, SectionItem_Attend> section) {
        /*
         * 需要做的事情
         * 绑定数据
         * 绑定点击事件，展开列表
         */
        SectionHeaderViewHolder realHolder = (SectionHeaderViewHolder) holder;
        SectionHeader_Act head = section.getHeader();
        ActShow act = head.getAct();
        realHolder.tvName.setText(act.getName());
        realHolder.tvTime.setText(act.getStartTime() + " -> " + act.getEndTime());
        realHolder.tvLoc.setText(act.getLocation());
        realHolder.tvIntro.setText(BaseApplication.getContext().getString(R.string.act_desc) + act.getDesc());
        String statusText = "";
        int statusTextColor = R.color.thing_default;
        if (2 == act.getStatus()) {
            statusText = BaseApplication.getContext().getResources().getString(R.string.act_ing);
            statusTextColor = R.color.thing_ing;
        }
        if (1 == act.getStatus()) {
            statusText = BaseApplication.getContext().getResources().getString(R.string.act_not_begin);
            statusTextColor = R.color.thing_not_begin;
        }
        if (3 == act.getStatus()) {
            statusText = BaseApplication.getContext().getResources().getString(R.string.act_end);
            statusTextColor = R.color.thing_finish;
        }
        realHolder.tvStatus.setText(statusText);
        realHolder.tvStatus.setTextColor(BaseApplication.getContext().getResources().getColor(statusTextColor));
        /*
        不要修改这里的touchListener为clickListener，也不要修改返回值，原因如下：
         使用OnTouchListener因为其比OnClickListener的优先级高，否则无法响应该事件
         因为会被com/aqinn/actmanagersys/mobile/base/QMUI/my/BaseSectionLayoutFragment.java
         中的mAdapter.setCallback({ void onItemClick() })覆盖
         返回false代表不拦截该事件，即click事件仍可以正常触发，正常逻辑下返回true让事件终止才对，
         但是QMUI这个组件的longClick判断貌似会受这个onTouch的返回值所影响，
         当返回true的时候，下一次点击这个holder.itemView就会触发longClick
         */
        realHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 写两层判断是为了方便看
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // 经过无数次测试，200ms的时长可以刚好不与长按事件重叠
                    if (event.getEventTime() - event.getDownTime() < 200) {
                        if (isInCardDismiss) {
                            if (section.getItemCount() == 0) {
                                Toast.makeText(BaseApplication.getContext(), "该活动没有签到了", Toast.LENGTH_SHORT).show();
                            } else {
                                int pos = holder.isForStickyHeader ? position : holder.getAdapterPosition();
                                toggleFold(pos, false);
                                if (section.isFold()) {
                                    realHolder.ivBackCard.setVisibility(View.VISIBLE);
                                    realHolder.ivBackCard.appear();
                                } else {
                                    realHolder.ivBackCard.setVisibility(View.GONE);
                                    realHolder.ivBackCard.disappear();
                                }
                            }
                        }
                    }
                }
                return false;
            }

        });
        if (isInCardDismiss) {
            if (section.getItemCount() == 0) {
                // Toast.makeText(BaseApplication.getContext(), "该活动没有签到了", Toast.LENGTH_SHORT).show();
            } else {
                if (section.isFold()) {
                    realHolder.ivBackCard.setVisibility(View.VISIBLE);
                    // realHolder.ivBackCard.appear();
                } else {
                    realHolder.ivBackCard.setVisibility(View.GONE);
                    // realHolder.ivBackCard.disappear();
                }
            }
        }
//        itemView.render(section.getHeader(), section.isFold());
//        itemView.getArrowView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pos = holder.isForStickyHeader ? position : holder.getAdapterPosition();
//                toggleFold(pos, false);
//            }
//        });
    }

    @Override
    protected void onBindSectionItem(ViewHolder holder, int position, QMUISection<SectionHeader_Act, SectionItem_Attend> section, int itemIndex) {
        /*
         * 需要做的事情
         * 绑定数据
         */
        SectionItemViewHolder realHolder = (SectionItemViewHolder) holder;
        SectionItem_Attend item = section.getItemAt(itemIndex);
        AttendShow attend = item.getAttend();
        if (attend == null) {
            attend = new AttendShow();
            attend.setName(attend.getName() + ", 这是一个null");
        }
        realHolder.tvType.setText(ParseUtil.getAttendType(attend.getAttendType()));
        realHolder.tvCount.setText(BaseApplication.getContext().getString(R.string.should_attend) + "/"
                + BaseApplication.getContext().getString(R.string.have_attend) + "/"
                + BaseApplication.getContext().getString(R.string.have_not_attend) + "/"
                + attend.getShouldAttendCount() + "/"
                + attend.getHaveAttendCount() + "/"
                + (attend.getShouldAttendCount() - attend.getHaveAttendCount()));
        String status = mMap.get(new Random().nextInt(3));
        realHolder.tvStatus.setText(status);
        realHolder.tvTime.setText(attend.getStartTime() + " -> " + attend.getEndTime());
        int statusTextColor = R.color.thing_default;
        if ("未开始".equals(status)) {
            statusTextColor = R.color.thing_not_begin;
        } else if ("进行中".equals(status)) {
            statusTextColor = R.color.thing_ing;
        } else if ("已结束".equals(status)) {
            statusTextColor = R.color.thing_finish;
        }
        realHolder.tvStatus.setTextColor(BaseApplication.getContext().getResources().getColor(statusTextColor));
    }

    public static class SectionHeaderViewHolder extends ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_loc)
        TextView tvLoc;
        //        @BindView(R.id.tv_intro_text)
//        TextView tvIntroText;
        @BindView(R.id.tv_intro)
        TextView tvIntro;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.cl_item_act_intro)
        ConstraintLayout clItemActIntro;
        @BindView(R.id.iv_back_card)
        AnimationImageView ivBackCard;

        public SectionHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public static class SectionItemViewHolder extends ViewHolder {

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

        public SectionItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
