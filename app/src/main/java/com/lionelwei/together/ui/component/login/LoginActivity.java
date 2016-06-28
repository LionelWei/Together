package com.lionelwei.together.ui.component.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lionelwei.together.R;
import com.lionelwei.together.common.util.ToastUtil;
import com.lionelwei.together.config.preference.Preferences;
import com.lionelwei.together.ui.activity.MainActivity;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.widget.ClearableEditTextWithIcon;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * MVP: Activity只做UI和交互相关操作, 其他操作(如preference, 数据及网络相关)全部放到Presenter中
 */
public class LoginActivity extends Activity implements ILoginVIew{
    private static final String KICK_OUT = "KICK_OUT";

    // presenter
    LoginPresenter mPresenter;

    // views
    @BindView(R.id.register_login_tip)
    TextView switchModeBtn;  // 注册/登录切换按钮

    @BindView(R.id.edit_login_account)
    ClearableEditTextWithIcon loginAccountEdit;

    @BindView(R.id.edit_login_password)
    ClearableEditTextWithIcon loginPasswordEdit;

    @BindView(R.id.edit_register_account)
    ClearableEditTextWithIcon registerAccountEdit;

    @BindView(R.id.edit_register_nickname)
    ClearableEditTextWithIcon registerNickNameEdit;

    @BindView(R.id.edit_register_password)
    ClearableEditTextWithIcon registerPasswordEdit;

    @BindView(R.id.login_layout)
    View loginLayout;

    @BindView(R.id.register_layout)
    View registerLayout;

    @BindView(R.id.btn_register)
    Button registerButton;

    @BindView(R.id.btn_login)
    Button loginButton;

    @BindString(R.string.register)
    String STR_REGISTER;

    @BindString(R.string.login)
    String STR_LOGIN;

    @BindString(R.string.network_is_not_available)
    String STR_NETWORK_UNAVAILABLE;

    @BindString(R.string.login_registering)
    String STR_REGISTERING;

    private boolean registerMode = false; // 注册模式

    public static void start(Context context) {
        start(context, false);
    }

    public static void start(Context context, boolean kickOut) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(KICK_OUT, kickOut);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        onParseIntent();
        initView();

        mPresenter = new LoginPresenter(this);
//        setupRegisterPanel();
    }


    @Override
    public void onNetworkError() {
        ToastUtil.show(STR_NETWORK_UNAVAILABLE);
    }

    @Override
    public void onLoginAccOrPwdError(String errorMsg, int code) {
        ToastUtil.show("Login Error: " + code);
    }

    @Override
    public void onLoginSuccess() {
        // 进入主界面
        MainActivity.start(LoginActivity.this, null);
        finish();
    }

    @Override
    public void showRegLoading() {
        DialogMaker.showProgressDialog(this, STR_REGISTERING, false);
    }

    @Override
    public void showLogLoading() {
        DialogMaker.showProgressDialog(this, STR_LOGIN, false);
    }

    @Override
    public void hideLoading() {
        DialogMaker.dismissProgressDialog();
    }

    @Override
    public void onRegisterSuccess() {
        switchMode();  // 切换回登录

        final String account = registerAccountEdit.getText().toString();
        final String password = registerPasswordEdit.getText().toString();
        loginAccountEdit.setText(account);
        loginPasswordEdit.setText(password);

        registerAccountEdit.setText("");
        registerNickNameEdit.setText("");
        registerPasswordEdit.setText("");
    }

    @Override
    public void onRegisterFailed() {
        ToastUtil.show("Register failed");
    }

    private void onParseIntent() {
/*
        if (getIntent().getBooleanExtra(KICK_OUT, false)) {
            int type = NIMClient.getService(AuthService.class).getKickedClientType();
            String client;
            switch (type) {
                case ClientType.Web:
                    client = "网页端";
                    break;
                case ClientType.Windows:
                    client = "电脑端";
                    break;
                default:
                    client = "移动端";
                    break;
            }
            EasyAlertDialogHelper.showOneButtonDiolag(LoginActivity.this, getString(R.string.kickout_notify),
                    String.format(getString(R.string.kickout_content), client), getString(R.string.ok), true, null);
        }
*/
    }

    /**
     * 登录面板
     */
    private void initView() {
        loginAccountEdit.setIconResource(R.drawable.user_account_icon);
        loginPasswordEdit.setIconResource(R.drawable.user_pwd_lock_icon);


        loginAccountEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32)});
        loginPasswordEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32)});
//        loginPasswordEdit.setOnKeyListener(this);

        String account = Preferences.getUserAccount();
        loginAccountEdit.setText(account);

        registerAccountEdit.setIconResource(R.drawable.user_account_icon);
        registerNickNameEdit.setIconResource(R.drawable.user_nick_name_icon);
        registerPasswordEdit.setIconResource(R.drawable.user_pwd_lock_icon);

        registerAccountEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        registerNickNameEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        registerPasswordEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
    }

    @OnClick(R.id.register_login_tip)
    public void clickSwichButton() {
        switchMode();
    }

    @OnClick(R.id.btn_login)
    public void clickLogin() {
        final String account = loginAccountEdit.getText().toString();
        final String password = loginPasswordEdit.getText().toString();
        mPresenter.login(account, password);
    }

    @OnClick(R.id.btn_register)
    public void clickRegister() {
        if (!registerMode) {
            return;
        }
        if (!checkRegisterContentValid(true)) {
            return;
        }
        // 注册流程
        final String account = registerAccountEdit.getText().toString();
        final String nickName = registerNickNameEdit.getText().toString();
        final String password = registerPasswordEdit.getText().toString();
        Log.d("MY_LOGIN", "account: " + account);
        Log.d("MY_LOGIN", "account: " + nickName);
        Log.d("MY_LOGIN", "password: " + "*****");
        mPresenter.register(account, nickName, password);
    }


    private boolean checkRegisterContentValid(boolean tipError) {
        if (!registerMode) {
            return false;
        }

        // 帐号检查
        if (registerAccountEdit.length() <= 0 || registerAccountEdit.length() > 20) {
            if (tipError) {
                ToastUtil.show(R.string.register_account_in_20chars);
            }

            return false;
        }

        // 昵称检查
        if (registerNickNameEdit.length() <= 0 || registerNickNameEdit.length() > 10) {
            if (tipError) {
                ToastUtil.show(R.string.register_nick_in_10chars);
            }

            return false;
        }

        // 密码检查
        if (registerPasswordEdit.length() < 6 || registerPasswordEdit.length() > 20) {
            if (tipError) {
                ToastUtil.show(R.string.register_password_from_6_to_20chars);
            }

            return false;
        }

        return true;
    }

    private void switchMode() {
        registerMode = !registerMode;

        setTitle(registerMode ? STR_REGISTER : STR_LOGIN);
        loginLayout.setVisibility(registerMode ? View.GONE : View.VISIBLE);
        registerLayout.setVisibility(registerMode ? View.VISIBLE : View.GONE);
        switchModeBtn.setText(registerMode
                ? R.string.login_has_account
                : R.string.register_no_account);

        if (registerMode) {
            registerButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);
        } else {
            registerButton.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
        }
    }

}
