package com.aqinn.actmanagersys.mobile.index.act.joinact;

import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.common.OnRecyclerItemClickListener;
import com.aqinn.actmanagersys.mobile.index.act.actdetail.ActDetailFragment;
import com.aqinn.actmanagersys.mobile.index.act.createact.ICreateAct;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.popup.QMUIQuickAction;

import java.util.ArrayList;
import java.util.List;

/**
 * 参与的活动 - Presenter
 *
 * @author Aqinn
 * @date 2021/4/15 9:55 PM
 */
public class JoinActPresenter implements IJoinAct.Presenter {

    private IJoinAct.View mView;

    private IJoinAct.Model mModel;

    private List<ActShow> mData;

    private JoinActAdapter mAdapter;

    public JoinActPresenter(IJoinAct.View view) {
        mView = view;
        mModel = new JoinActModel();
    }

    @Override
    public void initData(BaseFragmentActivity context, RecyclerView rvJoin) {
        // 测试用，填充测试数据
        if (PublicConfig.isDebug) {
            mData = new ArrayList<>();
            mAdapter = new JoinActAdapter(context, mData);
            mModel.getJoinAct(mData, new IJoinAct.Model.Callback() {
                @Override
                public void onSuccess() {
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(ICreateAct.ErrorCode errorCode) {

                }
            });
//            mData.add(new ActShow("参加活动的阿巴阿巴"));
//            mData.add(new ActShow("参加活动的古卡古卡"));
//            mData.add(new ActShow("参加活动的唔西迪西"));
//            mData.add(new ActShow("参加活动的玛卡巴卡"));
            rvJoin.setAdapter(mAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            rvJoin.setLayoutManager(layoutManager);
            rvJoin.addOnItemTouchListener(new OnRecyclerItemClickListener(rvJoin) {
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
                                    ActDetailFragment fragment = new ActDetailFragment(item, false, false);
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
                    qqa.addAction(new QMUIQuickAction.Action().text("退出活动").onClick(
                            new QMUIQuickAction.OnClickListener() {
                                @Override
                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                    quickAction.dismiss();
                                    if (PublicConfig.isDebug)
                                        Toast.makeText(context, "Test: 退出活动", Toast.LENGTH_SHORT).show();
                                }
                            }
                    ));
                    qqa.show(vh.itemView);
                }
            });
        }
    }

}
