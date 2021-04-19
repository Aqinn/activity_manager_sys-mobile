package com.aqinn.actmanagersys.mobile.index.attendcenter.createattend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseDialogFragment;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.myview.CustomDatePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建签到 - View
 *
 * @author Aqinn
 * @date 2021/4/16 8:26 PM
 */
@Deprecated
public class CreateAttendFragment extends BaseDialogFragment implements ICreateAttend.View {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_type_text)
    TextView tvTypeText;
    @BindView(R.id.cb_self)
    CheckBox cbSelf;
    @BindView(R.id.cb_video)
    CheckBox cbVideo;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.al_find)
    ConstraintLayout alFind;
    @BindView(R.id.tv_start_time_text)
    TextView tvStartTimeText;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time_text)
    TextView tvEndTimeText;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;

    private ICreateAttend.Presenter mPresenter;

    public CustomDatePicker startTimePicker;
    public CustomDatePicker endTimePicker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_attend, container);
        ButterKnife.bind(this, root);
        initDialog();
        mPresenter = new CreateAttendPresenter(this);
        mPresenter.initTimePicker(this, tvStartTime, tvEndTime);
        return root;
    }

    @OnClick({R.id.tv_start_time, R.id.tv_end_time, R.id.bt_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                startTimePicker.show(tvStartTime.getText().toString());
                break;
            case R.id.tv_end_time:
                endTimePicker.show(tvEndTime.getText().toString());
                break;
            case R.id.bt_confirm:
                if (PublicConfig.isDebug) {
                    Toast.makeText(getActivity(), "Test: 点击了确认", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
                break;
        }
    }

}
