package com.aqinn.actmanagersys.mobile.base.QMUI.my;

import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.index.actcenter.actdetail.ActDetailFragment;
import com.aqinn.actmanagersys.mobile.index.actcenter.joinact.JoinActDialogBuilder;
import com.aqinn.actmanagersys.mobile.model.AttendShow;
import com.qmuiteam.qmui.recyclerView.QMUIRVDraggableScrollBar;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUICenterGravityRefreshOffsetCalculator;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.qmuiteam.qmui.widget.section.QMUISection;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 活动卡片列表 - Presenter
 *
 * @author Aqinn
 * @date 2021/4/18 4:33 PM
 */
public class ActCardPresenter implements IActCard.Presenter {

    private static final String TAG = "ActCardPresenter";

    private IActCard.View mView;
    private IActCard.Model mModel;

    BaseFragment mFragment;
    QMUITopBarLayout mTopBar;
    QMUIPullRefreshLayout mPullRefreshLayout;
    QMUIStickySectionLayout mSectionLayout;

    private RecyclerView.LayoutManager mLayoutManager;
    public QMUIStickySectionAdapter<SectionHeader_Act, SectionItem_Attend, QMUIStickySectionAdapter.ViewHolder> mAdapter;

    public ActCardPresenter(IActCard.View view) {
        mView = view;
        mModel = new ActCardModel();
    }

    /**
     * 初始化所有组件
     *
     * @param fragment
     * @param topBar
     * @param pullRefreshLayout
     * @param sectionLayout
     */
    @Override
    public void init(BaseFragment fragment, QMUITopBarLayout topBar, QMUIPullRefreshLayout pullRefreshLayout, QMUIStickySectionLayout sectionLayout) {
        mFragment = fragment;
        mTopBar = topBar;
        mPullRefreshLayout = pullRefreshLayout;
        mSectionLayout = sectionLayout;
        initTopBar();
        initRefreshLayout();
        initStickyLayout();
        initData();
    }

    // 初始化TopBar
    private void initTopBar() {
        mTopBar.setVisibility(View.GONE);
    }

    // 初始化刷新布局
    private void initRefreshLayout() {
        mPullRefreshLayout.setRefreshOffsetCalculator(new QMUICenterGravityRefreshOffsetCalculator());
        mPullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {
                Log.d(TAG, "onMoveTarget: offset => " + offset);
            }

            @Override
            public void onMoveRefreshView(int offset) {
                Log.d(TAG, "onMoveRefreshView: offset => " + offset);
            }

            @Override
            public void onRefresh() {
                mPullRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshLayout.finishRefresh();
                        // mSectionLayout.scrollToPosition(2, true, true);
                    }
                }, 500);
            }
        });
