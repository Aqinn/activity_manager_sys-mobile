package com.aqinn.actmanagersys.mobile.index;

import android.content.res.ColorStateList;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.aqinn.actmanagersys.mobile.index.actcenter.actdetail.ActDetailFragment;
import com.aqinn.actmanagersys.mobile.index.actcenter.joinact.JoinActDialogBuilder;
import com.aqinn.actmanagersys.mobile.myview.TitleCenterToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * 首页 - Presenter
 *
 * @author Aqinn
 * @date 2021/4/18 7:45 PM
 */
public class IndexPresenter implements IIndex.Presenter {

    private IIndex.View mView;

    QMUIViewPager pager;
    QMUITabSegment tabs;
    TitleCenterToolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    private IndexFragment mFragment;

    private QMUIPopup mGlobalAction;

    public IndexPresenter(IIndex.View view, IndexFragment fragment) {
        mView = view;
        mFragment = fragment;
    }

    @Override
    public void init(TitleCenterToolbar toolbar, QMUIViewPager pager, QMUITabSegment tabs, DrawerLayout drawerLayout, NavigationView navigationView) {
        this.toolbar = toolbar;
        this.pager = pager;
        this.tabs = tabs;
        this.drawerLayout = drawerLayout;
        this.navigationView = navigationView;
        initToolbar();
        initPagerAndTab();
        initDrawerLayout();
    }

    private void initToolbar() {
//        toolbar.setTitle("叮咚活动");
//        toolbar.setSubtitle("活动创建者");
        toolbar.setMyCenterTitle("早上好, ElonZhong");
        toolbar.setMyCenterTextColor(mFragment.getResources().getColor(R.color.cookie_black, mFragment.getActivity().getTheme()));
//        toolbar.setMySettingIcon(mFragment.getResources().getDrawable(R.drawable.role_switch, mFragment.getActivity().getTheme()));
//        toolbar.setMySettingText("活动创建者");
//        toolbar.setSettingIconOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(v, "当前是活动创建者,请在设置中切换身份", Snackbar.LENGTH_SHORT)
//                        .setAction("快捷切换", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(getActivity(), "当前身份是活动创建者, 请在设置中切换身份", Toast.LENGTH_SHORT).show();
//                            }
//                        }).show();

//            }
//        });
        // mFragment.getBaseFragmentActivity().setSupportActionBar(toolbar);  // 将toolbar与ActionBar关联
    }

    private void initPagerAndTab() {
        QMUIFragmentPagerAdapter pagerAdapter = new QMUIFragmentPagerAdapter(mFragment.getChildFragmentManager()) {
            @Override
            public QMUIFragment createFragment(int position) {
                switch (position) {
                    case 0:
//                        return new ErrorFragment("发生了错误", "活动中心还在动工ing");
//                        return new ActCenterFragment();
                        return new ActCardFragment();
                    case 1:
//                        return new ErrorFragment("发生了错误", "签到中心还在动工ing");
//                        return new AttendCenterFragment();
                        return new ActCardFragment();
                    case 2:
//                        return new ErrorFragment("发生了错误", "个人中心还在动工ing");
                        // return new PersonalFragment();
                        // return new QDListWithDecorationSectionLayoutFragment();
                        return new ListWithDecorationSectionLayoutFragment();
                    case 3:
                    default:
                        return new ErrorFragment("发生了错误", "首页Fragment滑动超出范围");
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return mFragment.getResources().getString(R.string.tab_create_act);
                    case 1:
                        return mFragment.getResources().getString(R.string.tab_join_act);
                    case 2:
                        return mFragment.getResources().getString(R.string.personal);
                    default:
                        return mFragment.getResources().getString(R.string.err_page);
                }
            }
        };
        pager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(pager);

        // 设置底部tab消失，记得要去fragment_index.xml中把ViewPager的margin_bottom设为原来的值（原来的值已经注释掉）
//        tabs.setVisibility(View.GONE);

    }

