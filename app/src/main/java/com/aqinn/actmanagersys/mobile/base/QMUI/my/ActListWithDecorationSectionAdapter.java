package com.aqinn.actmanagersys.mobile.base.QMUI.my;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.QMUI.SectionHeader;
import com.aqinn.actmanagersys.mobile.base.QMUI.SectionItem;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.model.AttendShow;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.section.QMUISection;
import com.qmuiteam.qmui.widget.section.QMUISectionDiffCallback;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 带有装饰区布局片段的活动列表
 *
 * @author Aqinn
 * @date 2021/4/17 5:44 PM
 */
public class ActListWithDecorationSectionAdapter extends ActCardSectionAdapter {

    private static final String TAG = "ActListWithDecorationSe";

    public static final int ITEM_INDEX_LIST_HEADER = -1;
    public static final int ITEM_INDEX_LIST_FOOTER = -2;
    public static final int ITEM_INDEX_SECTION_TIP_START = -3;
    public static final int ITEM_INDEX_SECTION_TIP_END = -4;

    public static final int ITEM_TYPE_LIST_HEADER = 1;
    public static final int ITEM_TYPE_LIST_FOOTER = 2;
    public static final int ITEM_TYPE_SECTION_TIP_START = 3;
    public static final int ITEM_TYPE_SECTION_TIP_END = 4;

    private BaseFragment mFragment;

