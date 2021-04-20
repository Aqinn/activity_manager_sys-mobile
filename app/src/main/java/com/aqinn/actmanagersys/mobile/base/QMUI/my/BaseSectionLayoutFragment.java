package com.aqinn.actmanagersys.mobile.base.QMUI.my;

import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.actcard.SectionHeader_Act;
import com.aqinn.actmanagersys.mobile.actcard.SectionItem_Attend;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qmuiteam.qmui.recyclerView.QMUIRVDraggableScrollBar;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 基础的Section布局Fragment
 * 可以根据不同的Adapter或者LayoutFragment调整Fragment的展示
 *
 * @author Aqinn
 * @date 2021/4/17 5:49 PM
 */
@Deprecated
public abstract class BaseSectionLayoutFragment extends BaseFragment {

    private static final String TAG = "BaseSectionLayoutFragme";

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.pull_to_refresh)
    QMUIPullRefreshLayout mPullRefreshLayout;
    @BindView(R.id.section_layout)
    QMUIStickySectionLayout mSectionLayout;
    @BindView(R.id.fbt)
    FloatingActionButton fbt;

    private QMUIPopup mGlobalAction;

    private RecyclerView.LayoutManager mLayoutManager;
    protected QMUIStickySectionAdapter<SectionHeader_Act, SectionItem_Attend, QMUIStickySectionAdapter.ViewHolder> mAdapter;

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_section_layout, null);
        ButterKnife.bind(this, view);
        initTopBar();
        initRefreshLayout();
        initStickyLayout();
        initData();
        return view;
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });
        mTopBar.setVisibility(View.GONE);
    }

    // 初始化刷新布局
    private void initRefreshLayout() {
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
                    }
                }, 500);
            }
        });
    }

    protected void initStickyLayout() {
        mLayoutManager = createLayoutManager();
        mSectionLayout.setLayoutManager(mLayoutManager);
        QMUIRVDraggableScrollBar scrollBar = new QMUIRVDraggableScrollBar(0, 0, 0);
        scrollBar.setEnableScrollBarFadeInOut(false);
        scrollBar.attachToStickSectionLayout(mSectionLayout);
    }

    private void initData() {
        mAdapter = createAdapter();
        mSectionLayout.setAdapter(mAdapter, true);
    }

    protected abstract QMUIStickySectionAdapter<SectionHeader_Act, SectionItem_Attend, QMUIStickySectionAdapter.ViewHolder> createAdapter();

    protected abstract RecyclerView.LayoutManager createLayoutManager();

}
