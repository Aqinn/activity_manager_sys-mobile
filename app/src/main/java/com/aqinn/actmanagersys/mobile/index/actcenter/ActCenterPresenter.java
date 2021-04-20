package com.aqinn.actmanagersys.mobile.index.actcenter;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.index.actcenter.actdetail.ActDetailFragment;
import com.aqinn.actmanagersys.mobile.index.actcenter.joinact.JoinActDialogBuilder;
import com.qmuiteam.qmui.layout.QMUIFrameLayout;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.popup.QMUIFullScreenPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 活动中心 - Presenter
 *
 * @author Aqinn
 * @date 2021/4/7 2:24 PM
 */
@Deprecated
public class ActCenterPresenter implements IActCenter.Presenter {

    private IActCenter.View mView;
    private IActCenter.Model mModel;

    private Banner mBanner;
    private QMUIPopup mGlobalAction;

    public ActCenterPresenter(IActCenter.View view) {
        mView = view;
    }

    @Override
    public void initBanner(BaseFragment context, Banner banner) {
        mBanner = banner;
        List<Integer> imgList = new ArrayList<>();
        imgList.add(R.drawable.wxgg);
        imgList.add(R.drawable.wxgg);
        imgList.add(R.drawable.wxgg);
        mBanner.setAdapter(new BannerImageAdapter<Integer>(imgList) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer drawableId, int position, int size) {
                holder.imageView.setImageDrawable(context.getResources().getDrawable(drawableId));
            }
        }).addBannerLifecycleObserver(context)
                .setIndicator(new CircleIndicator(context.getContext()))
        .setBannerRound(20);
    }

    /**
     * 显示新建活动菜单
     * 选择创建活动 / 加入活动
     */
    @Override
    public void showCreateMenuDialog(ActCenterFragment context, View view) {
        // oldShowCreateMenuDialog(context, view);
        newShowCreateMenuDialog(context, view);
    }

    private void newShowCreateMenuDialog(ActCenterFragment context, View view) {
        String[] listItems = new String[]{
                context.getResources().getString(R.string.act_center_fb_create_act),
                context.getResources().getString(R.string.act_center_fb_join_act)
        };
        List<String> data = new ArrayList<>();

        Collections.addAll(data, listItems);

        ArrayAdapter adapter = new ArrayAdapter<>(context.getActivity(), R.layout.item_simple_list, data);
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    // 创建活动
                    ActDetailFragment fragment = new ActDetailFragment(null, false, true);
                    context.startFragment(fragment);
                } else if (i == 1) {
                    // 加入活动
                    // oldShowJoinActDialog(context);
                    newShowJoinActDialog(context);
                }
                if (mGlobalAction != null)
                    mGlobalAction.dismiss();
            }
        };
        mGlobalAction = QMUIPopups.listPopup(context.getActivity(),
                QMUIDisplayHelper.dp2px(context.getActivity(), 100),
                QMUIDisplayHelper.dp2px(context.getActivity(), 300),
                adapter,
                onItemClickListener)
                .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                .preferredDirection(QMUIPopup.DIRECTION_TOP)
                .shadow(true)
                .edgeProtection(QMUIDisplayHelper.dp2px(context.getActivity(), 10))
                .offsetYIfTop(QMUIDisplayHelper.dp2px(context.getActivity(), 5))
                .skinManager(QMUISkinManager.defaultInstance(context.getActivity()))
                .show(view);
    }

    /**
     * 弹窗加入活动
     */
    private void newShowJoinActDialog(ActCenterFragment fragment) {
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

    /**
     * 弹窗加入活动
     */
    @Deprecated
    private void oldShowJoinActDialog(ActCenterFragment fragment) {
        // 这里必须用 QMUI 的布局作为父布局，不然不能调节提示框的大小
        QMUISkinValueBuilder builder = QMUISkinValueBuilder.acquire();
        QMUIFrameLayout frameLayout = new QMUIFrameLayout(fragment.getContext());
        frameLayout.setBackground(
                QMUIResHelper.getAttrDrawable(fragment.getContext(), R.attr.qmui_skin_support_popup_bg));
        builder.background(R.attr.qmui_skin_support_popup_bg);
        QMUISkinHelper.setSkinValue(frameLayout, builder);
        frameLayout.setRadius(QMUIDisplayHelper.dp2px(fragment.getContext(), 12));
        final int padding = QMUIDisplayHelper.dp2px(fragment.getContext(), 20);
        frameLayout.setPadding(padding, padding / 2, padding, padding);
        builder.clear();
        builder.textColor(R.attr.app_skin_common_title_text_color);
        builder.release();
        int height = QMUIDisplayHelper.dp2px(fragment.getContext(), 240);
        int width = QMUIDisplayHelper.dp2px(fragment.getContext(), 165);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(height, width);
        final View editView = LayoutInflater.from(fragment.getContext()).inflate(R.layout.dialog_join_act, null);
        editView.setBackground(QMUIResHelper.getAttrDrawable(fragment.getContext(), R.attr.qmui_skin_support_popup_bg));
        TextView tv_name = (TextView) editView.findViewById(R.id.tv_name);
        EditText et_code = (EditText) editView.findViewById(R.id.et_code);
        EditText et_pwd = (EditText) editView.findViewById(R.id.et_pwd);
        tv_name.setText("加入活动");
        frameLayout.addView(editView, lp);
        // 这个弹框的 closeBtn 用来当做确认按钮 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // closeIcon 已更换成 一个勾的图案
        QMUIPopups.fullScreenPopup(fragment.getContext())
                .addView(frameLayout)
                .closeBtn(true)
                .closeIcon(fragment.getContext().getDrawable(R.drawable.icon_popup_finish))
                .onBlankClick(new QMUIFullScreenPopup.OnBlankClickListener() {
                    @Override
                    public void onBlankClick(QMUIFullScreenPopup popup) {
                        popup.onDismiss(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                if (PublicConfig.isDebug)
                                    Toast.makeText(fragment.getActivity(), "Test: 取消弹框", Toast.LENGTH_SHORT).show();
                            }
                        });
                        popup.dismiss();
                    }
                })
                .onDismiss(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        if (PublicConfig.isDebug) {
                            Toast.makeText(fragment.getActivity(), "Test: 点击确认\ncode: " + et_code.getText().toString()
                                    + "\npwd: " + et_pwd.getText().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .show(fragment.getView());
    }

    @Deprecated
    private void oldShowCreateMenuDialog(ActCenterFragment context, View view) {
        final String[] items = new String[]{"创建活动", "加入活动"};
        new QMUIDialog.MenuDialogBuilder(context.getActivity())
                .setSkinManager(QMUISkinManager.defaultInstance(context.getActivity()))
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (PublicConfig.isDebug)
                            Toast.makeText(context.getActivity(), "Test: 你选择了 " + items[which], Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                // 创建活动
                                ActDetailFragment fragment = new ActDetailFragment(null, false, true);
                                context.startFragment(fragment);
                                break;
                            case 1:
                                // 加入活动
                                // showJoinActDialog((BaseFragmentActivity) context.getActivity(), view);
                                break;
                        }
                    }
                })
                .create(PublicConfig.mCurrentDialogStyle).show();
    }

}