//        mPullRefreshLayout..startScroll(hOffset, vOffset, 0, -vOffset, scrollDuration(mBottomPullAction, vOffset));
//        mPullRefreshLayout.postInvalidateOnAnimation();

    }

    // 初始化Section列表布局
    private void initStickyLayout() {
        mLayoutManager = createLayoutManager();
        mSectionLayout.setLayoutManager(mLayoutManager);
//        QMUIRVDraggableScrollBar scrollBar = new QMUIRVDraggableScrollBar(0, 0, 0);
//        scrollBar.setEnableScrollBarFadeInOut(false);
//        scrollBar.attachToStickSectionLayout(mSectionLayout);
//        scrollBar.setDraggable(false);
    }

    // 初始化数据
    private void initData() {
        // 设置数据
        mAdapter = createAdapter();
        mAdapter.setData(mModel.getData());
        mSectionLayout.setAdapter(mAdapter, true);
    }

    // 创建Section布局的适配器
    private QMUIStickySectionAdapter<SectionHeader_Act, SectionItem_Attend, QMUIStickySectionAdapter.ViewHolder> createAdapter() {
        mAdapter = new ActCardSectionAdapter(mModel.getActList(), mModel.getActAttendMap());
        mAdapter.setCallback(new QMUIStickySectionAdapter.Callback<SectionHeader_Act, SectionItem_Attend>() {
            /**
             * 尝试加载更多的Attend
             * @param section
             * @param loadMoreBefore
             */
            @Override
            public void loadMore(final QMUISection<SectionHeader_Act, SectionItem_Attend> section, final boolean loadMoreBefore) {
                mSectionLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mFragment.isAttachedToActivity()) {
                            ArrayList<SectionItem_Attend> list = new ArrayList<>();
                            for (int i = 0; i < 3; i++) {
                                AttendShow attend = new AttendShow();
                                attend.setName(attend.getName() + ", No." + i);
                                list.add(new SectionItem_Attend(attend));
                            }
                            mAdapter.finishLoadMore(section, list, loadMoreBefore, false);
                        }
                    }
                }, 800);
            }

            @Override
            public void onItemClick(QMUIStickySectionAdapter.ViewHolder holder, int position) {
                // 如果是item就...
                if (mAdapter.getItemViewType(position) == QMUIStickySectionAdapter.ITEM_TYPE_SECTION_ITEM) {
                    if (PublicConfig.isDebug) {
                        Toast.makeText(mFragment.getContext(), "Test: 是item", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public boolean onItemLongClick(QMUIStickySectionAdapter.ViewHolder holder, int position) {
                if (PublicConfig.isDebug)
                    Toast.makeText(mFragment.getContext(), "Test: \nlong click item " + position + "\n"
                            + "mAdapter.getItemIndex(position)" + mAdapter.getItemIndex(position) + "\n"
                            + "mAdapter.getSectionIndex(position)" + mAdapter.getSectionIndex(position) + "\n"
                            + "mAdapter.getItemViewType(position)" + mAdapter.getItemViewType(position) + "\n", Toast.LENGTH_SHORT).show();
                // 如果是header就附加一个菜单
                if (mAdapter.getItemViewType(position) == QMUIStickySectionAdapter.ITEM_TYPE_SECTION_HEADER) {
                    if (PublicConfig.isDebug) {
                        Toast.makeText(mFragment.getContext(), "Test: 是header", Toast.LENGTH_SHORT).show();
                    }
                }
//                ActCardSectionAdapter.SectionHeaderViewHolder realHolder = (ActCardSectionAdapter.SectionHeaderViewHolder) holder;
//                QMUISection<SectionHeader_Act, SectionItem_Attend> section = mAdapter.getSection(position);
//
//                SectionHeader_Act sectionHeaderAct = section.getHeader();
//                ActShow act = sectionHeaderAct.getAct();
                return true;
            }
        });
        return mAdapter;
    }

    // 创建布局管理器
    private RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(mFragment.getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
    }

    // 底部弹出的Grid菜单
//    private void showSimpleBottomSheetGrid() {
//        final int TAG_SHARE_WECHAT_FRIEND = 0;
//        final int TAG_SHARE_WECHAT_MOMENT = 1;
//        final int TAG_SHARE_WEIBO = 2;
//        final int TAG_SHARE_CHAT = 3;
//        final int TAG_SHARE_LOCAL = 4;
//        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(getActivity());
//        builder.addItem(R.mipmap.icon_more_operation_share_friend, "分享到微信", TAG_SHARE_WECHAT_FRIEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
//                .addItem(R.mipmap.icon_more_operation_share_moment, "分享到朋友圈", TAG_SHARE_WECHAT_MOMENT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
//                .addItem(R.mipmap.icon_more_operation_share_weibo, "分享到微博", TAG_SHARE_WEIBO, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
//                .addItem(R.mipmap.icon_more_operation_share_chat, "分享到私信", TAG_SHARE_CHAT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
//                .addItem(R.mipmap.icon_more_operation_save, "保存到本地", TAG_SHARE_LOCAL, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
//                .setAddCancelBtn(true)
//                .setSkinManager(QMUISkinManager.defaultInstance(getContext()))
//                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
//                    @Override
//                    public void onClick(QMUIBottomSheet dialog, View itemView) {
//                        dialog.dismiss();
//                        int tag = (int) itemView.getTag();
//                        switch (tag) {
//                            case TAG_SHARE_WECHAT_FRIEND:
//                                Toast.makeText(getActivity(), "分享到微信", Toast.LENGTH_SHORT).show();
//                                break;
//                            case TAG_SHARE_WECHAT_MOMENT:
//                                Toast.makeText(getActivity(), "分享到朋友圈", Toast.LENGTH_SHORT).show();
//                                break;
//                            case TAG_SHARE_WEIBO:
//                                Toast.makeText(getActivity(), "分享到微博", Toast.LENGTH_SHORT).show();
//                                break;
//                            case TAG_SHARE_CHAT:
//                                Toast.makeText(getActivity(), "分享到私信", Toast.LENGTH_SHORT).show();
//                                break;
//                            case TAG_SHARE_LOCAL:
//                                Toast.makeText(getActivity(), "保存到本地", Toast.LENGTH_SHORT).show();
//                                break;
//                        }
//                    }
//                }).build().show();
//
//
//    }

}
