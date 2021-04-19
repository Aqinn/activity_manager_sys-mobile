package com.aqinn.actmanagersys.mobile.index.actcenter.mycreateact;

import android.view.LayoutInflater;
import android.view.View;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.myview.RecyclerViewEmptySupport;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建的活动列表 - View
 *
 * @author Aqinn
 * @date 2021/3/30 10:20 AM
 */
public class MyCreateActFragment extends BaseFragment implements IMyCreateAct.View {

    @BindView(R.id.rv_create)
    RecyclerViewEmptySupport rvCreate;

    private IMyCreateAct.Presenter mPresenter;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_create_act, null);
        ButterKnife.bind(this, root);
        initView(root);
        mPresenter = new MyCreateActPresenter(this);
        mPresenter.initData((BaseFragmentActivity) getActivity(), rvCreate);
        return root;
    }

    private void initView(View root) {
        View emptyView = root.findViewById(R.id.empty_view);
        rvCreate.setEmptyView(emptyView);
    }

}
