package com.aqinn.actmanagersys.mobile.index.actcenter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.index.actcenter.mycreateact.MyCreateActFragment;
import com.aqinn.actmanagersys.mobile.index.actcenter.myjoinact.MyJoinActFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 活动中心 - View
 *
 * @author Aqinn
 * @date 2021/3/29 5:30 PM
 */
public class ActCenterFragment extends BaseFragment implements IActCenter.View {

//    @BindView(R.id.topbar)
//    QMUITopBarLayout topbar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tabSegment)
    QMUITabSegment tabSegment;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.fbt)
    public FloatingActionButton fbt;

    private IActCenter.Presenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_act_center, null);
        ButterKnife.bind(this, root);
        initView();
        mPresenter = new ActCenterPresenter(this);
        mPresenter.initBanner(this, banner);
        return root;
    }

    private void initView() {
//        topbar.setTitle(getResources().getString(R.string.act_center));
        viewPager.setAdapter(new MyPageAdapter(getChildFragmentManager()));
        QMUITabBuilder builder = tabSegment.tabBuilder();
        tabSegment.addTab(builder.setText(getString(R.string.act_center_tab_1_title)).build(getContext()));
        tabSegment.addTab(builder.setText(getString(R.string.act_center_tab_2_title)).build(getContext()));
        tabSegment.setupWithViewPager(viewPager, false);
        tabSegment.setMode(QMUITabSegment.MODE_FIXED);
    }

    @OnClick({R.id.fbt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fbt:
//                if (PublicConfig.isDebug)
//                    Toast.makeText(getActivity(), "Test: 点击了浮动按钮", Toast.LENGTH_SHORT).show();
                mPresenter.showCreateMenuDialog(this, view);
                break;
        }
    }

    private static class MyPageAdapter extends QMUIFragmentPagerAdapter {
        public MyPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public QMUIFragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new MyCreateActFragment();
                case 1:
                    return new MyJoinActFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
