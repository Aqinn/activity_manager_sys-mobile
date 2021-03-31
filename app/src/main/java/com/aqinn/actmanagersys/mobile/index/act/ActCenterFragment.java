package com.aqinn.actmanagersys.mobile.index.act;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.index.act.createact.CreateActFragment;
import com.aqinn.actmanagersys.mobile.index.act.joinact.JoinActFragment;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 活动中心 - View
 *
 * @author Aqinn
 * @date 2021/3/29 5:30 PM
 */
public class ActCenterFragment extends BaseFragment implements IActCenter.View {

    @BindView(R.id.topbar)
    QMUITopBarLayout topbar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tabSegment)
    QMUITabSegment tabSegment;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private IActCenter.Presenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_act, null);
        ButterKnife.bind(this, root);
        initView();
        return root;
    }

    private void initView() {
        topbar.setTitle(getResources().getString(R.string.act_center));
        List<Integer> imgList = new ArrayList<>();
        imgList.add(R.drawable.wxgg);
        imgList.add(R.drawable.wxgg);
        imgList.add(R.drawable.wxgg);
        banner.setAdapter(new BannerImageAdapter<Integer>(imgList) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer drawableId, int position, int size) {
                holder.imageView.setImageDrawable(getResources().getDrawable(drawableId));
            }
        }).addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(getActivity()));
        viewPager.setAdapter(new MyPageAdapter(getActivity().getSupportFragmentManager()));
        QMUITabBuilder builder = tabSegment.tabBuilder();
        tabSegment.addTab(builder.setText(getString(R.string.act_center_tab_1_title)).build(getContext()));
        tabSegment.addTab(builder.setText(getString(R.string.act_center_tab_2_title)).build(getContext()));
        tabSegment.setupWithViewPager(viewPager, false);
        tabSegment.setMode(QMUITabSegment.MODE_FIXED);
    }

    private static class MyPageAdapter extends QMUIFragmentPagerAdapter {
        public MyPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public QMUIFragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new CreateActFragment();
                case 1:
                    return new JoinActFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