    public ActListWithDecorationSectionAdapter(List<ActShow> actList, Map<ActShow, List<AttendShow>> actAttendMap, BaseFragment mFragment) {
        super(actList, actAttendMap);
        this.mFragment = mFragment;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateCustomItemViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View view;
        Context context = viewGroup.getContext();
        // 整个列表的最顶部
        if (type == ITEM_TYPE_LIST_HEADER) {
            // 设置轮播图
//            ImageView iv = new ImageView(context);
//            iv.setImageResource(R.mipmap.example_image2);
//            view = iv;

//            Banner banner = new Banner(context);
//            List<Integer> imgList = new ArrayList<>();
//            imgList.add(R.drawable.wxgg);
//            imgList.add(R.drawable.wxgg);
//            imgList.add(R.drawable.wxgg);
//            banner.setAdapter(new BannerImageAdapter<Integer>(imgList) {
//                @Override
//                public void onBindView(BannerImageHolder holder, Integer drawableId, int position, int size) {
//                    Drawable drawable = context.getResources().getDrawable(drawableId);
//                    holder.imageView.setImageDrawable(drawable);
//                    holder.imageView.setMaxWidth(10);
//                    holder.imageView.setMaxHeight(10);
//                }
//            }).addBannerLifecycleObserver(mFragment)
//                    .setIndicator(new CircleIndicator(context))
//                    .setBannerRound(20);
//            view = banner;

            TextView tv = new TextView(context);
            tv.setTextSize(12);
            tv.setText(" ");
            int padding = QMUIDisplayHelper.dp2px(context, 0);
            tv.setPadding(0, padding, 0, padding);
            view = tv;

//            TextView tv = new TextView(context);
//            tv.setTextSize(12);
//            tv.setBackgroundColor(ContextCompat.getColor(context, R.color.qmui_config_color_white));  // qmui_config_color_gray_9
//            TypedValue typedValue = new TypedValue();
//            context.getTheme().resolveAttribute(R.attr.qmui_skin_support_color_background, typedValue, true);
//            tv.setBackgroundColor(ContextCompat.getColor(context, typedValue.resourceId));  // qmui_config_color_gray_9
//            tv.setTextColor(Color.DKGRAY);
//            tv.setText(R.string.sticky_section_decoration_section_top_tip);
//            tv.setGravity(Gravity.CENTER);
//            int paddingTop = QMUIDisplayHelper.dp2px(context, 16);
//            int paddingBottom = QMUIDisplayHelper.dp2px(context, 16);
//            tv.setPadding(0, paddingTop, 0, paddingBottom);
//            view = tv;
        }
        // 整个列表的最底部
        else if (type == ITEM_TYPE_LIST_FOOTER) {
            TextView tv = new TextView(context);
            tv.setTextSize(12);
            tv.setBackgroundColor(ContextCompat.getColor(context, R.color.qmui_config_color_white));  // qmui_config_color_gray_9
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.qmui_skin_support_color_background, typedValue, true);
            tv.setBackgroundColor(ContextCompat.getColor(context, typedValue.resourceId));  // qmui_config_color_gray_9
            tv.setTextColor(Color.DKGRAY);
            tv.setText(R.string.sticky_section_decoration_list_footer);
            tv.setGravity(Gravity.CENTER);
            int paddingTop = QMUIDisplayHelper.dp2px(context, 16);
            int paddingBottom = QMUIDisplayHelper.dp2px(context, 16);
            tv.setPadding(0, paddingTop, 0, paddingBottom);
            view = tv;
//            view = new View(viewGroup.getContext());
        }
        // 列表中一级item的头部
        else if (type == ITEM_TYPE_SECTION_TIP_START) {
//            TextView tv = new TextView(context);
//            tv.setTextSize(12);
//            tv.setBackgroundColor(ContextCompat.getColor(context, R.color.qmui_config_color_gray_9));
//            tv.setTextColor(Color.DKGRAY);
//            tv.setText(R.string.sticky_section_decoration_section_top_tip);
//            tv.setGravity(Gravity.CENTER);
//            int paddingVer = QMUIDisplayHelper.dp2px(context, 16);
//            tv.setPadding(0, paddingVer, 0, paddingVer);
//            view = tv;
            view = new View(viewGroup.getContext());
        }
        // 列表中一级item的底部
        else if (type == ITEM_TYPE_SECTION_TIP_END) {
            TextView tv = new TextView(context);
            tv.setTextSize(12);
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.qmui_skin_support_color_background, typedValue, true);
            tv.setBackgroundColor(ContextCompat.getColor(context, typedValue.resourceId));  // qmui_config_color_gray_9
            tv.setTextColor(Color.DKGRAY);
            tv.setText(R.string.sticky_section_decoration_section_bottom_tip);
            tv.setGravity(Gravity.CENTER);
            int paddingTop = QMUIDisplayHelper.dp2px(context, 0);
            int paddingBottom = QMUIDisplayHelper.dp2px(context, 10);
            tv.setPadding(0, paddingTop, 0, paddingBottom);
            view = tv;
//            view = new View(viewGroup.getContext());
        } else {
            view = new View(viewGroup.getContext());
        }
        return new QMUIStickySectionAdapter.ViewHolder(view);
    }

    @Override
    protected int getCustomItemViewType(int itemIndex, int position) {
        if (itemIndex == ITEM_INDEX_LIST_HEADER) {
            return ITEM_TYPE_LIST_HEADER;
        } else if (itemIndex == ITEM_INDEX_LIST_FOOTER) {
            return ITEM_TYPE_LIST_FOOTER;
        } else if (itemIndex == ITEM_INDEX_SECTION_TIP_START) {
            return ITEM_TYPE_SECTION_TIP_START;
        } else if (itemIndex == ITEM_INDEX_SECTION_TIP_END) {
            return ITEM_TYPE_SECTION_TIP_END;
        }
        return super.getCustomItemViewType(itemIndex, position);
    }

    @Override
    protected QMUISectionDiffCallback<SectionHeader_Act, SectionItem_Attend> createDiffCallback(
            List<QMUISection<SectionHeader_Act, SectionItem_Attend>> lastData,
            List<QMUISection<SectionHeader_Act, SectionItem_Attend>> currentData) {
        return new QMUISectionDiffCallback<SectionHeader_Act, SectionItem_Attend>(lastData, currentData) {

            @Override
            protected void onGenerateCustomIndexBeforeSectionList(IndexGenerationInfo generationInfo, List<QMUISection<SectionHeader_Act, SectionItem_Attend>> list) {
                generationInfo.appendWholeListCustomIndex(ITEM_INDEX_LIST_HEADER);
            }

            @Override
            protected void onGenerateCustomIndexAfterSectionList(IndexGenerationInfo generationInfo, List<QMUISection<SectionHeader_Act, SectionItem_Attend>> list) {
                generationInfo.appendWholeListCustomIndex(ITEM_INDEX_LIST_FOOTER);
            }

            @Override
            protected void onGenerateCustomIndexBeforeItemList(IndexGenerationInfo generationInfo,
                                                               QMUISection<SectionHeader_Act, SectionItem_Attend> section,
                                                               int sectionIndex) {
                if (!section.isExistBeforeDataToLoad()) {
                    generationInfo.appendCustomIndex(sectionIndex, ITEM_INDEX_SECTION_TIP_START);
                }
            }

            @Override
            protected void onGenerateCustomIndexAfterItemList(IndexGenerationInfo generationInfo,
                                                              QMUISection<SectionHeader_Act, SectionItem_Attend> section,
                                                              int sectionIndex) {
                if (!section.isExistAfterDataToLoad()) {
                    generationInfo.appendCustomIndex(sectionIndex, ITEM_INDEX_SECTION_TIP_END);
                }
            }

            @Override
            protected boolean areCustomContentsTheSame(@Nullable QMUISection<SectionHeader_Act, SectionItem_Attend> oldSection, int oldItemIndex, @Nullable QMUISection<SectionHeader_Act, SectionItem_Attend> newSection, int newItemIndex) {
                return true;
            }
        };
    }


}
