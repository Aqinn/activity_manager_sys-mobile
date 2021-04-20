package com.aqinn.actmanagersys.mobile.actcard;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseApplication;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.facecollect.FaceCollectActivity;
import com.aqinn.actmanagersys.mobile.index.actcenter.actdetail.ActDetailFragment;
import com.aqinn.actmanagersys.mobile.index.attendcenter.createattend.CreateAttendDialogBuilder;
import com.aqinn.actmanagersys.mobile.index.attendcenter.editattend.EditAttendTimeDialogBuilder;
import com.aqinn.actmanagersys.mobile.index.attendcenter.editattend.EditAttendTypeDialogBuilder;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.model.AttendShow;
import com.aqinn.actmanagersys.mobile.newui.actdetail.ActDetailDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUICenterGravityRefreshOffsetCalculator;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.qmuiteam.qmui.widget.section.QMUISection;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionLayout;

import java.util.ArrayList;

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

    private Handler mHandler = new Handler();

    ActCardType mType;
    BaseFragment mFragment;
    QMUITopBarLayout mTopBar;
    QMUIPullRefreshLayout mPullRefreshLayout;
    QMUIStickySectionLayout mSectionLayout;

    private RecyclerView.LayoutManager mLayoutManager;
    public QMUIStickySectionAdapter<SectionHeader_Act, SectionItem_Attend, QMUIStickySectionAdapter.ViewHolder> mAdapter;

    public ActCardPresenter(IActCard.View view, ActCardType type) {
        mView = view;
        mType = type;
        mModel = new ActCardModel(type);
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

            }

            @Override
            public void onMoveRefreshView(int offset) {

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
        mAdapter = new ActListWithDecorationSectionAdapter(mType, mModel.getActList(), mModel.getActAttendMap());
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
//                if (mAdapter.getItemViewType(position) == QMUIStickySectionAdapter.ITEM_TYPE_SECTION_ITEM) {
//                    if (PublicConfig.isDebug) {
//                        Toast.makeText(mFragment.getContext(), "Test: 是item, 签到状态码是 " + String.valueOf(attend.getStatus()), Toast.LENGTH_SHORT).show();
//                    }
//                } else if (mAdapter.getItemViewType(position) == QMUIStickySectionAdapter.ITEM_TYPE_SECTION_HEADER) {
//                    ActCardSectionAdapter.SectionHeaderViewHolder realHolder = (ActCardSectionAdapter.SectionHeaderViewHolder) holder;
//                    QMUISection<SectionHeader_Act, SectionItem_Attend> section = mAdapter.getSection(position);
//                    SectionHeader_Act sectionHeaderAct = section.getHeader();
//                    ActShow act = sectionHeaderAct.getAct();
//                }
            }

            /**
             * 长按
             * @param holder
             * @param position 这个position从1开始！
             * @return
             */
            @Override
            public boolean onItemLongClick(QMUIStickySectionAdapter.ViewHolder holder, int position) {
//                if (PublicConfig.isDebug)
//                    Toast.makeText(mFragment.getContext(), "Test: \nlong click item " + position + "\n"
//                            + "mAdapter.getItemIndex(position)" + mAdapter.getItemIndex(position) + "\n"
//                            + "mAdapter.getSectionIndex(position)" + mAdapter.getSectionIndex(position) + "\n"
//                            + "mAdapter.getItemViewType(position)" + mAdapter.getItemViewType(position) + "\n", Toast.LENGTH_SHORT).show();
                // 如果是header就附加一个菜单
                if (mAdapter.getItemViewType(position) == QMUIStickySectionAdapter.ITEM_TYPE_SECTION_HEADER) {
//                    if (PublicConfig.isDebug) {
//                        Toast.makeText(mFragment.getContext(), "Test: 是header", Toast.LENGTH_SHORT).show();
//                    }
                    ActCardSectionAdapter.SectionHeaderViewHolder realHolder = (ActCardSectionAdapter.SectionHeaderViewHolder) holder;
                    QMUISection<SectionHeader_Act, SectionItem_Attend> section = mAdapter.getSection(position);

                    SectionHeader_Act sectionHeaderAct = section.getHeader();
                    ActShow act = sectionHeaderAct.getAct();
                    showActBottomSheetGrid(act, position);
                } else if (mAdapter.getItemViewType(position) == QMUIStickySectionAdapter.ITEM_TYPE_SECTION_ITEM) {
                    QMUISection<SectionHeader_Act, SectionItem_Attend> section = mAdapter.getSection(position);
                    SectionItem_Attend sectionItem = section.getItemAt(mAdapter.getItemIndex(position));
                    AttendShow attend = sectionItem.getAttend();
                    showAttendBottomSheetGrid(attend, position);
                }
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

    /**
     * 底部弹出创建活动的Grid菜单
     */
    private void showActBottomSheetGrid(ActShow act, int position) {
        final int TAG_ACT_DETAIL = 0;
        final int TAG_ACT_CODE = 1;
        final int TAG_EDIT_ACT = 2;
        final int TAG_CREATE_ATTEND = 3;
        final int TAG_DELETE_ACT = 4;
        final int TAG_QUIT_ACT = 5;
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(mFragment.getActivity());
        builder.addItem(R.mipmap.icon_act_detail_small, "查看详情", TAG_ACT_DETAIL, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.icon_act_code_small, "活动代码", TAG_ACT_CODE, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .setAddCancelBtn(true)
                .setSkinManager(QMUISkinManager.defaultInstance(mFragment.getContext()));
        if (mType == ActCardType.FLAG_CREATE) {
            builder.addItem(R.mipmap.icon_update_act_small, "编辑活动", TAG_EDIT_ACT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_create_attend_small, "创建签到", TAG_CREATE_ATTEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_delete_act_small, "删除活动", TAG_DELETE_ACT, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE);
        } else if (mType == ActCardType.FLAG_JOIN) {
            builder.addItem(R.mipmap.icon_quit_act_small, "退出活动", TAG_QUIT_ACT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
        }
        builder.setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
            @Override
            public void onClick(QMUIBottomSheet dialog, View itemView) {
                dialog.dismiss();
                int tag = (int) itemView.getTag();
                switch (tag) {
                    case TAG_ACT_DETAIL:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "查看详情", Toast.LENGTH_SHORT).show();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showActDetail(act);
                            }
                        }, 285);
                        break;
                    case TAG_EDIT_ACT:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "编辑活动", Toast.LENGTH_SHORT).show();
                        ActDetailFragment fragment = new ActDetailFragment(act, true, true);
                        mFragment.startFragment(fragment);
                        break;
                    case TAG_ACT_CODE:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "活动代码", Toast.LENGTH_SHORT).show();
                        showActCode(act);
                        break;
                    case TAG_CREATE_ATTEND:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "创建签到", Toast.LENGTH_SHORT).show();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showCreateAttend(act, position);
                            }
                        }, 285);
                        break;
                    case TAG_DELETE_ACT:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "删除活动", Toast.LENGTH_SHORT).show();
                        showDeleteActConfirmDialog(act, new DeleteActConfirmCallback() {
                            @Override
                            public void onDelete() {
                                deleteAct(position);
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                        break;
                    case TAG_QUIT_ACT:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "退出活动", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }).build().show();


    }

    /**
     * 展示活动详情
     *
     * @param act
     */
    private void showActDetail(ActShow act) {
        ActDetailDialogBuilder builder = new ActDetailDialogBuilder(mFragment.getActivity(), act);
        builder.setTitle(mFragment.getString(R.string.act_detail))
                .setAddCancelBtn(false)
                .setAllowDrag(true)
                .setSkinManager(QMUISkinManager.defaultInstance(mFragment.getContext()))
                .build().show();
    }

    /**
     * 展示活动代码和活动密码
     *
     * @param act
     */
    private void showActCode(ActShow act) {
//        final QMUITipDialog
////                                        tipDialog = new QMUITipDialog.CustomBuilder(mFragment.getContext())
////                                        .setContent(R.layout.tipdialog_custom)
////                                        .create();
//                tipDialog = new QMUITipDialog.Builder(mFragment.getContext())
//                .setTipWord("活动代码: " + act.getCode() + "\n"
//                        + "活动密码: " + act.getPwd())
//                .create();
//        tipDialog.show();
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                tipDialog.dismiss();
//            }
//        }, 1500);

//        Toast.makeText(mFragment.getActivity(), "活动代码: " + act.getCode() + "\n"
//                + "活动密码: " + act.getPwd(), Toast.LENGTH_SHORT).show();

        Snackbar codeAndPwdSnackbar = Snackbar.make(mFragment.getView(), "活动代码: " + act.getCode() + "\n"
                + "活动密码: " + act.getPwd(), Snackbar.LENGTH_INDEFINITE).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        codeAndPwdSnackbar
                .setActionTextColor(BaseApplication.getContext().getColor(R.color.app_color_blue))
                .setAction("关闭", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        codeAndPwdSnackbar.dismiss();
                    }
                }).show();
    }

    /**
     * 展示创建签到的弹窗
     *
     * @param act
     */
    private void showCreateAttend(ActShow act, int position) {
//        CreateAttendBottomSheetDialogBuilder builder = new CreateAttendBottomSheetDialogBuilder(mFragment.getActivity());
//        builder.setTitle(mFragment.getString(R.string.create_attend))
//                .setAddCancelBtn(true)
//                .setCancelText("确定")
//                .setSkinManager(QMUISkinManager.defaultInstance(mFragment.getContext()))
//                .build().show();
        CreateAttendDialogBuilder builder = new CreateAttendDialogBuilder(mFragment.getActivity());
        builder.setTitle("创建签到")
                .setSkinManager(QMUISkinManager.defaultInstance(mFragment.getActivity()))
                .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "Test: 点击确定", Toast.LENGTH_SHORT).show();
                        insertAttend(position);
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
     * 展示删除活动的确认弹窗
     *
     * @param callback
     */
    private void showDeleteActConfirmDialog(ActShow act, DeleteActConfirmCallback callback) {
        new QMUIDialog.MessageDialogBuilder(mFragment.getActivity())
                .setTitle("删除活动")
                .setMessage("确定要删除活动\"" + act.getName() + "\"吗？")
                .setSkinManager(QMUISkinManager.defaultInstance(mFragment.getContext()))
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        callback.onCancel();
                    }
                })
                .addAction(0, "删除", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        callback.onDelete();
                    }
                })
                .create(PublicConfig.mCurrentDialogStyle).show();
    }

    private void insertAct(int position) {
        // 新增测试
        ActShow toBeInsert = new ActShow();
        toBeInsert.setName("刚刚新加的");
        Log.d(TAG, "onItemLongClick: " + position);
        boolean isLastThree = mAdapter.getSectionCount() <= (position + 2);
        mModel.insertAct(position - 1, toBeInsert, null);
        if (!isLastThree)
            mAdapter.notifyItemInserted(position);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.setDataWithoutDiff(mModel.getData(), false);
            }
        }, 280);
    }

    private void deleteAct(int position) {
        boolean isLastSection = mAdapter.getSectionCount() == position;
        Log.d(TAG, "onDelete: " + mAdapter.getSectionCount() + ", " + position);
        boolean deleteRes = mModel.deleteAct(mAdapter.getSectionIndex(position));
        if (deleteRes) {
            if (isLastSection) {
                mAdapter.setDataWithoutDiff(mModel.getData(), false);
            } else {
                mAdapter.notifyItemRemoved(position);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setDataWithoutDiff(mModel.getData(), false);
                    }
                }, 280);
            }
            Toast.makeText(mFragment.getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mFragment.getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateAct(ActShow act, int position) {
        act.setName("已被修改");
        mModel.updateAct(mAdapter.getSectionIndex(position), act);
        mAdapter.notifyItemChanged(position);
    }

    /**
     * 底部弹出签到的Grid菜单
     */
    private void showAttendBottomSheetGrid(AttendShow attend, int position) {
        final int TAG_EDIT_ATTEND_TIME = 0;
        final int TAG_EDIT_ATTEND_TYPE = 1;
        final int TAG_DELETE_ATTEND = 2;
        final int TAG_SELF_ATTEND = 3;
        final int TAG_VIDEO_ATTEND = 4;
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(mFragment.getActivity());
        builder.setAddCancelBtn(true)
                .setSkinManager(QMUISkinManager.defaultInstance(mFragment.getContext()));
        if (mType == ActCardType.FLAG_CREATE) {
            builder.addItem(R.mipmap.icon_act_detail_small, "编辑签到时间", TAG_EDIT_ATTEND_TIME, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_act_code_small, "编辑签到类型", TAG_EDIT_ATTEND_TYPE, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_act_code_small, "视频签到", TAG_VIDEO_ATTEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_delete_act_small, "删除签到", TAG_DELETE_ATTEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
        } else if (mType == ActCardType.FLAG_JOIN) {
            builder.addItem(R.mipmap.icon_act_detail_small, "自助签到", TAG_SELF_ATTEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
        }
        builder.setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
            @Override
            public void onClick(QMUIBottomSheet dialog, View itemView) {
                dialog.dismiss();
                int tag = (int) itemView.getTag();
                switch (tag) {
                    case TAG_EDIT_ATTEND_TIME:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "编辑签到时间", Toast.LENGTH_SHORT).show();
                        showEditAttendTime(attend);
                        break;
                    case TAG_EDIT_ATTEND_TYPE:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "编辑签到类型", Toast.LENGTH_SHORT).show();
                        showEditAttendType(attend);
                        break;
                    case TAG_VIDEO_ATTEND:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "视频签到", Toast.LENGTH_SHORT).show();
                        // TODO
                        break;
                    case TAG_DELETE_ATTEND:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "删除签到", Toast.LENGTH_SHORT).show();
                        deleteAttend(position);
                        break;
                    case TAG_SELF_ATTEND:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "自助签到", Toast.LENGTH_SHORT).show();
                        // TODO
                        break;
                }
            }
        }).build().show();


    }

    /**
     * 展示编辑签到时间弹窗
     *
     * @param attend
     */
    private void showEditAttendTime(AttendShow attend) {
        EditAttendTimeDialogBuilder builder = new EditAttendTimeDialogBuilder(mFragment.getActivity(), attend.getStartTime(), attend.getEndTime());
        builder.setTitle("更改签到时间")
                .setSkinManager(QMUISkinManager.defaultInstance(mFragment.getActivity()))
                .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "Test: 点击确定", Toast.LENGTH_SHORT).show();
                        String newStartTime = builder.tvStartTime.getText().toString();
                        String newEndTime = builder.tvEndTime.getText().toString();

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
     * 展示编辑签到类型弹窗
     *
     * @param attend
     */
    private void showEditAttendType(AttendShow attend) {
        EditAttendTypeDialogBuilder builder = new EditAttendTypeDialogBuilder(mFragment.getActivity(), attend.getAttendType());
        builder.setTitle("更改签到方式")
                .setSkinManager(QMUISkinManager.defaultInstance(mFragment.getActivity()))
                .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "Test: 点击确定", Toast.LENGTH_SHORT).show();
                        Integer attendType = builder.getAttendType();
                        // 一个签到方式都没有选择
                        if (attendType == -1) {
                            Toast.makeText(mFragment.getActivity(), "请至少选择一种签到方式", Toast.LENGTH_SHORT).show();
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
     * 删除签到
     *
     * @param position
     */
    private void deleteAttend(int position) {
        boolean deleteRes = mModel.deleteAttend(mAdapter.getSectionIndex(position), mAdapter.getItemIndex(position));
        if (deleteRes) {
            mAdapter.setDataWithoutDiff(mModel.getData(), false);
            Toast.makeText(mFragment.getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mFragment.getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertAttend(int position) {
        AttendShow toBeInsert = new AttendShow();
        toBeInsert.setHaveAttendCount(-10086);
        mModel.insertAttend(mAdapter.getSectionIndex(position), true, toBeInsert);
        mAdapter.setDataWithoutDiff(mModel.getData(), false);
    }

    /**
     * 删除活动的确认弹窗回调
     */
    private interface DeleteActConfirmCallback {
        void onDelete();
        void onCancel();
    }

}
