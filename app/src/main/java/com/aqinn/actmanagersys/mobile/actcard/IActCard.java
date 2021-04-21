package com.aqinn.actmanagersys.mobile.actcard;

import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.BaseNetworkService;
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

        void insertAct(int position, ActShow act, List<AttendShow> attendList);
    }

    interface Model extends BaseNetworkService {
        List<QMUISection<SectionHeader_Act, SectionItem_Attend>> getData();

        List<ActShow> getActList();

        Map<ActShow, List<AttendShow>> getActAttendMap();

        void initData(InitDataCallback callback);

        void refreshData(InitDataCallback callback);

        void deleteAct(int sectionHeaderIndex, DeleteActCallback callback);

        void updateAct(int sectionHeaderIndex, ActShow newAct, UpdateActCallback callback);

        void insertAct(int position, ActShow newAct, List<AttendShow> attendList, InsertActCallback callback);

        void quitAct(int sectionHeaderIndex, QuitActCallback callback);

        void deleteAttend(int sectionHeaderIndex, int sectionItemIndex, DeleteAttendCallback callback);

        void updateAttendType(int sectionHeaderIndex, int sectionItemIndex, AttendShow attend, UpdateAttendTypeCallback callback);

        void updateAttendTime(int sectionHeaderIndex, int sectionItemIndex, AttendShow attend, UpdateAttendTimeCallback callback);

        void insertAttend(int sectionHeaderIndex, boolean isLoadBefore, AttendShow attend, InsertAttendCallback callback);

        interface InitDataCallback {
            void onSuccess(String msg);

            void onError(ErrorCode errorCode);
        }

        interface RefreshDataCallback {
            void onSuccess(String msg);

            void onError(ErrorCode errorCode);
        }

        interface DeleteActCallback {
            void onSuccess();

            void onError();
        }

        interface UpdateActCallback {
            void onSuccess();

            void onError();
        }

        interface InsertActCallback {
            void onSuccess();

            void onError();
        }

        interface QuitActCallback {
            void onSuccess();

            void onError();
        }

        interface DeleteAttendCallback {
            void onSuccess();

            void onError();
        }

        interface UpdateAttendTimeCallback {
            void onSuccess();

            void onError();
        }

        interface UpdateAttendTypeCallback {
            void onSuccess();

            void onError();
        }

        interface InsertAttendCallback {
            void onSuccess(AttendShow attend);

            void onError();
        }

    }

    enum ErrorCode {
        INCOMPLETE_DATA("不完整的数据"),
        FAIL_LOAD_CREATE_ACT("创建的活动加载失败"),
        FAIL_LOAD_JOIN_ACT("加入的活动加载失败"),
        FAIL_LOAD_CREATE_ATTEND("创建的签到加载失败"),
        FAIL_LOAD_JOIN_ATTEND("加入的签到加载失败"),
        UNKNOWN_RESPONSE_ERROR("未知响应错误"),
        UNKNOWN_NETWORK_ERROR("未知网络错误");
        public String desc;

        ErrorCode(String desc) {
            this.desc = desc;
        }
    }

}
