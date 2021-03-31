package com.aqinn.actmanagersys.mobile.error;

import android.view.LayoutInflater;
import android.view.View;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.qmuiteam.qmui.widget.QMUIEmptyView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Aqinn
 * @date 2020/12/11 8:30 PM
 */
public class ErrorFragment extends BaseFragment {

    @BindView(R.id.emptyView)
    QMUIEmptyView emptyView;

    private String mTitle = null, mDetail = null;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_error, null);
        ButterKnife.bind(this, root);
        if (mTitle != null)
            emptyView.setTitleText(mTitle);
        else
            emptyView.setTitleText("未知错误");
        if (mDetail != null)
            emptyView.setDetailText(mDetail);
        else
            emptyView.setTitleText("未知错误描述");
        return root;
    }

    public ErrorFragment(String title, String detail) {
        mTitle = title;
        mDetail = detail;
    }

}
