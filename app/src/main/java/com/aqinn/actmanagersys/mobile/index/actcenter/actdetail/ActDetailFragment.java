package com.aqinn.actmanagersys.mobile.index.actcenter.actdetail;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragment;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.model.ActShow;
import com.aqinn.actmanagersys.mobile.myview.CustomDatePicker;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 活动详情 - View
 *
 * @author Aqinn
 * @date 2021/4/5 2:34 PM
 */
public class ActDetailFragment extends BaseFragment implements IActDetail.View {

    @BindView(R.id.topbar)
    QMUITopBarLayout topbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_creator_text)
    TextView tvCreatorText;
    @BindView(R.id.tv_creator)
    TextView tvCreator;
    @BindView(R.id.tv_loc_text)
    TextView tvLocText;
    @BindView(R.id.et_loc)
    EditText etLoc;
    @BindView(R.id.tv_intro_text)
    TextView tvIntroText;
    @BindView(R.id.et_intro)
    EditText etIntro;
    @BindView(R.id.tv_start_time_text)
    TextView tvStartTimeText;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time_text)
    TextView tvEndTimeText;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;

    private IActDetail.Presenter mPresenter;

    private boolean isEditable;
    private boolean isEditableMode;
    private ActShow mAct;
    private QMUIAlphaImageButton btMenu;
    private QMUIAlphaImageButton btEdit;
    public CustomDatePicker startTimePicker;
    public CustomDatePicker endTimePicker;

    /**
     * 构造函数
     *
     * @param item       要展示的Act, 为空则代表当前是要创建活动
     * @param isEditable 能否编辑
     * @param isEditMode 是否进入编辑模式
     */
    public ActDetailFragment(ActShow item, boolean isEditable, boolean isEditMode) {
        mAct = item;
        this.isEditable = isEditable;
        this.isEditableMode = isEditMode;
    }

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_act_detail, null);
        ButterKnife.bind(this, root);
        initView();
        mPresenter = new ActDetailPresenter(this);
        mPresenter.initTimePicker(this, tvStartTime, tvEndTime);
        return root;
    }

    private void initView() {
        topbar.setTitle(getResources().getString(R.string.act_detail));
        btMenu = topbar.addRightImageButton(R.mipmap.icon_topbar_overflow, R.id.topbar_right_change_button);
        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetList();
            }
        });
        if (mAct == null)
            btMenu.setVisibility(View.GONE);
        btEdit = topbar.addRightImageButton(R.mipmap.edit_finish, R.id.bt_edit_finish);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PublicConfig.isDebug)
                    Toast.makeText(getActivity(), "Test: 编辑按钮", Toast.LENGTH_SHORT).show();
                ActShow act = new ActShow();
                act.setName(etName.getText().toString());
                act.setDesc(etIntro.getText().toString());
                act.setLocation(etLoc.getText().toString());
                act.setStartTime(tvStartTime.getText().toString());
                act.setEndTime(tvEndTime.getText().toString());
                // 创建活动
                if (mAct == null) {
                    mPresenter.createAct(act, new IActDetail.Presenter.Callback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getActivity(), "活动创建成功", Toast.LENGTH_SHORT).show();
                            editModeOff();
                            popBackStack();
                        }

                        @Override
                        public void onError(IActDetail.ErrorCode errorCode) {
                            switch (errorCode) {
                                case ACT_REPEAT:
                                    Toast.makeText(getActivity(), "活动重复创建", Toast.LENGTH_SHORT).show();
                                    break;
                                case INFO_ILLEGAL:
                                    Toast.makeText(getActivity(), "活动信息格式不符合规范，请检查", Toast.LENGTH_SHORT).show();
                                    break;
                                case UNKNOWN_NETWORK_ERROR:
                                    Toast.makeText(getActivity(), "未知网络错误，请稍后再试", Toast.LENGTH_SHORT).show();
                                    break;
                                case UNKNOWN_RESPONSE_ERROR:
                                    Toast.makeText(getActivity(), "未知响应错误", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
                }
                // 修改活动
                else {
                    mPresenter.editAct(act, new IActDetail.Presenter.Callback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getActivity(), "活动编辑成功", Toast.LENGTH_SHORT).show();
                            editModeOff();
                        }

                        @Override
                        public void onError(IActDetail.ErrorCode errorCode) {
                            switch (errorCode) {
                                case ACT_REPEAT:
                                    Toast.makeText(getActivity(), "活动重复创建", Toast.LENGTH_SHORT).show();
                                    break;
                                case INFO_ILLEGAL:
                                    Toast.makeText(getActivity(), "活动信息格式不符合规范，请检查", Toast.LENGTH_SHORT).show();
                                    break;
                                case UNKNOWN_NETWORK_ERROR:
                                    Toast.makeText(getActivity(), "未知网络错误，请稍后再试", Toast.LENGTH_SHORT).show();
                                    break;
                                case UNKNOWN_RESPONSE_ERROR:
                                    Toast.makeText(getActivity(), "未知响应错误", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
                }
            }
        });
        btEdit.setVisibility(View.INVISIBLE);
        if (mAct == null) {

        } else if (PublicConfig.isDebug) {
            etName.setText(mAct.getName());
            etLoc.setText(mAct.getLocation());
            etIntro.setText(mAct.getDesc());
            tvStartTime.setText(mAct.getStartTime());
            tvEndTime.setText(mAct.getEndTime());
        }
        if (isEditableMode) {
            editModeOn();
        } else {
            editModeOff();
        }
    }

    /**
     * 弹出底部菜单栏
     */
    private void showBottomSheetList() {
        final QMUIBottomSheet.BottomListSheetBuilder sh = new QMUIBottomSheet.BottomListSheetBuilder(getActivity());
        sh.addItem("查看签到");
        if (isEditable) {
            sh.addItem("编辑活动");
            if (mAct.getStatus() == 2)
                sh.addItem("结束活动");
            sh.addItem("创建签到");
        }
        if (!isEditable) {
            sh.addItem("退出活动");
        }
        sh.setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
            @Override
            public void onClick(QMUIBottomSheet dialog, final View itemView, int position, String tag) {
                dialog.dismiss();
                switch (position) {
                    case 0:
                        // 查看签到
                        if (PublicConfig.isDebug)
                            Toast.makeText(getActivity(), "Test: 查看签到", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        // 编辑活动
                        if (isEditable) {
                            if (PublicConfig.isDebug)
                                Toast.makeText(getActivity(), "Test: 编辑活动", Toast.LENGTH_SHORT).show();
                            editModeOn();
                        }
                        // 退出活动
                        else {
                            if (PublicConfig.isDebug)
                                Toast.makeText(getActivity(), "Test: 退出活动", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        // 结束活动
                        if (mAct.getStatus() == 2) {
                            if (PublicConfig.isDebug)
                                Toast.makeText(getContext(), "Test: 结束活动", Toast.LENGTH_SHORT).show();
                        }
                        // 创建签到
                        else {
                            createAttend();
                        }
                        break;
                    case 3:
                        // 创建签到
                        createAttend();
                        break;
                    default:
                        break;
                }
            }

            private void createAttend() {
                if (PublicConfig.isDebug)
                    Toast.makeText(getContext(), "Test: 创建签到", Toast.LENGTH_SHORT).show();

            }
        }).build().show();
    }

    /**
     * 开启编辑模式
     */
    private void editModeOn() {
        etEnabledChanged(1);
        topbar.findViewById(R.id.topbar_right_change_button).setClickable(false);
        btEdit.setVisibility(View.VISIBLE);
    }

    /**
     * 关闭编辑模式
     */
    private void editModeOff() {
        btEdit.setVisibility(View.INVISIBLE);
        etEnabledChanged(0);
        topbar.findViewById(R.id.topbar_right_change_button).setClickable(true);
    }

    /**
     * 更换 EditText 的可编辑状态
     *
     * @param status 0: 全为 false
     *               1: 全为 true
     *               2: 自动切换为另一个状态
     */
    private void etEnabledChanged(int status) {
        switch (status) {
            case 0:
                etName.setEnabled(false);
                tvStartTime.setEnabled(false);
                tvEndTime.setEnabled(false);
                etLoc.setEnabled(false);
                etIntro.setEnabled(false);
                break;
            case 1:
                etName.setEnabled(true);
                tvStartTime.setEnabled(true);
                tvEndTime.setEnabled(true);
                etLoc.setEnabled(true);
                etIntro.setEnabled(true);
                break;
            case 2:
                etName.setEnabled(!etName.isEnabled());
                tvStartTime.setEnabled(!tvStartTime.isEnabled());
                tvEndTime.setEnabled(!tvEndTime.isEnabled());
                etLoc.setEnabled(!etLoc.isEnabled());
                etIntro.setEnabled(!etIntro.isEnabled());
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.tv_start_time, R.id.tv_end_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                startTimePicker.show(tvStartTime.getText().toString());
                break;
            case R.id.tv_end_time:
                endTimePicker.show(tvEndTime.getText().toString());
                break;
        }
    }

}
