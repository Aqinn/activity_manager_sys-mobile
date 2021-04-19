package com.aqinn.actmanagersys.mobile.index.attendcenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.common.OnRecyclerItemClickListener;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.index.attendcenter.createattend.CreateAttendDialogBuilder;
import com.aqinn.actmanagersys.mobile.index.attendcenter.editattend.EditAttendTimeDialogBuilder;
import com.aqinn.actmanagersys.mobile.index.attendcenter.editattend.EditAttendTypeDialogBuilder;
import com.aqinn.actmanagersys.mobile.model.AttendShow;
import com.aqinn.actmanagersys.mobile.selfattend.SelfAttendActivity;
import com.aqinn.actmanagersys.mobile.utils.ParseUtil;
import com.aqinn.actmanagersys.mobile.videoattend.VideoAttendActivity;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.popup.QMUIQuickAction;

import java.util.ArrayList;
import java.util.List;

/**
 * 签到中心 - Presenter
 *
 * @author Aqinn
 * @date 2021/4/7 1:48 PM
 */
public class AttendCenterPresenter implements IAttendCenter.Presenter {

    private IAttendCenter.View mView;

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    private List<AttendShow> mData;
    private RecyclerView mRecyclerView;

    public AttendCenterPresenter(IAttendCenter.View view) {
        mView = view;
    }

