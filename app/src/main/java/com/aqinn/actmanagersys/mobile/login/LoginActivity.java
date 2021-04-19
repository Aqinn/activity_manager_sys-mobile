package com.aqinn.actmanagersys.mobile.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.aqinn.actmanagersys.mobile.R;
import com.aqinn.actmanagersys.mobile.base.BaseFragmentActivity;
import com.aqinn.actmanagersys.mobile.base.PublicConfig;
import com.aqinn.actmanagersys.mobile.index.IndexActivity;
import com.aqinn.actmanagersys.mobile.register.RegisterActivity;
import com.aqinn.actmanagersys.mobile.utils.SPUtil;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录功能 - View
 *
 * @author Aqinn
 * @date 2021/3/29 10:59 AM
 */
public class LoginActivity extends BaseFragmentActivity implements ILogin.View {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.iv_head)
    QMUIRadiusImageView2 ivHead;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.cb_rm)
    CheckBox cbRm;
    @BindView(R.id.bt_register)
    TextView btRegister;
    @BindView(R.id.ll_extra)
    LinearLayout llExtra;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.tv_copyright)
    TextView tvCopyright;

    private ILogin.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenter(this);
        if (PublicConfig.isDebug) {
            etUsername.setText("_aqinn");
            etPassword.setText("biubiubiu");
        }
        String rememberAccount = SPUtil.getRememberAccount(this);
        String rememberPwd = SPUtil.getRememberPwd(this);
        if (rememberAccount != null) {
            etUsername.setText(rememberAccount);
            etPassword.setText(rememberPwd);
            cbRm.setChecked(true);
        }
        mPresenter.login(etUsername.getText().toString(), etPassword.getText().toString(), cbRm.isChecked());
    }

    @OnClick({R.id.iv_head, R.id.bt_register, R.id.bt_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                // TODO 可以作为彩蛋？
                break;
            case R.id.bt_register:
                if (PublicConfig.isDebug)
                    Toast.makeText(this, "Test: 跳转至注册页面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_confirm:
                String account = etUsername.getText().toString();
                String pwd = etPassword.getText().toString();
                mPresenter.login(account, pwd, cbRm.isChecked());
                break;
        }
    }

    @Override
    public void loginSuccess() {
        if (PublicConfig.isDebug)
            Toast.makeText(this, "Test: 登录成功，跳转至首页", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, IndexActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginError(ILogin.ErrorCode errCode) {
        switch (errCode) {
            case WRONG_PWD:
                Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                break;
            case NOT_FOUND_USER:
                Toast.makeText(this, "账户不存在", Toast.LENGTH_SHORT).show();
                break;
            case WRONG_FORMAT:
                Toast.makeText(this, "账户或密码格式不正确", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "loginError: 错误的账号or密码 => " + etUsername.getText().toString() + ", " + etPassword.getText().toString());
                break;
            default:
                break;
        }
    }

}