    private void initDrawerLayout() {
        toggle = new ActionBarDrawerToggle(
                mFragment.getActivity(), drawerLayout, toolbar, 0, 0);
        drawerLayout.setDrawerListener(toggle);//初始化状态
        toggle.syncState();

        /*---------------------------添加头布局和尾布局-----------------------------*/
        //获取xml头布局view
        View headerView = navigationView.getHeaderView(0);
        //添加头布局的另外一种方式
        //View headview=navigationview.inflateHeaderView(R.layout.navigationview_header);

        //寻找头部里面的控件
        ImageView imageView = headerView.findViewById(R.id.iv_head);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PublicConfig.isDebug)
                    Toast.makeText(mFragment.getActivity(), "Test: 点击了头像", Toast.LENGTH_LONG).show();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
        ColorStateList csl = (ColorStateList) mFragment.getResources().getColorStateList(R.color.black_text);  // app_color_blue
        //设置item的条目颜色
        navigationView.setItemTextColor(csl);
        //去掉默认颜色显示原来颜色  设置为null显示本来图片的颜色
        navigationView.setItemIconTintList(csl);

        //设置条目点击监听
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //安卓
                if (PublicConfig.isDebug)
                    Toast.makeText(mFragment.getActivity(), menuItem.getTitle(), Toast.LENGTH_LONG).show();
                //设置哪个按钮被选中
//                menuItem.setChecked(true);
                //关闭侧边栏
//                drawer.closeDrawers();
                return false;
            }
        });
    }

    /**
     * 展示创建活动的弹窗
     *
     * @param view
     */
    @Override
    public void showCreateMenuDialog(View view) {
        String[] listItems = new String[]{
                mFragment.getResources().getString(R.string.act_center_fb_create_act),
                mFragment.getResources().getString(R.string.act_center_fb_join_act)
        };
        List<String> data = new ArrayList<>();

        Collections.addAll(data, listItems);

        ArrayAdapter adapter = new ArrayAdapter<>(mFragment.getActivity(), R.layout.item_simple_list, data);
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    // 创建活动
                    ActDetailFragment fragment = new ActDetailFragment(null, false, true);
                    mFragment.startFragment(fragment);
                } else if (i == 1) {
                    // 加入活动
                    // oldShowJoinActDialog(context);
                    showJoinActDialog(mFragment);
                }
                if (mGlobalAction != null)
                    mGlobalAction.dismiss();
            }
        };
        mGlobalAction = QMUIPopups.listPopup(mFragment.getActivity(),
                QMUIDisplayHelper.dp2px(mFragment.getActivity(), 100),
                QMUIDisplayHelper.dp2px(mFragment.getActivity(), 300),
                adapter,
                onItemClickListener)
                .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                .preferredDirection(QMUIPopup.DIRECTION_TOP)
                .shadow(true)
                .edgeProtection(QMUIDisplayHelper.dp2px(mFragment.getActivity(), 10))
                .offsetYIfTop(QMUIDisplayHelper.dp2px(mFragment.getActivity(), 5))
                .skinManager(QMUISkinManager.defaultInstance(mFragment.getActivity()))
                .show(view);
    }

    /**
     * 弹窗加入活动
     */
    private void showJoinActDialog(BaseFragment fragment) {
        JoinActDialogBuilder builder = new JoinActDialogBuilder(fragment.getActivity());
        builder.setTitle(fragment.getResources().getString(R.string.join_act))
                .setSkinManager(QMUISkinManager.defaultInstance(fragment.getActivity()))
                .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        if (PublicConfig.isDebug)
                            Toast.makeText(fragment.getActivity(), "Test: 点击确定", Toast.LENGTH_SHORT).show();
                        if (builder.getCode() == null || builder.getPwd() == null ||
                                builder.getCode().length() != 6 || builder.getPwd().length() != 6) {
                            Toast.makeText(fragment.getActivity(), "请正确填写活动代码或密码", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        dialog.dismiss();
                    }
                })
                .addAction(0, "取消", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create(PublicConfig.mCurrentDialogStyle).show();
    }

}
