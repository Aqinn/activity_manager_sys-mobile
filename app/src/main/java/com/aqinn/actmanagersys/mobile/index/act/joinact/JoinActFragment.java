package com.aqinn.actmanagersys.mobile.index.act.joinact;

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
 * 参加的活动 - View
 *
 * @author Aqinn
 * @date 2021/3/30 10:20 AM
 */
public class JoinActFragment extends BaseFragment implements IJoinAct.View {


    @BindView(R.id.rv_join)
    RecyclerViewEmptySupport rvJoin;

    private IJoinAct.Presenter mPresenter;

    private List<ActShow> mData;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_join_act, null);
        ButterKnife.bind(this, root);
        initView(root);
        mPresenter = new JoinActPresenter(this);
        mPresenter.initData((BaseFragmentActivity) getActivity(), rvJoin);
        return root;
    }

    private void initView(View root) {
        View emptyView = root.findViewById(R.id.empty_view);
        rvJoin.setEmptyView(emptyView);
    }

}
