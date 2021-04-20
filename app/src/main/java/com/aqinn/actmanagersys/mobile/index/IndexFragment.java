package com.aqinn.actmanagersys.mobile.index;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.drawerlayout.widget.DrawerLayout;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.myview.TitleCenterToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
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

    @Override
    public void onStart() {
        super.onStart();
        // 这两句话（下面那句在onStop()）不要删，用于修改主页Fragment的状态栏颜色，删了别的地方就没有沉浸式状态栏的效果了
        comeIndexChangeStatusBarTextColor();
    }

    @Override
    public void onStop() {
        super.onStop();
        // 这两句话（下面那句在onStop()）不要删，用于修改主页Fragment的状态栏颜色，删了别的地方就没有沉浸式状态栏的效果了
        leaveIndexChangeStatusBarTextColor();
    }

    @OnClick(R.id.fbt)
    public void onClick(View v) {
        mPresenter.showCreateMenuDialog(v);
    }

}
