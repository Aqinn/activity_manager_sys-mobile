package com.aqinn.actmanagersys.mobile.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.login.LoginActivity;
import com.aqinn.actmanagersys.mobile.model.User;
import com.qmuiteam.qmui.util.QMUIDeviceHelper;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册功能 - View
 * @author Aqinn
 * @date 2021/3/29 4:55 PM
 */
public class RegisterActivity extends BaseFragmentActivity implements IRegister.View {

    private static final String TAG = "RegisterActivity";

    @BindView(R.id.topbar)
    QMUITopBarLayout topbar;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_repeat)
    EditText etPasswordRepeat;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_girl)
    RadioButton rbGirl;
    @BindView(R.id.et_sex)
    LinearLayout etSex;
    @BindView(R.id.et_intro)
    EditText etIntro;
    @BindView(R.id.gl_input)
    GridLayout glInput;
    @BindView(R.id.bt_register)
    QMUIRoundButton btRegister;
    @BindView(R.id.sv)
    ScrollView sv;

    private IRegister.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
        mPresenter = new RegisterPresenter(this);
    }

    private void initView() {
        topbar.setTitle(getResources().getString(R.string.register_center));
    }

    @OnClick({R.id.bt_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_register:
                // TODO 这里的User不能为空User
                User user = new User();
                mPresenter.register(user);
                break;
        }
    }

    @Override
    public void registerSuccess() {
        if (PublicConfig.isDebug)
            Toast.makeText(this, "Test: 注册成功，跳转至登录界面", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void registerError(IRegister.ErrorCode errCode) {
        switch (errCode) {
            case ACCOUNT_REPEAT:
                Toast.makeText(this, "账号已存在", Toast.LENGTH_SHORT).show();
                break;
            case UNKNOWN_ERROR:
                Toast.makeText(this, "注册遇到未知错误，请联系客服人员", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

}