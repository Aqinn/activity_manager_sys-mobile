package com.aqinn.actmanagersys.mobile.index.act.createact;

import android.app.Activity;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.common.OnRecyclerItemClickListener;
import com.aqinn.actmanagersys.mobile.index.act.actdetail.ActDetailFragment;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
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
public class CreateActPresenter implements ICreateAct.Presenter {

    private ICreateAct.View mView;
    private ICreateAct.Model mModel;

    private List<ActShow> mData;

    private CreateActAdapter mAdapter;

    public CreateActPresenter(ICreateAct.View view) {
        mView = view;
        mModel = new CreateActModel();
    }

    @Override
    public void initData(BaseFragmentActivity context, RecyclerView rvCreate) {
        // 测试用，填充测试数据
        if (PublicConfig.isDebug) {
            mData = new ArrayList<>();
            mAdapter = new CreateActAdapter(context, mData);
            mModel.getCreateAct(mData, new ICreateAct.Model.Callback() {
                @Override
                public void onSuccess() {
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(ICreateAct.ErrorCode errorCode) {

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
                                }
                            }
                    ));
                    qqa.show(vh.itemView);
                }
            });
        }
    }

}
