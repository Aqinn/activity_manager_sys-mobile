package com.aqinn.actmanagersys.mobile.index.act.createact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.common.OnRecyclerItemClickListener;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.index.act.actdetail.ActDetailFragment;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.myview.RecyclerViewEmptySupport;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.popup.QMUIQuickAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建的活动 - View
 *
 * @author Aqinn
 * @date 2021/3/30 10:20 AM
 */
public class CreateActFragment extends BaseFragment implements ICreateAct.View {

    @BindView(R.id.rv_create)
    RecyclerViewEmptySupport rvCreate;

    private ICreateAct.Presenter mPresenter;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_create_act, null);
        ButterKnife.bind(this, root);
        initView(root);
        mPresenter = new CreateActPresenter(this);
        mPresenter.initData((BaseFragmentActivity) getActivity(), rvCreate);
        return root;
    }

    private void initView(View root) {
        View emptyView = root.findViewById(R.id.empty_view);
        rvCreate.setEmptyView(emptyView);
    }

}
