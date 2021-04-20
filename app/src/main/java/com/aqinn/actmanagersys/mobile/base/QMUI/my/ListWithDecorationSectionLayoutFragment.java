package com.aqinn.actmanagersys.mobile.base.QMUI.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.actcard.ActListWithDecorationSectionAdapter;
import com.aqinn.actmanagersys.mobile.actcard.IActCard;
import com.aqinn.actmanagersys.mobile.actcard.SectionHeader_Act;
import com.aqinn.actmanagersys.mobile.actcard.SectionItem_Attend;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.model.AttendShow;
import com.qmuiteam.qmui.widget.section.QMUISection;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * 活动卡片列表 - View
 * 带有装饰区的Section布局Fragment
 *
 * @author Aqinn
 * @date 2021/4/17 6:00 PM
 */
@Deprecated
public class ListWithDecorationSectionLayoutFragment extends BaseSectionLayoutFragment implements IActCard.View {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected QMUIStickySectionAdapter<SectionHeader_Act, SectionItem_Attend, QMUIStickySectionAdapter.ViewHolder> createAdapter() {
        List<ActShow> actList = new ArrayList<>();
        Map<ActShow, List<AttendShow>> actAttendMap = new HashMap<>();
        prepareData(actList, actAttendMap);
        mAdapter = new ActListWithDecorationSectionAdapter(null, actList, actAttendMap);
        List<QMUISection<SectionHeader_Act, SectionItem_Attend>> data = new ArrayList<>();
        wrapData(actList, actAttendMap, data);
        mAdapter.setData(data);
        mAdapter.setCallback(new QMUIStickySectionAdapter.Callback<SectionHeader_Act, SectionItem_Attend>() {
            /**
             * 尝试加载更多的Attend
             * @param section
             * @param loadMoreBefore
             */
            @Override
            public void loadMore(final QMUISection<SectionHeader_Act, SectionItem_Attend> section, final boolean loadMoreBefore) {
                mSectionLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isAttachedToActivity()) {
                            ArrayList<SectionItem_Attend> list = new ArrayList<>();
                            for (int i = 0; i < 3; i++) {
                                AttendShow attend = new AttendShow();
                                attend.setName(attend.getName() + ", No." + i);
                                list.add(new SectionItem_Attend(attend));
                            }
                            mAdapter.finishLoadMore(section, list, loadMoreBefore, false);
                        }
                    }
                }, 800);
            }

            @Override
            public void onItemClick(QMUIStickySectionAdapter.ViewHolder holder, int position) {
                // 如果是item就...
                if (mAdapter.getItemViewType(position) == QMUIStickySectionAdapter.ITEM_TYPE_SECTION_ITEM) {
                    if (PublicConfig.isDebug) {
                        Toast.makeText(getContext(), "Test: 是item", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public boolean onItemLongClick(QMUIStickySectionAdapter.ViewHolder holder, int position) {
                if (PublicConfig.isDebug)
                    Toast.makeText(getContext(), "Test: \nlong click item " + position + "\n"
                            + "mAdapter.getItemIndex(position)" + mAdapter.getItemIndex(position) + "\n"
                            + "mAdapter.getSectionIndex(position)" + mAdapter.getSectionIndex(position) + "\n"
                            + "mAdapter.getItemViewType(position)" + mAdapter.getItemViewType(position) + "\n", Toast.LENGTH_SHORT).show();
                // 如果是header就附加一个菜单
                if (mAdapter.getItemViewType(position) == QMUIStickySectionAdapter.ITEM_TYPE_SECTION_HEADER) {
                    if (PublicConfig.isDebug) {
                        Toast.makeText(getContext(), "Test: 是header", Toast.LENGTH_SHORT).show();
                    }
                }
//                ActCardSectionAdapter.SectionHeaderViewHolder realHolder = (ActCardSectionAdapter.SectionHeaderViewHolder) holder;
//                QMUISection<SectionHeader_Act, SectionItem_Attend> section = mAdapter.getSection(position);
//
//                SectionHeader_Act sectionHeaderAct = section.getHeader();
//                ActShow act = sectionHeaderAct.getAct();
                return true;
            }
        });
        return mAdapter;
    }

    /**
     * 准备初始数据
     * 数据类型为ActShow/AttendShow
     * 需要结合以下函数调用 {@link ListWithDecorationSectionLayoutFragment#wrapData(List, Map, List)}
     *
     * @param actList
     * @param actAttendMap
     */
    private void prepareData(List<ActShow> actList, Map<ActShow, List<AttendShow>> actAttendMap) {
        for (int i = 1; i <= 5; i++) {
            ActShow act = new ActShow();
            act.setName(act.getName() + ", Act.No." + i);
            actList.add(act);
            List<AttendShow> attendList = new ArrayList<>();
            if (i != 3) {
                int jSize = new Random().nextInt(3) + 1;
                for (int j = 1; j <= jSize; j++) {
                    AttendShow attend = new AttendShow();
                    attend.setName(attend.getName() + ", Attend.No." + i);
                    attendList.add(attend);
                }
            }
            actAttendMap.put(act, attendList);
        }
    }

    /**
     * 包装活动与签到数据
     * 将数据载体类包装成展示数据类
     * ActShow -> SectionHeader_Act
     * AttendShow -> SectionItem_Attend
     *
     * @param actList
     * @param actAttendMap
     * @param data
     */
    private void wrapData(List<ActShow> actList, Map<ActShow, List<AttendShow>> actAttendMap,
                          List<QMUISection<SectionHeader_Act, SectionItem_Attend>> data) {
        for (ActShow act : actList) {
            if (actAttendMap.containsKey(act)) {
                data.add(createSection(act, Objects.requireNonNull(actAttendMap.get(act)), true));
            }
        }
    }

    /**
     * 根据ActShow/AttendShow创建SectionHeader_Act/SectionItem_attend
     *
     * @param act
     * @param attendList
     * @param isFold
     * @return
     */
    private QMUISection<SectionHeader_Act, SectionItem_Attend> createSection(ActShow act, List<AttendShow> attendList, boolean isFold) {
        SectionHeader_Act header = new SectionHeader_Act(act);
        ArrayList<SectionItem_Attend> contents = new ArrayList<>();
        for (AttendShow attend : attendList) {
            contents.add(new SectionItem_Attend(attend));
        }
        QMUISection<SectionHeader_Act, SectionItem_Attend> section = new QMUISection<>(header, contents, isFold);
        // if test load more, you can open the code
        section.setExistAfterDataToLoad(false);
//        section.setExistBeforeDataToLoad(true);
        return section;
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
    }

}
