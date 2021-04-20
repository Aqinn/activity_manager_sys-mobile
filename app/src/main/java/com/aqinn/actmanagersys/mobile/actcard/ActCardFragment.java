package com.aqinn.actmanagersys.mobile.actcard;

import android.view.LayoutInflater;
import android.view.View;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 活动卡片列表界面 - View
 *
 * @author Aqinn
 * @date 2021/4/18 4:38 PM
 */
public class ActCardFragment extends BaseFragment implements IActCard.View {


    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.pull_to_refresh)
    QMUIPullRefreshLayout mPullRefreshLayout;
    @BindView(R.id.section_layout)
    QMUIStickySectionLayout mSectionLayout;

    private final ActCardType mType;

    public IActCard.Presenter mPresenter;

    public ActCardFragment(ActCardType type) {
        mType = type;
    }

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_section_layout, null);
        ButterKnife.bind(this, view);
        mPresenter = new ActCardPresenter(this, mType);
        mPresenter.init(this, mTopBar, mPullRefreshLayout, mSectionLayout);
        return view;
    }

}
