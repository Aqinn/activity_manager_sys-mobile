package com.aqinn.actmanagersys.mobile.actcard;

import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.model.AttendShow;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.qmuiteam.qmui.widget.section.QMUISection;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionLayout;

import java.util.List;
import java.util.Map;

/**
 * 活动卡片列表界面 - MVP接口
 *
 * @author Aqinn
 * @date 2021/4/18 4:32 PM
 */
public interface IActCard {

    interface View {
    }

    interface Presenter {
        /**
         * 初始化所有组件
         * 在Presenter中保留所有组件的引用
         *
         * @param fragment
         * @param topBar
         * @param pullRefreshLayout
         * @param sectionLayout
         */
        void init(BaseFragment fragment, QMUITopBarLayout topBar, QMUIPullRefreshLayout pullRefreshLayout, QMUIStickySectionLayout sectionLayout);
    }

    interface Model {
        List<QMUISection<SectionHeader_Act, SectionItem_Attend>> getData();

        List<ActShow> getActList();

        Map<ActShow, List<AttendShow>> getActAttendMap();

        boolean deleteAct(int sectionHeaderIndex);

        boolean updateAct(int sectionHeaderIndex, ActShow newAct);

        // 头插
        boolean insertAct(int position, ActShow newAct, List<AttendShow> attendList);

        boolean deleteAttend(int sectionHeaderIndex, int sectionItemIndex);

        boolean updateAttend();

        boolean insertAttend(int sectionHeaderIndex, boolean isLoadBefore, AttendShow attend);
    }

}
