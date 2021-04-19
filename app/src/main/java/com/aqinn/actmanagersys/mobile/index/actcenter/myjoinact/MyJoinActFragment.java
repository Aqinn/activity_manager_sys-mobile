package com.aqinn.actmanagersys.mobile.index.actcenter.myjoinact;

import android.view.LayoutInflater;
import android.view.View;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.myview.RecyclerViewEmptySupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 参加的活动列表 - View
 *
 * @author Aqinn
 * @date 2021/3/30 10:20 AM
 */
public class MyJoinActFragment extends BaseFragment implements IMyJoinAct.View {


    @BindView(R.id.rv_join)
    RecyclerViewEmptySupport rvJoin;

    private IMyJoinAct.Presenter mPresenter;

    private List<ActShow> mData;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_join_act, null);
        ButterKnife.bind(this, root);
        initView(root);
        mPresenter = new MyJoinActPresenter(this);
        mPresenter.initData((BaseFragmentActivity) getActivity(), rvJoin);
        return root;
    }

    private void initView(View root) {
        View emptyView = root.findViewById(R.id.empty_view);
        rvJoin.setEmptyView(emptyView);
    }

}
