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
import com.aqinn.actmanagersys.mobile.selfattend.SelfAttendActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUICenterGravityRefreshOffsetCalculator;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.qmuiteam.qmui.widget.section.QMUISection;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * ?????????????????? - Presenter
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

    private final static int WAIT_DIALOG_DISMISS_TIME = 285;

    public ActCardPresenter(IActCard.View view, ActCardType type) {
        mView = view;
        mType = type;
        mModel = new ActCardModel(type);
    }

    /**
     * ?????????????????????
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

    // ?????????TopBar
    private void initTopBar() {
        mTopBar.setVisibility(View.GONE);
    }

    // ?????????????????????
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
                // ?????????????????????????????????????????????
                mPullRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mModel.refreshData(new IActCard.Model.InitDataCallback() {
                            @Override
                            public void onSuccess(String msg) {
                                if (PublicConfig.isDebug)
                                    Toast.makeText(mFragment.getActivity(), "Test: " + msg, Toast.LENGTH_SHORT).show();
                                mPullRefreshLayout.finishRefresh();
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdapter.setData(mModel.getData());
                                        mAdapter.setDataWithoutDiff(mModel.getData(), false);
                                    }
                                }, 200);
                            }

                            @Override
                            public void onError(IActCard.ErrorCode errorCode) {
                                mPullRefreshLayout.finishRefresh();
                                switch (errorCode) {
                                    case INCOMPLETE_DATA:
                                        Toast.makeText(mFragment.getActivity(), "?????????????????????", Toast.LENGTH_SHORT).show();
                                        break;
                                    case FAIL_LOAD_CREATE_ACT:
                                        Toast.makeText(mFragment.getActivity(), IActCard.ErrorCode.FAIL_LOAD_CREATE_ACT.desc, Toast.LENGTH_SHORT).show();
                                        break;
                                    case FAIL_LOAD_JOIN_ACT:
                                        Toast.makeText(mFragment.getActivity(), IActCard.ErrorCode.FAIL_LOAD_JOIN_ACT.desc, Toast.LENGTH_SHORT).show();
                                        break;
                                    case FAIL_LOAD_CREATE_ATTEND:
                                        Toast.makeText(mFragment.getActivity(), IActCard.ErrorCode.FAIL_LOAD_CREATE_ATTEND.desc, Toast.LENGTH_SHORT).show();
                                        break;
                                    case FAIL_LOAD_JOIN_ATTEND:
                                        Toast.makeText(mFragment.getActivity(), IActCard.ErrorCode.FAIL_LOAD_JOIN_ATTEND.desc, Toast.LENGTH_SHORT).show();
                                        break;
                                    case UNKNOWN_RESPONSE_ERROR:
                                        Toast.makeText(mFragment.getActivity(), "??????????????????", Toast.LENGTH_SHORT).show();
                                        break;
                                    case UNKNOWN_NETWORK_ERROR:
                                        Toast.makeText(mFragment.getActivity(), "??????????????????", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        });
                        // mSectionLayout.scrollToPosition(2, true, true);
                    }
                }, 500);
            }
        });
//        mPullRefreshLayout..startScroll(hOffset, vOffset, 0, -vOffset, scrollDuration(mBottomPullAction, vOffset));
//        mPullRefreshLayout.postInvalidateOnAnimation();

    }

    // ?????????Section????????????
    private void initStickyLayout() {
        mLayoutManager = createLayoutManager();
        mSectionLayout.setLayoutManager(mLayoutManager);
//        QMUIRVDraggableScrollBar scrollBar = new QMUIRVDraggableScrollBar(0, 0, 0);
//        scrollBar.setEnableScrollBarFadeInOut(false);
//        scrollBar.attachToStickSectionLayout(mSectionLayout);
//        scrollBar.setDraggable(false);
    }

    // ???????????????
    private void initData() {
        // ????????????
        mAdapter = createAdapter();
        mSectionLayout.setAdapter(mAdapter, true);
        final QMUITipDialog tipDialog = new QMUITipDialog.Builder(mFragment.getActivity())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("????????????")
                .create();
        tipDialog.show();
        mModel.initData(new IActCard.Model.InitDataCallback() {
            @Override
            public void onSuccess(String msg) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tipDialog.dismiss();
                    }
                }, 50);
                // Toast.makeText(mFragment.getActivity(), msg, Toast.LENGTH_SHORT).show();
                mAdapter.setData(mModel.getData());
                mAdapter.setDataWithoutDiff(mModel.getData(), false);
                Log.d(TAG, "onSuccess: " + mModel.getData());
            }

            @Override
            public void onError(IActCard.ErrorCode errorCode) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tipDialog.dismiss();
                    }
                }, 50);
                switch (errorCode) {
                    case INCOMPLETE_DATA:
                        Toast.makeText(mFragment.getActivity(), "?????????????????????", Toast.LENGTH_SHORT).show();
                        break;
                    case FAIL_LOAD_CREATE_ACT:
                        Toast.makeText(mFragment.getActivity(), IActCard.ErrorCode.FAIL_LOAD_CREATE_ACT.desc, Toast.LENGTH_SHORT).show();
                        break;
                    case FAIL_LOAD_JOIN_ACT:
                        Toast.makeText(mFragment.getActivity(), IActCard.ErrorCode.FAIL_LOAD_JOIN_ACT.desc, Toast.LENGTH_SHORT).show();
                        break;
                    case FAIL_LOAD_CREATE_ATTEND:
                        Toast.makeText(mFragment.getActivity(), IActCard.ErrorCode.FAIL_LOAD_CREATE_ATTEND.desc, Toast.LENGTH_SHORT).show();
                        break;
                    case FAIL_LOAD_JOIN_ATTEND:
                        Toast.makeText(mFragment.getActivity(), IActCard.ErrorCode.FAIL_LOAD_JOIN_ATTEND.desc, Toast.LENGTH_SHORT).show();
                        break;
                    case UNKNOWN_RESPONSE_ERROR:
                        Toast.makeText(mFragment.getActivity(), "??????????????????", Toast.LENGTH_SHORT).show();
                        break;
                    case UNKNOWN_NETWORK_ERROR:
                        Toast.makeText(mFragment.getActivity(), "??????????????????", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    // ??????Section??????????????????
    private QMUIStickySectionAdapter<SectionHeader_Act, SectionItem_Attend, QMUIStickySectionAdapter.ViewHolder> createAdapter() {
        mAdapter = new ActListWithDecorationSectionAdapter(mType, mModel.getActList(), mModel.getActAttendMap());
        mAdapter.setCallback(new QMUIStickySectionAdapter.Callback<SectionHeader_Act, SectionItem_Attend>() {
            /**
             * ?????????????????????Attend
             * @param section
             * @param loadMoreBefore
             */
            @Override
            public void loadMore(final QMUISection<SectionHeader_Act, SectionItem_Attend> section, final boolean loadMoreBefore) {
                mSectionLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                            ArrayList<SectionItem_Attend> list = new ArrayList<>();
//                            for (int i = 0; i < 3; i++) {
//                                AttendShow attend = new AttendShow();
//                                attend.setName(attend.getName() + ", No." + i);
//                                list.add(new SectionItem_Attend(attend));
//                            }
//                            mAdapter.finishLoadMore(section, list, loadMoreBefore, false);
                    }
                }, 3000);
            }

            @Override
            public void onItemClick(QMUIStickySectionAdapter.ViewHolder holder, int position) {
                // ?????????item???...
//                if (mAdapter.getItemViewType(position) == QMUIStickySectionAdapter.ITEM_TYPE_SECTION_ITEM) {
//                    if (PublicConfig.isDebug) {
//                        Toast.makeText(mFragment.getContext(), "Test: ???item, ?????????????????? " + String.valueOf(attend.getStatus()), Toast.LENGTH_SHORT).show();
//                    }
//                } else if (mAdapter.getItemViewType(position) == QMUIStickySectionAdapter.ITEM_TYPE_SECTION_HEADER) {
//                    ActCardSectionAdapter.SectionHeaderViewHolder realHolder = (ActCardSectionAdapter.SectionHeaderViewHolder) holder;
//                    QMUISection<SectionHeader_Act, SectionItem_Attend> section = mAdapter.getSection(position);
//                    SectionHeader_Act sectionHeaderAct = section.getHeader();
//                    ActShow act = sectionHeaderAct.getAct();
//                }
            }

            /**
             * ??????
             * @param holder
             * @param position ??????position???1?????????
             * @return
             */
            @Override
            public boolean onItemLongClick(QMUIStickySectionAdapter.ViewHolder holder, int position) {
//                if (PublicConfig.isDebug)
//                    Toast.makeText(mFragment.getContext(), "Test: \nlong click item " + position + "\n"
//                            + "mAdapter.getItemIndex(position)" + mAdapter.getItemIndex(position) + "\n"
//                            + "mAdapter.getSectionIndex(position)" + mAdapter.getSectionIndex(position) + "\n"
//                            + "mAdapter.getItemViewType(position)" + mAdapter.getItemViewType(position) + "\n", Toast.LENGTH_SHORT).show();
                // ?????????header?????????????????????
                if (mAdapter.getItemViewType(position) == QMUIStickySectionAdapter.ITEM_TYPE_SECTION_HEADER) {
//                    if (PublicConfig.isDebug) {
//                        Toast.makeText(mFragment.getContext(), "Test: ???header", Toast.LENGTH_SHORT).show();
//                    }
                    ActShow act = mModel.getActList().get(mAdapter.getSectionIndex(position));
                    showActBottomSheetGrid(act, position);
                } else if (mAdapter.getItemViewType(position) == QMUIStickySectionAdapter.ITEM_TYPE_SECTION_ITEM) {
                    ActShow act = mModel.getActList().get(mAdapter.getSectionIndex(position));
                    List<AttendShow> attendList = mModel.getActAttendMap().get(act);
                    AttendShow attend = attendList.get(mAdapter.getItemIndex(position));
                    showAttendBottomSheetGrid(attend, position);
                }
                return true;
            }
        });
        return mAdapter;
    }

    // ?????????????????????
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
     * ???????????????????????????Grid??????
     */
    private void showActBottomSheetGrid(ActShow act, int position) {
        final int TAG_ACT_DETAIL = 0;
        final int TAG_ACT_CODE = 1;
        final int TAG_EDIT_ACT = 2;
        final int TAG_CREATE_ATTEND = 3;
        final int TAG_DELETE_ACT = 4;
        final int TAG_QUIT_ACT = 5;
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(mFragment.getActivity());
        builder.addItem(R.mipmap.icon_act_detail_small, "????????????", TAG_ACT_DETAIL, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.icon_act_code_small, "????????????", TAG_ACT_CODE, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .setAddCancelBtn(true)
                .setSkinManager(QMUISkinManager.defaultInstance(mFragment.getContext()));
        if (mType == ActCardType.FLAG_CREATE) {
            builder.addItem(R.mipmap.icon_update_act_small, "????????????", TAG_EDIT_ACT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_create_attend_small, "????????????", TAG_CREATE_ATTEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_delete_act_small, "????????????", TAG_DELETE_ACT, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE);
        } else if (mType == ActCardType.FLAG_JOIN) {
            builder.addItem(R.mipmap.icon_quit_act_small, "????????????", TAG_QUIT_ACT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
        }
        builder.setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
            @Override
            public void onClick(QMUIBottomSheet dialog, View itemView) {
                dialog.dismiss();
                int tag = (int) itemView.getTag();
                switch (tag) {
                    case TAG_ACT_DETAIL:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "????????????", Toast.LENGTH_SHORT).show();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showActDetail(act);
//                                insertAct(position);
                            }
                        }, WAIT_DIALOG_DISMISS_TIME);
                        break;
                    case TAG_EDIT_ACT:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "????????????", Toast.LENGTH_SHORT).show();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ActDetailFragment fragment = new ActDetailFragment(act, true, true, new ActDetailFragment.UpdateActCallback() {
                                    @Override
                                    public void onSuccess(ActShow newAct) {
                                        updateAct(newAct, position);
                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                                mFragment.startFragment(fragment);
                            }
                        }, WAIT_DIALOG_DISMISS_TIME);
