package com.aqinn.actmanagersys.mobile.index;

import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;

import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.BaseNetworkService;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.model.InsertActMessage;
import com.aqinn.actmanagersys.mobile.model.JoinActResult;
import com.aqinn.actmanagersys.mobile.myview.TitleCenterToolbar;
import com.google.android.material.navigation.NavigationView;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

/**
 * 首页 - MVP接口
 *
 * @author Aqinn
 * @date 2021/4/18 7:43 PM
 */
public interface IIndex {

    interface View {

    }

    interface Presenter {
        void init(TitleCenterToolbar toolbar, QMUIViewPager pager, QMUITabSegment tabs,
                  DrawerLayout drawerLayout, NavigationView navigationView);
        void resetToolbarText();
        void showCreateMenuDialog(android.view.View view);
    }

    interface Model extends BaseNetworkService {
        void joinAct(String code, String pwd, JoinActCallback callback);
    }

    interface JoinActCallback{
        void onSuccess(JoinActResult joinActResult);
        void onError();
    }


}