    @Override
    public void initRecyclerView(BaseFragment context, RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        if (PublicConfig.isDebug) {
            mData = new ArrayList<>();
            mData.add(new AttendShow("活动1", "2021-4-5 12:00", "2021-4-5 15:00", 1, 100, 66, 1));
            mData.add(new AttendShow("活动2", "2021-4-5 12:00", "2021-4-5 15:00", 2, 100, 66, 1));
            mData.add(new AttendShow("活动3", "2021-4-5 12:00", "2021-4-5 15:00", 3, 100, 66, 1));
            mData.add(new AttendShow("活动4", "2021-4-5 12:00", "2021-4-5 15:00", 2, 100, 66, 1));
            mData.add(new AttendShow("活动5", "2021-4-5 12:00", "2021-4-5 15:00", 1, 100, 66, 1));
            mData.add(new AttendShow("活动6", "2021-4-5 12:00", "2021-4-5 15:00", 3, 100, 66, 1));
            mData.add(new AttendShow("活动7", "2021-4-5 12:00", "2021-4-5 15:00", 2, 100, 66, 1));
            mData.add(new AttendShow("活动8", "2021-4-5 12:00", "2021-4-5 15:00", 2, 100, 66, 1));
            mData.add(new AttendShow("活动9", "2021-4-5 12:00", "2021-4-5 15:00", 1, 100, 66, 1));
            AttendCenterAdapter adapter = new AttendCenterAdapter(context.getActivity(), mData);
            mRecyclerView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context.getActivity());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
                @Override
                public void onItemClick(RecyclerView.ViewHolder vh) {
                    final AttendShow item = mData.get(vh.getAdapterPosition());
                    QMUIQuickAction qqa = QMUIPopups.quickAction(context.getContext(),
                            QMUIDisplayHelper.dp2px(context.getContext(), 70),
                            QMUIDisplayHelper.dp2px(context.getContext(), 56))
                            .shadow(true)
                            .skinManager(QMUISkinManager.defaultInstance(context.getContext()))
                            .edgeProtection(QMUIDisplayHelper.dp2px(context.getContext(), 20));
                    qqa.addAction(new QMUIQuickAction.Action().text("查看详情").onClick(
                            new QMUIQuickAction.OnClickListener() {
                                @Override
                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                    quickAction.dismiss();
                                    if (PublicConfig.isDebug)
                                        Toast.makeText(context.getActivity(), "Test: 查看详情", Toast.LENGTH_SHORT).show();

                                }
                            }
                    ));
                    qqa.addAction(new QMUIQuickAction.Action().text("更改签到方式").onClick(
                            new QMUIQuickAction.OnClickListener() {
                                @Override
                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                    quickAction.dismiss();
                                    if (PublicConfig.isDebug)
                                        Toast.makeText(context.getActivity(), "Test: 更改签到方式", Toast.LENGTH_SHORT).show();
                                    // showEditAttendType(context, item);
                                    EditAttendTypeDialogBuilder builder = new EditAttendTypeDialogBuilder(context.getActivity(), item.getAttendType());
                                    builder.setTitle("更改签到方式")
                                            .setSkinManager(QMUISkinManager.defaultInstance(context.getActivity()))
                                            .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                                                @Override
                                                public void onClick(QMUIDialog dialog, int index) {
                                                    if (PublicConfig.isDebug)
                                                        Toast.makeText(context.getActivity(), "Test: 点击确定", Toast.LENGTH_SHORT).show();
                                                    Integer attendType = builder.getAttendType();
                                                    // 一个签到方式都没有选择
                                                    if (attendType == -1) {
                                                        Toast.makeText(context.getActivity(), "请至少选择一种签到方式", Toast.LENGTH_SHORT).show();
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
                    ));
                    qqa.addAction(new QMUIQuickAction.Action().text("更改签到时间").onClick(
                            new QMUIQuickAction.OnClickListener() {
                                @Override
                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                    quickAction.dismiss();
                                    if (PublicConfig.isDebug)
                                        Toast.makeText(context.getActivity(), "Test: 更改签到时间", Toast.LENGTH_SHORT).show();
                                    // showEditAttendTime(context, ((AttendCenterViewHolder) vh).tvTime, item);
                                    EditAttendTimeDialogBuilder builder = new EditAttendTimeDialogBuilder(context.getActivity());
                                    builder.setTitle("更改签到时间")
                                            .setSkinManager(QMUISkinManager.defaultInstance(context.getActivity()))
                                            .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                                                @Override
                                                public void onClick(QMUIDialog dialog, int index) {
                                                    dialog.dismiss();
                                                    if (PublicConfig.isDebug)
                                                        Toast.makeText(context.getActivity(), "Test: 点击确定", Toast.LENGTH_SHORT).show();
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
                    ));
                    qqa.addAction(new QMUIQuickAction.Action().text("自助签到").onClick(
                            new QMUIQuickAction.OnClickListener() {
                                @Override
                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                    quickAction.dismiss();
                                    if (PublicConfig.isDebug)
                                        Toast.makeText(context.getActivity(), "Test: 自助签到", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context.getActivity(), SelfAttendActivity.class);
                                    context.startActivity(intent);
                                }
                            }
                    ));
                    qqa.addAction(new QMUIQuickAction.Action().text("视频签到").onClick(
                            new QMUIQuickAction.OnClickListener() {
                                @Override
                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                    quickAction.dismiss();
                                    if (PublicConfig.isDebug)
                                        Toast.makeText(context.getActivity(), "Test: 视频签到", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context.getActivity(), VideoAttendActivity.class);
                                    context.startActivity(intent);
                                }
                            }
                    ));
                    qqa.show(((AttendCenterViewHolder) vh).tvTime);
                }
            });
        }
    }

    /**
     * 弹窗编辑签到方式
     */
    @Deprecated
    private void showEditAttendType(BaseFragment context, final AttendShow caii) {
        Integer type[] = ParseUtil.dec2typeArr(caii.getAttendType());
        int[] caiiType = new int[type.length];
        for (int i = 0; i < type.length; i++) {
            caiiType[i] = type[i] - 1;
        }
//        final String[] items = new String[]{"视频签到", "自助签到", "视频弱签到(暂时没用)", "自助弱签到(暂时没用)"};
        final String[] items = new String[]{"视频签到", "自助签到"};
        final QMUIDialog.MultiCheckableDialogBuilder builder = new QMUIDialog.MultiCheckableDialogBuilder(context.getActivity())
                .setCheckedItems(caiiType)
                .setSkinManager(QMUISkinManager.defaultInstance(context.getActivity()))
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.addAction("取消", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        });
        builder.addAction("确认", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                String result = "Test: 你选择了 ";
                Integer[] caiiType = new Integer[builder.getCheckedItemIndexes().length];
                for (int i = 0; i < builder.getCheckedItemIndexes().length; i++) {
                    result += "" + builder.getCheckedItemIndexes()[i] + "; ";
                    caiiType[i] = builder.getCheckedItemIndexes()[i] + 1;
                }
                if (PublicConfig.isDebug)
                    Toast.makeText(context.getActivity(), result, Toast.LENGTH_SHORT).show();
                // serviceManager.editAttendType(caii.getActId(), ParseUtil.typeArr2dec(caiiType));
                dialog.dismiss();
            }
        });
        builder.create(mCurrentDialogStyle).show();
    }

    /**
     * 弹窗编辑签到时间
     *
     * @param v
     */
    @Deprecated
    private void showEditAttendTime(BaseFragment context, View v, AttendShow attend) {
//        // 这里必须用 QMUI 的布局作为父布局，不然不能调节提示框的大小
//        QMUISkinValueBuilder builder = QMUISkinValueBuilder.acquire();
//        QMUIFrameLayout frameLayout = new QMUIFrameLayout(context.getActivity());
//        frameLayout.setBackground(
//                QMUIResHelper.getAttrDrawable(context.getActivity(), R.attr.qmui_skin_support_popup_bg));
//        builder.background(R.attr.qmui_skin_support_popup_bg);
//        QMUISkinHelper.setSkinValue(frameLayout, builder);
//        frameLayout.setRadius(QMUIDisplayHelper.dp2px(context.getActivity(), 12));
//        final int padding = QMUIDisplayHelper.dp2px(context.getActivity(), 10);
//        frameLayout.setPadding(padding, padding, padding, padding);
//        builder.clear();
//        builder.textColor(R.attr.app_skin_common_title_text_color);
//        builder.release();
//        int height = QMUIDisplayHelper.dp2px(context.getActivity(), 250);
//        int width = QMUIDisplayHelper.dp2px(context.getActivity(), 115);
//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(height, width);
//
//        final View editView = LayoutInflater.from(context.getActivity()).inflate(R.layout.fragment_edit_attend_time, null);
//        editView.setBackground(QMUIResHelper.getAttrDrawable(context.getActivity(), R.attr.qmui_skin_support_popup_bg));
//        TextView tv_name = (TextView) editView.findViewById(R.id.tv_name);
//        tv_name.setText(caii.getName());
//        TextView tv_time = (TextView) editView.findViewById(R.id.tv_time);
//        initTimerPicker(tv_time);
//        tv_time.setClickable(true);
//        tv_time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                timePicker.show(tv_time.getText().toString());
//            }
//        });
//        frameLayout.addView(editView, lp);
//        // 这个弹框的 closeBtn 用来当做确认按钮 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        // closeIcon 已更换成 一个勾的图案
//        QMUIPopups.fullScreenPopup(context.getActivity())
//                .addView(frameLayout)
//                .closeBtn(true)
//                .closeIcon(context.getActivity().getDrawable(R.drawable.icon_popup_yes))
//                .onBlankClick(new QMUIFullScreenPopup.OnBlankClickListener() {
//                    @Override
//                    public void onBlankClick(QMUIFullScreenPopup popup) {
//                        popup.onDismiss(new PopupWindow.OnDismissListener() {
//                            @Override
//                            public void onDismiss() {
//                                if (PublicConfig.isDebug)
//                                    Toast.makeText(context.getActivity(), "Test: 取消弹框", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        popup.dismiss();
//                    }
//                })
//                .onDismiss(new PopupWindow.OnDismissListener() {
//                    @Override
//                    public void onDismiss() {
////                        serviceManager.editAttendTime(id, ((TextView) editView.findViewById(R.id.tv_time)).getText().toString());
//                    }
//                })
//                .show(v);
    }

}
