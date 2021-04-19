package com.aqinn.actmanagersys.mobile.index;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.base.QMUI.my.ActCardFragment;
import com.aqinn.actmanagersys.mobile.base.QMUI.my.ListWithDecorationSectionLayoutFragment;
import com.aqinn.actmanagersys.mobile.error.ErrorFragment;
import com.aqinn.actmanagersys.mobile.myview.TitleCenterToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主页 - Fragment
 *
 * @author Aqinn
 * @date 2021/4/5 3:13 PM
 */
public class IndexFragment extends BaseFragment implements IIndex.View {

    @BindView(R.id.pager)
    QMUIViewPager pager;
    @BindView(R.id.tabs)
    QMUITabSegment tabs;
    @BindView(R.id.toolbar)
    TitleCenterToolbar toolbar;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.fbt)
    FloatingActionButton fbt;

    private IIndex.Presenter mPresenter;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_index, null);
        ButterKnife.bind(this, root);
        mPresenter = new IndexPresenter(this, this);
        mPresenter.init(toolbar, pager, tabs, drawerLayout, navigationView);
        changeStatusBarTextColor(getActivity().getWindow(), true);
        return root;
    }

    @OnClick(R.id.fbt)
    public void onClick(View v) {
        mPresenter.showCreateMenuDialog(v);
    }

    public void changeStatusBarTextColor(Window window, boolean isBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = window.getDecorView();
            int flags = 0;
            if (isBlack) {
                flags = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }else {
                flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            }
            decor.setSystemUiVisibility(flags);
        }
    }

}
