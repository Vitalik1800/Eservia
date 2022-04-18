package com.eservia.booking.ui.auth.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.ClearFocusLayoutListener;
import com.eservia.booking.ui.auth.approve_phone.PhoneApproveActivity;
import com.eservia.booking.ui.auth.login.dialog.LoginFailedDialog;
import com.eservia.booking.ui.auth.login.facebook.FacebookLoginManager;
import com.eservia.booking.ui.auth.login.google.GoogleLoginManager;
import com.eservia.booking.ui.auth.register_phone.RegisterPhoneActivity;
import com.eservia.booking.ui.auth.registration.RegistrationActivity;
import com.eservia.booking.ui.auth.reset_password.RequestRestoreCodeActivity;
import com.eservia.booking.ui.splash.SplashActivity;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.MessageUtil;
import com.eservia.booking.util.ValidatorUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.entity.Provider;
import com.eservia.picker.hbb20.CountryCodePicker;
import com.eservia.utils.KeyboardUtil;
import com.eservia.utils.PhoneUtil;

import moxy.presenter.InjectPresenter;

public class LoginActivity extends BaseActivity implements
        LoginView,
        LoginFailedDialog.OnButtonClickListener,
        FacebookLoginManager.FacebookLoginListener,
        GoogleLoginManager.GoogleLoginListener {

    @InjectPresenter
    LoginPresenter mLoginPresenter;
    ScrollView scrollView;
    RelativeLayout rlLoginContent;
    EditText etPhone;
    EditText etPassword;
    Button btnLogin;
    ImageView mFacebook;
    ImageView mGoogle;
    ProgressBar pbProgress;
    CheckBox cbShowPassword;
    TextView btnCreateAccount;
    TextView btnForgotPassword;
    CountryCodePicker ccp;

    private FacebookLoginManager mFacebookLoginManager;

    private GoogleLoginManager mGoogleLoginManager;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        ccp = findViewById(R.id.ccp);
        scrollView = findViewById(R.id.scrollView);
        rlLoginContent = findViewById(R.id.rlLoginContent);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        mFacebook = findViewById(R.id.ivFacebook);
        mGoogle = findViewById(R.id.ivGoogle);
        pbProgress = findViewById(R.id.pbProgress);
        cbShowPassword = findViewById(R.id.cbShowPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        btnLogin.setOnClickListener(v -> onLoginButtonClicked());
        btnCreateAccount.setOnClickListener(v -> openRegistrationActivity());
        mFacebook.setOnClickListener(v -> onFacebookLoginClick());
        mGoogle.setOnClickListener(v -> onGoogleLoginClick());
        ccp.setOnCountryChangeListener(() -> {
            ccp.getSelectedCountryCodeWithPlus();
            etPhone.setText(ccp.getSelectedCountryCodeWithPlus());
        });
        btnForgotPassword.setOnClickListener(v -> openRequestRestoreCodeActivity());
        initViews();
        mFacebookLoginManager = new FacebookLoginManager();
        mGoogleLoginManager = new GoogleLoginManager(this, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleLoginManager.dispose();
    }

    public void onLoginButtonClicked() {
        attemptLogin();
    }
    public void onCreateAccountButtonClicked() {
        openRegistrationActivity();
    }
    public void onFacebookLoginClick() {
        mFacebookLoginManager.signIn(this, this);
    }
    public void onGoogleLoginClick() {
        mGoogleLoginManager.startGoogleLogin();
    }
    public void onForgotPassButtonClick() {
        openRequestRestoreCodeActivity();
    }

    @Override
    public void showProgress() {
        WindowUtils.setTouchable(this, false);
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        WindowUtils.setTouchable(this, true);
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void onLoginSuccess() {
        openSplashScreen();
    }

    @Override
    public void onLoginFailed(Throwable throwable) {
        openLoginFailedDialog(MessageUtil.getResErrorOrServerErrorMessage(this, throwable));
    }

    @Override
    public void onLoginWithProviderSuccess() {
        mFacebook.setEnabled(true);
        mGoogle.setEnabled(true);
        openSplashScreen();
    }

    @Override
    public void onLoginWithProviderFailed(Throwable throwable) {
        mFacebook.setEnabled(true);
        mGoogle.setEnabled(true);
        openLoginFailedDialog(MessageUtil.getResErrorOrServerErrorMessage(this, throwable));
    }

    @Override
    public void onForgotPassClick() {
        openRequestRestoreCodeActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGoogleLoginManager.onActivityResult(requestCode, resultCode, data);
        mFacebookLoginManager.setResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccessFacebookLogin(String token) {
        mFacebook.setEnabled(false);
        mLoginPresenter.signInUserWithProvider(Provider.FACEBOOK, token);
    }

    @Override
    public void onErrorFacebookLogin(String message) {
        MessageUtil.showSnackbar(rlLoginContent, R.string.authorization_failed);
    }

    @Override
    public void onGoogleLoginSucceeded(@NonNull String token) {
        mGoogle.setEnabled(false);
        mLoginPresenter.signInUserWithProvider(Provider.GOOGLE, token);
    }

    @Override
    public void onGoogleLoginFailed(String message) {
        MessageUtil.showSnackbar(rlLoginContent, R.string.authorization_failed);
    }

    @Override
    public void openPhoneApprove(String phone) {
        openApprovePhone(phone);
    }

    @Override
    public void providerUserNotFound(String provider, String token) {
        openRegisterPhone(provider, token);
    }

    private void attemptLogin() {
        etPassword.setError(ValidatorUtil.isPasswordValid(this, etPassword.getText().toString()));
        etPhone.setError(ValidatorUtil.isPhoneValid(this, etPhone.getText().toString()));
        if (!hasErrors()) {
            KeyboardUtil.hideSoftKeyboard(this);
            String phone = etPhone.getText().toString();
            String password = etPassword.getText().toString();
            mLoginPresenter.loginUser(phone, password);
        }
    }

    private boolean hasErrors() {
        boolean error = false;
        if (etPassword.getError() != null) {
            etPassword.requestFocus();
            error = true;
        }
        if (etPhone.getError() != null) {
            etPhone.requestFocus();
            error = true;
        }
        return error;
    }

    private void initViews() {

        ColorUtil.setProgressColor(pbProgress, R.color.green_bright);

        etPhone.setOnFocusChangeListener((view, b) -> {
            if (b) {
                PhoneUtil.addPrefix(etPhone);
                etPhone.requestFocus();
            }
        });

        setUpOnEditorActionListeners();

        setViewTreeObservers();

        etPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                ValidatorUtil.MAX_PASSWORD_LENGTH)});

        cbShowPassword.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b) {
                ViewUtil.hidePassword(etPassword);
            } else {
                ViewUtil.showPassword(etPassword);
            }
            ViewUtil.moveCursorToEnd(etPassword);
        });
    }

    private void setUpOnEditorActionListeners() {
        etPhone.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etPassword.requestFocus();
                ViewUtil.moveCursorToEnd(etPassword);
                return true;
            }
            return false;
        });

        etPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                attemptLogin();
                return true;
            }
            return false;
        });
    }

    private void setViewTreeObservers() {
        etPassword.getViewTreeObserver().addOnGlobalLayoutListener(new ClearFocusLayoutListener(
                etPassword, new View[]{etPhone, etPassword}));
    }

    private void openLoginFailedDialog(String message) {
        LoginFailedDialog dialog = LoginFailedDialog.newInstance(message);
        dialog.setListener(this);
        dialog.show(getSupportFragmentManager(), LoginFailedDialog.class.getSimpleName());
    }

    private void openRegistrationActivity() {
        startActivity(RegistrationActivity.openRegistration(this));
        finish();
    }

    private void openApprovePhone(String phone) {
        startActivity(PhoneApproveActivity.start(this, phone, null, null));
        finish();
    }

    private void openRegisterPhone(String provider, String token) {
        startActivity(RegisterPhoneActivity.open(this, provider, token));
        finish();
    }

    private void openRequestRestoreCodeActivity() {
        RequestRestoreCodeActivity.start(this);
    }

    public void openSplashScreen() {
        SplashActivity.start(this);
    }
}
