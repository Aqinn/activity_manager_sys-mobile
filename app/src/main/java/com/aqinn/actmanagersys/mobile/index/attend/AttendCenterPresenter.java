package com.aqinn.actmanagersys.mobile.index.attend;

import android.content.Intent;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.common.OnRecyclerItemClickListener;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.model.AttendShow;
import com.aqinn.actmanagersys.mobile.selfattend.SelfAttendActivity;
import com.aqinn.actmanagersys.mobile.videoattend.VideoAttendActivity;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
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
                            QMUIDisplayHelper.dp2px(context.getContext(), 56),
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

}
