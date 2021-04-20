package com.aqinn.actmanagersys.mobile.index.attendcenter;

import android.view.LayoutInflater;
import android.view.View;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.model.AttendShow;
import com.aqinn.actmanagersys.mobile.myview.RecyclerViewEmptySupport;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 签到中心 - View
 *
 * @author Aqinn
 * @date 2021/4/1 10:19 AM
 */
@Deprecated
public class AttendCenterFragment extends BaseFragment implements IAttendCenter.View{

//    @BindView(R.id.topbar)
//    QMUITopBarLayout topbar;
    @BindView(R.id.rv)
    RecyclerViewEmptySupport rv;

    private IAttendCenter.Presenter mPresenter;

    private List<AttendShow> mData;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_attend_center, null);
        ButterKnife.bind(this, root);
        initView(root);
        mPresenter = new AttendCenterPresenter(this);
        mPresenter.initRecyclerView(this, rv);
        return root;
    }

    private void initView(View root) {
//        topbar.setTitle(getResources().getString(R.string.attend_center));
        View emptyView = root.findViewById(R.id.empty_view);
        rv.setEmptyView(emptyView);
    }

}
