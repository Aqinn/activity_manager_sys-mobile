package com.aqinn.actmanagersys.mobile.index;

import android.view.LayoutInflater;
import android.view.View;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.error.ErrorFragment;
import com.aqinn.actmanagersys.mobile.index.act.ActCenterFragment;
import com.aqinn.actmanagersys.mobile.index.attend.AttendCenterFragment;
import com.aqinn.actmanagersys.mobile.index.personal.PersonalFragment;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页 - Fragment
 *
 * @author Aqinn
 * @date 2021/4/5 3:13 PM
 */
public class IndexFragment extends BaseFragment {

    @BindView(R.id.pager)
    QMUIViewPager pager;
    @BindView(R.id.tabs)
    QMUITabSegment tabs;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_index, null);
        ButterKnife.bind(this, root);
        initView();
        return root;
    }

    private void initView() {
        QMUIFragmentPagerAdapter pagerAdapter = new QMUIFragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public QMUIFragment createFragment(int position) {
                switch (position) {
                    case 0:
//                        return new ErrorFragment("发生了错误", "活动中心还在动工ing");
                        return new ActCenterFragment();
                    case 1:
//                        return new ErrorFragment("发生了错误", "签到中心还在动工ing");
                        return new AttendCenterFragment();
                    case 2:
//                        return new ErrorFragment("发生了错误", "个人中心还在动工ing");
                        return new PersonalFragment();
                    case 3:
                    default:
                        return new ErrorFragment("发生了错误", "首页Fragment滑动超出范围");
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getResources().getString(R.string.act);
                    case 1:
                        return getResources().getString(R.string.checkin);
                    case 2:
                        return getResources().getString(R.string.personal);
                    default:
                        return getResources().getString(R.string.err_page);
                }
            }
        };
        pager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(pager);
    }

}
