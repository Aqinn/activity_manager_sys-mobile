package com.aqinn.actmanagersys.mobile.index.act;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.index.act.actdetail.ActDetailFragment;
import com.aqinn.actmanagersys.mobile.index.act.createact.CreateActFragment;
import com.aqinn.actmanagersys.mobile.index.act.joinact.JoinActFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter;
import com.qmuiteam.qmui.layout.QMUIFrameLayout;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.popup.QMUIFullScreenPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
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
    @BindView(R.id.fbt)
    FloatingActionButton fbt;

    private IActCenter.Presenter mPresenter;

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

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
        topbar.setTitle(getResources().getString(R.string.act_center));
        viewPager.setAdapter(new MyPageAdapter(getChildFragmentManager()));
        QMUITabBuilder builder = tabSegment.tabBuilder();
        tabSegment.addTab(builder.setText(getString(R.string.act_center_tab_1_title)).build(getContext()));
        tabSegment.addTab(builder.setText(getString(R.string.act_center_tab_2_title)).build(getContext()));
        tabSegment.setupWithViewPager(viewPager, false);
        tabSegment.setMode(QMUITabSegment.MODE_FIXED);
        fbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PublicConfig.isDebug)
                    Toast.makeText(getActivity(), "Test: 点击了浮动按钮", Toast.LENGTH_SHORT).show();
                showCreateMenuDialog();
            }
        });
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

    /**
     * 显示新建活动菜单
     * 选择创建活动 / 加入活动
     */
    private void showCreateMenuDialog() {
        final String[] items = new String[]{"创建活动", "加入活动"};
        new QMUIDialog.MenuDialogBuilder(getActivity())
                .setSkinManager(QMUISkinManager.defaultInstance(getContext()))
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (PublicConfig.isDebug)
                            Toast.makeText(getActivity(), "Test: 你选择了 " + items[which], Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                // 创建活动
                                ActDetailFragment fragment = new ActDetailFragment(null, false, true);
                                startFragment(fragment);
                                break;
                            case 1:
                                // 加入活动
                                showJoinActDialog();
                                break;
                        }
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    /**
     * 弹窗加入活动
     */
    private void showJoinActDialog() {
        // 这里必须用 QMUI 的布局作为父布局，不然不能调节提示框的大小
        QMUISkinValueBuilder builder = QMUISkinValueBuilder.acquire();
        QMUIFrameLayout frameLayout = new QMUIFrameLayout(getContext());
        frameLayout.setBackground(
                QMUIResHelper.getAttrDrawable(getContext(), R.attr.qmui_skin_support_popup_bg));
        builder.background(R.attr.qmui_skin_support_popup_bg);
        QMUISkinHelper.setSkinValue(frameLayout, builder);
        frameLayout.setRadius(QMUIDisplayHelper.dp2px(getContext(), 12));
        final int padding = QMUIDisplayHelper.dp2px(getContext(), 20);
        frameLayout.setPadding(padding, padding / 2, padding, padding);
        builder.clear();
        builder.textColor(R.attr.app_skin_common_title_text_color);
        builder.release();
        int height = QMUIDisplayHelper.dp2px(getContext(), 240);
        int width = QMUIDisplayHelper.dp2px(getContext(), 165);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(height, width);
        final View editView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_join_act, null);
        editView.setBackground(QMUIResHelper.getAttrDrawable(getContext(), R.attr.qmui_skin_support_popup_bg));
        TextView tv_name = (TextView) editView.findViewById(R.id.tv_name);
        EditText et_code = (EditText) editView.findViewById(R.id.et_code);
        EditText et_pwd = (EditText) editView.findViewById(R.id.et_pwd);
        tv_name.setText("加入活动");
        frameLayout.addView(editView, lp);
        // 这个弹框的 closeBtn 用来当做确认按钮 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // closeIcon 已更换成 一个勾的图案
        QMUIPopups.fullScreenPopup(getContext())
                .addView(frameLayout)
                .closeBtn(true)
                .closeIcon(getContext().getDrawable(R.drawable.icon_popup_finish))
                .onBlankClick(new QMUIFullScreenPopup.OnBlankClickListener() {
                    @Override
                    public void onBlankClick(QMUIFullScreenPopup popup) {
                        popup.onDismiss(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                if (PublicConfig.isDebug)
                                    Toast.makeText(getActivity(), "Test: 取消弹框", Toast.LENGTH_SHORT).show();
                            }
                        });
                        popup.dismiss();
                    }
                })
                .onDismiss(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {

                    }
                })
                .show(getView());
    }

}
