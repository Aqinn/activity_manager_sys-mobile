package com.aqinn.actmanagersys.mobile.index.actcenter.mycreateact;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.common.OnRecyclerItemClickListener;
import com.aqinn.actmanagersys.mobile.index.actcenter.actdetail.ActDetailFragment;
import com.aqinn.actmanagersys.mobile.index.attendcenter.createattend.CreateAttendDialogBuilder;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.popup.QMUIQuickAction;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建的活动 - Presenter
 *
 * @author Aqinn
 * @date 2021/4/15 8:24 PM
 */
@Deprecated
public class MyCreateActPresenter implements IMyCreateAct.Presenter {

    private IMyCreateAct.View mView;
    private IMyCreateAct.Model mModel;

    private List<ActShow> mData;

    private MyCreateActAdapter mAdapter;

    public MyCreateActPresenter(IMyCreateAct.View view) {
        mView = view;
        mModel = new MyCreateActModel();
    }

    @Override
    public void initData(BaseFragmentActivity context, RecyclerView rvCreate) {
        // 测试用，填充测试数据
        if (PublicConfig.isDebug) {
            mData = new ArrayList<>();
            mAdapter = new MyCreateActAdapter(context, mData);
            mModel.getCreateAct(mData, new IMyCreateAct.Model.Callback() {
                @Override
                public void onSuccess() {
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(IMyCreateAct.ErrorCode errorCode) {

                }
            });
//            mData.add(new ActShow("创建活动的唔西迪西"));
//            mData.add(new ActShow("创建活动的玛卡巴卡"));
            rvCreate.setAdapter(mAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            rvCreate.setLayoutManager(layoutManager);
            rvCreate.addOnItemTouchListener(new OnRecyclerItemClickListener(rvCreate) {
                @Override
                public void onItemClick(RecyclerView.ViewHolder vh) {
                    final ActShow item = mData.get(vh.getAdapterPosition());
                    QMUIQuickAction qqa = QMUIPopups.quickAction(context,
                            QMUIDisplayHelper.dp2px(context, 56),
                            QMUIDisplayHelper.dp2px(context, 56))
                            .shadow(true)
                            .skinManager(QMUISkinManager.defaultInstance(context))
                            .edgeProtection(QMUIDisplayHelper.dp2px(context, 20));
                    qqa.addAction(new QMUIQuickAction.Action().text("查看详情").onClick(
                            new QMUIQuickAction.OnClickListener() {
                                @Override
                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                    quickAction.dismiss();
                                    ActDetailFragment fragment = new ActDetailFragment(item, true, false);
                                    context.startFragment(fragment);
                                }
                            }
                    ));
                    qqa.addAction(new QMUIQuickAction.Action().text("活动代码").onClick(
                            new QMUIQuickAction.OnClickListener() {
                                @Override
                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                    quickAction.dismiss();
                                    if (PublicConfig.isDebug)
                                        Toast.makeText(context, "Test: 活动代码", Toast.LENGTH_SHORT).show();
                                    showActCodeAndPwd(context, vh.itemView, String.valueOf(item.getCode()), String.valueOf(item.getPwd()));
                                }
                            }
                    ));
                    qqa.addAction(new QMUIQuickAction.Action().text("编辑活动").onClick(
                            new QMUIQuickAction.OnClickListener() {
                                @Override
                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                    quickAction.dismiss();
                                    ActDetailFragment fragment = new ActDetailFragment(item, true, true);
                                    context.startFragment(fragment);
                                }
                            }
                    ));
                    qqa.addAction(new QMUIQuickAction.Action().text("创建签到").onClick(
                            new QMUIQuickAction.OnClickListener() {
                                @Override
                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                    quickAction.dismiss();
                                    if (PublicConfig.isDebug)
                                        Toast.makeText(context, "Test: 创建签到", Toast.LENGTH_SHORT).show();
//                                    CreateAttendFragment fragment = new CreateAttendFragment();
//                                    FragmentTransaction ft = context.getSupportFragmentManager().beginTransaction();
//                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                                    fragment.show(ft, "findMindmapDialogFragment");
                                    CreateAttendDialogBuilder builder = new CreateAttendDialogBuilder(context);
                                    builder.setTitle("创建签到")
                                            .setSkinManager(QMUISkinManager.defaultInstance(context))
                                            .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                                                @Override
                                                public void onClick(QMUIDialog dialog, int index) {
                                                    dialog.dismiss();
                                                    if (PublicConfig.isDebug)
                                                        Toast.makeText(context, "Test: 点击确定", Toast.LENGTH_SHORT).show();
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
                    qqa.addAction(new QMUIQuickAction.Action().text("删除活动").onClick(
                            new QMUIQuickAction.OnClickListener() {
                                @Override
                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                    quickAction.dismiss();
                                    if (PublicConfig.isDebug)
                                        Toast.makeText(context, "Test: 删除活动", Toast.LENGTH_SHORT).show();
                                    mModel.deleteCreateAct(item.getActId(), new IMyCreateAct.Model.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            Toast.makeText(context, "删除活动成功", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onError(IMyCreateAct.ErrorCode errorCode) {
                                            switch (errorCode) {
                                                case UNKNOWN_RESPONSE_ERROR:
                                                    Toast.makeText(context, "未知响应错误，可能是活动不存在", Toast.LENGTH_SHORT).show();
                                                    break;
                                                case UNKNOWN_NETWORK_ERROR:
                                                    Toast.makeText(context, "未知网络错误，可能是没有联网", Toast.LENGTH_SHORT).show();
                                                    break;
                                            }
                                        }
                                    });
                                }
                            }
                    ));
                    qqa.show(vh.itemView);
                }
            });
        }
    }

    private void showActCodeAndPwd(Context context, View view, String code, String pwd) {
        QMUIQuickAction qqa = QMUIPopups.quickAction(context,
                QMUIDisplayHelper.dp2px(context, 80),
                QMUIDisplayHelper.dp2px(context, 56))
                .shadow(true)
                .skinManager(QMUISkinManager.defaultInstance(context))
                .edgeProtection(QMUIDisplayHelper.dp2px(context, 20));
        qqa.addAction(new QMUIQuickAction.Action().text("代码: " + code + "\n密码: " + pwd));
        qqa.show(view);
    }

}
