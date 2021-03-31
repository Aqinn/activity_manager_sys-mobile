package com.aqinn.actmanagersys.mobile.index;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.error.ErrorFragment;
import com.aqinn.actmanagersys.mobile.index.act.ActCenterFragment;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页
 * @author Aqinn
 * @date 2021/3/29 5:04 PM
 */
public class IndexActivity extends BaseFragmentActivity {

    @BindView(R.id.pager)
    QMUIViewPager pager;
    @BindView(R.id.tabs)
    QMUITabSegment tabs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        QMUIFragmentPagerAdapter pagerAdapter = new QMUIFragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public QMUIFragment createFragment(int position) {
                switch (position) {
                    case 0:
                        // TODO 活动中心
//                        return new ErrorFragment("发生了错误", "活动中心还在动工ing");
                        return new ActCenterFragment();
                    case 1:
                        // TODO 签到中心
                        return new ErrorFragment("发生了错误", "签到中心还在动工ing");
                    case 2:
                        // TODO 个人中心
                        return new ErrorFragment("发生了错误", "个人中心还在动工ing");
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