//                        updateAct(new ActShow(), position);
                        break;
                    case TAG_ACT_CODE:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "????????????", Toast.LENGTH_SHORT).show();
                        showActCode(act);
                        break;
                    case TAG_CREATE_ATTEND:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "????????????", Toast.LENGTH_SHORT).show();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showCreateAttend(act, position);
                            }
                        }, WAIT_DIALOG_DISMISS_TIME);
                        break;
                    case TAG_DELETE_ACT:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "????????????", Toast.LENGTH_SHORT).show();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showDeleteActConfirmDialog(position, act);
                            }
                        }, WAIT_DIALOG_DISMISS_TIME);
                        break;
                    case TAG_QUIT_ACT:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "????????????", Toast.LENGTH_SHORT).show();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showQuitActConfirmDialog(position, act);
                            }
                        }, WAIT_DIALOG_DISMISS_TIME);
                        break;
                }
            }
        }).build().show();


    }

    /**
     * ??????????????????
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
     * ?????????????????????????????????
     *
     * @param act
     */
    private void showActCode(ActShow act) {
//        final QMUITipDialog
////                                        tipDialog = new QMUITipDialog.CustomBuilder(mFragment.getContext())
////                                        .setContent(R.layout.tipdialog_custom)
////                                        .create();
//                tipDialog = new QMUITipDialog.Builder(mFragment.getContext())
//                .setTipWord("????????????: " + act.getCode() + "\n"
//                        + "????????????: " + act.getPwd())
//                .create();
//        tipDialog.show();
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                tipDialog.dismiss();
//            }
//        }, 1500);

//        Toast.makeText(mFragment.getActivity(), "????????????: " + act.getCode() + "\n"
//                + "????????????: " + act.getPwd(), Toast.LENGTH_SHORT).show();

        Snackbar codeAndPwdSnackbar = Snackbar.make(mFragment.getView(), "????????????: " + act.getCode() + "\n"
                + "????????????: " + act.getPwd(), Snackbar.LENGTH_INDEFINITE).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        codeAndPwdSnackbar
                .setActionTextColor(BaseApplication.getContext().getColor(R.color.app_color_blue))
                .setAction("??????", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        codeAndPwdSnackbar.dismiss();
                    }
                }).show();
    }

    /**
     * ???????????????????????????
     *
     * @param act
     */
    private void showCreateAttend(ActShow act, int position) {
//        CreateAttendBottomSheetDialogBuilder builder = new CreateAttendBottomSheetDialogBuilder(mFragment.getActivity());
//        builder.setTitle(mFragment.getString(R.string.create_attend))
//                .setAddCancelBtn(true)
//                .setCancelText("??????")
//                .setSkinManager(QMUISkinManager.defaultInstance(mFragment.getContext()))
//                .build().show();
        CreateAttendDialogBuilder builder = new CreateAttendDialogBuilder(mFragment.getActivity());
        builder.setTitle("????????????")
                .setSkinManager(QMUISkinManager.defaultInstance(mFragment.getActivity()))
                .addAction(0, "??????", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "Test: ????????????", Toast.LENGTH_SHORT).show();
                        insertAttend(position);
                    }
                })
                .addAction(0, "??????", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create(PublicConfig.mCurrentDialogStyle).show();
    }

    /**
     * ?????????????????????????????????
     *
     * @param position
     * @param act
     */
    private void showDeleteActConfirmDialog(int position, ActShow act) {
        new QMUIDialog.MessageDialogBuilder(mFragment.getActivity())
                .setTitle("????????????")
                .setMessage("?????????????????????\"" + act.getName() + "\"??????")
                .setSkinManager(QMUISkinManager.defaultInstance(mFragment.getContext()))
                .addAction("??????", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "??????", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        deleteAct(position);
                    }
                })
                .create(PublicConfig.mCurrentDialogStyle).show();
    }

    /**
     * ?????????????????????????????????
     *
     * @param position
     * @param act
     */
    private void showQuitActConfirmDialog(int position, ActShow act) {
        new QMUIDialog.MessageDialogBuilder(mFragment.getActivity())
                .setTitle("????????????")
                .setMessage("?????????????????????\"" + act.getName() + "\"??????")
                .setSkinManager(QMUISkinManager.defaultInstance(mFragment.getContext()))
                .addAction("??????", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "??????", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        quitAct(position);
                    }
                })
                .create(PublicConfig.mCurrentDialogStyle).show();
    }

    @Override
    public void insertAct(int position, ActShow act, List<AttendShow> attendList) {
        // ????????????
        Log.d(TAG, "onItemLongClick: " + position);
        boolean isLastThree = mAdapter.getSectionCount() <= (position + 2);
        mModel.insertAct(position - 1, act, attendList, new IActCard.Model.InsertActCallback() {
            @Override
            public void onSuccess() {
                if (!isLastThree)
                    mAdapter.notifyItemInserted(position);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setDataWithoutDiff(mModel.getData(), false);
                    }
                }, WAIT_DIALOG_DISMISS_TIME);
            }

            @Override
            public void onError() {
                Toast.makeText(mFragment.getActivity(), mType == ActCardType.FLAG_CREATE ? "??????????????????" : "??????????????????", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteAct(int position) {
        boolean isLastSection = mAdapter.getSectionCount() == position;
        Log.d(TAG, "onDelete: " + mAdapter.getSectionCount() + ", " + position);
        mModel.deleteAct(mAdapter.getSectionIndex(position), new ActCardModel.DeleteActCallback() {
            @Override
            public void onSuccess() {
                if (isLastSection) {
                    mAdapter.setDataWithoutDiff(mModel.getData(), false);
                } else {
                    mAdapter.notifyItemRemoved(position);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setDataWithoutDiff(mModel.getData(), false);
                        }
                    }, WAIT_DIALOG_DISMISS_TIME);
                }
                Toast.makeText(mFragment.getActivity(), "????????????", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                Toast.makeText(mFragment.getActivity(), "????????????", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void quitAct(int position) {
        boolean isLastSection = mAdapter.getSectionCount() == position;
        Log.d(TAG, "onDelete: " + mAdapter.getSectionCount() + ", " + position);
        mModel.quitAct(mAdapter.getSectionIndex(position), new IActCard.Model.QuitActCallback() {
            @Override
            public void onSuccess() {
                if (isLastSection) {
                    mAdapter.setDataWithoutDiff(mModel.getData(), false);
                } else {
                    mAdapter.notifyItemRemoved(position);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setDataWithoutDiff(mModel.getData(), false);
                        }
                    }, WAIT_DIALOG_DISMISS_TIME);
                }
                Toast.makeText(mFragment.getActivity(), "??????????????????", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                Toast.makeText(mFragment.getActivity(), "??????????????????", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * ActDetailPresenter???????????????????????????????????????????????????????????????
     *
     * @param act
     * @param position
     */
    private void updateAct(ActShow act, int position) {
        mModel.updateAct(mAdapter.getSectionIndex(position), act, new IActCard.Model.UpdateActCallback() {
            @Override
            public void onSuccess() {
                mAdapter.setDataWithoutDiff(mModel.getData(), false);
            }

            @Override
            public void onError() {
                Toast.makeText(mFragment.getActivity(), "??????????????????", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * ?????????????????????Grid??????
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
            builder.addItem(R.mipmap.icon_act_detail_small, "??????????????????", TAG_EDIT_ATTEND_TIME, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_act_code_small, "??????????????????", TAG_EDIT_ATTEND_TYPE, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_act_code_small, "????????????", TAG_VIDEO_ATTEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_delete_act_small, "????????????", TAG_DELETE_ATTEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
        } else if (mType == ActCardType.FLAG_JOIN) {
            builder.addItem(R.mipmap.icon_act_detail_small, "????????????", TAG_SELF_ATTEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
        }
        builder.setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
            @Override
            public void onClick(QMUIBottomSheet dialog, View itemView) {
                dialog.dismiss();
                int tag = (int) itemView.getTag();
                switch (tag) {
                    case TAG_EDIT_ATTEND_TIME:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "??????????????????", Toast.LENGTH_SHORT).show();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showEditAttendTime(position, attend);
                            }
                        }, WAIT_DIALOG_DISMISS_TIME);
                        break;
                    case TAG_EDIT_ATTEND_TYPE:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "??????????????????", Toast.LENGTH_SHORT).show();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showEditAttendType(position, attend);
                            }
                        }, WAIT_DIALOG_DISMISS_TIME);
                        break;
                    case TAG_VIDEO_ATTEND:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "????????????", Toast.LENGTH_SHORT).show();
                        // TODO
                        break;
                    case TAG_DELETE_ATTEND:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "????????????", Toast.LENGTH_SHORT).show();
                        deleteAttend(position);
                        break;
                    case TAG_SELF_ATTEND:
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "????????????", Toast.LENGTH_SHORT).show();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent selfAttendIntent = new Intent(mFragment.getActivity(), SelfAttendActivity.class);
                                mFragment.startActivity(selfAttendIntent);
                            }
                        }, WAIT_DIALOG_DISMISS_TIME);
                        break;
                }
            }
        }).build().show();


    }

    /**
     * ??????????????????????????????
     *
     * @param position
     * @param attend
     */
    private void showEditAttendTime(int position, AttendShow attend) {
        EditAttendTimeDialogBuilder builder = new EditAttendTimeDialogBuilder(mFragment.getActivity(), attend.getStartTime(), attend.getEndTime());
        builder.setTitle("??????????????????")
                .setSkinManager(QMUISkinManager.defaultInstance(mFragment.getActivity()))
                .addAction(0, "??????", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "Test: ????????????", Toast.LENGTH_SHORT).show();
                        String newStartTime = builder.tvStartTime.getText().toString();
                        String newEndTime = builder.tvEndTime.getText().toString();
                        attend.setStartTime(newStartTime);
                        attend.setEndTime(newEndTime);
                        mModel.updateAttendTime(mAdapter.getSectionIndex(position), mAdapter.getItemIndex(position), attend, new IActCard.Model.UpdateAttendTimeCallback() {
                            @Override
                            public void onSuccess() {
                                mAdapter.setDataWithoutDiff(mModel.getData(), false);
                                Toast.makeText(mFragment.getActivity(), "????????????????????????", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError() {
                                Toast.makeText(mFragment.getActivity(), "????????????????????????", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .addAction(0, "??????", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create(PublicConfig.mCurrentDialogStyle).show();
    }

    /**
     * ??????????????????????????????
     *
     * @param position
     * @param attend
     */
    private void showEditAttendType(int position, AttendShow attend) {
        EditAttendTypeDialogBuilder builder = new EditAttendTypeDialogBuilder(mFragment.getActivity(), attend.getAttendType());
        builder.setTitle("??????????????????")
                .setSkinManager(QMUISkinManager.defaultInstance(mFragment.getActivity()))
                .addAction(0, "??????", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        if (PublicConfig.isDebug)
                            Toast.makeText(mFragment.getActivity(), "Test: ????????????", Toast.LENGTH_SHORT).show();
                        Integer attendType = builder.getAttendType();
                        // ?????????????????????????????????
                        if (attendType == -1) {
                            Toast.makeText(mFragment.getActivity(), "?????????????????????????????????", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        dialog.dismiss();
                        attend.setAttendType(builder.getAttendType());
                        mModel.updateAttendType(mAdapter.getSectionIndex(position), mAdapter.getItemIndex(position), attend, new IActCard.Model.UpdateAttendTypeCallback() {
                            @Override
                            public void onSuccess() {
                                mAdapter.setDataWithoutDiff(mModel.getData(), false);
                                Toast.makeText(mFragment.getActivity(), "????????????????????????", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError() {
                                Toast.makeText(mFragment.getActivity(), "????????????????????????", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .addAction(0, "??????", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create(PublicConfig.mCurrentDialogStyle).show();
    }

    /**
     * ????????????
     *
     * @param position
     */
    private void deleteAttend(int position) {
        mModel.deleteAttend(mAdapter.getSectionIndex(position), mAdapter.getItemIndex(position), new IActCard.Model.DeleteAttendCallback() {
            @Override
            public void onSuccess() {
                mAdapter.setDataWithoutDiff(mModel.getData(), false);
                Toast.makeText(mFragment.getActivity(), "??????????????????", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                Toast.makeText(mFragment.getActivity(), "??????????????????", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertAttend(int position) {
        AttendShow toBeInsert = new AttendShow();
        toBeInsert.setHaveAttendCount(-10086);
        mModel.insertAttend(mAdapter.getSectionIndex(position), true, toBeInsert, new IActCard.Model.InsertAttendCallback() {
            @Override
            public void onSuccess(AttendShow attend) {
                mAdapter.setDataWithoutDiff(mModel.getData(), false);
                Toast.makeText(mFragment.getActivity(), "??????????????????", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                Toast.makeText(mFragment.getActivity(), "??????????????????", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * ?????????????????????????????????
     */
    private interface DeleteActConfirmCallback {
        void onDelete();

        void onCancel();
    }

    /**
     * ?????????????????????????????????
     */
    private interface QuitActConfirmCallback {
        void onDelete();

        void onCancel();
    }

}
