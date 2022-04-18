package com.eservia.booking.ui.auth.registration;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.ClearFocusLayoutListener;
import com.eservia.booking.ui.auth.approve_phone.PhoneApproveActivity;
import com.eservia.booking.ui.auth.registration.dialog.RegistrationFailedDialog;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.MessageUtil;
import com.eservia.booking.util.ValidatorUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.common.view.SimpleTextWatcher;
import com.eservia.model.remote.UrlList;
import com.eservia.picker.hbb20.CountryCodePicker;
import com.eservia.utils.KeyboardUtil;
import com.eservia.utils.PhoneUtil;
import com.eservia.utils.SpanUtil;

import java.util.ArrayList;
import java.util.List;

import moxy.presenter.InjectPresenter;

public class RegistrationActivity extends BaseActivity implements RegistrationView {

    @InjectPresenter
    RegistrationPresenter mRegistrationPresenter;
    CountryCodePicker ccp;
    ScrollView mScrollView;
    RelativeLayout mContent;
    EditText mName;
    EditText mLogin;
    EditText mPassword;
    EditText mPasswordConfirmation;
    Button mCreateAccount;
    TextView mAccept;
    ProgressBar pbProgress;
    CheckBox cbShowPassword;
    CheckBox cbShowPasswordConfirmation;
    TextView tvLogin;

    public static Intent openRegistration(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        mScrollView = findViewById(R.id.scrollView);
        mContent = findViewById(R.id.rlRegistrationContent);
        mName = findViewById(R.id.etName);
        mLogin = findViewById(R.id.etPhone);
        mPassword = findViewById(R.id.etPassword);
        mPasswordConfirmation = findViewById(R.id.etPasswordConfirmation);
        mCreateAccount = findViewById(R.id.btnCreateAccount);
        mAccept = findViewById(R.id.tvAccept);
        pbProgress = findViewById(R.id.pbProgress);
        cbShowPassword = findViewById(R.id.cbShowPassword);
        ccp = findViewById(R.id.ccp);
        ccp.setOnCountryChangeListener(() -> {
            ccp.getSelectedCountryCodeWithPlus();
            mLogin.setText(ccp.getSelectedCountryCodeWithPlus());
        });
        cbShowPasswordConfirmation = findViewById(R.id.cbShowPasswordConfirmation);
        init();

        tvLogin = findViewById(R.id.tvLogin);
        mCreateAccount.setOnClickListener(v -> onCreateAccountClicked());
        tvLogin.setOnClickListener(v -> onSignInButtonClicked());
    }

    private void init() {
        initViews();
        mName.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void textChanged(String s) {
                mRegistrationPresenter.nameChanged(s);
            }
        });
        mLogin.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void textChanged(String s) {
                mRegistrationPresenter.loginChanged(s);
            }
        });
        mPassword.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void textChanged(String s) {
                mRegistrationPresenter.passwordChanged(s);
            }
        });
        mPasswordConfirmation.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void textChanged(String s) {
                mRegistrationPresenter.passwordConfirmationChanged(s);
            }
        });
    }
    public void onCreateAccountClicked() {
        mRegistrationPresenter.register();
    }
    public void onSignInButtonClicked() {
        openLoginActivity();
    }

    @Override
    public void showProgress() {
        mCreateAccount.setEnabled(false);
        WindowUtils.setTouchable(this, false);
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mCreateAccount.setEnabled(true);
        WindowUtils.setTouchable(this, true);
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void onRegistrationSuccess(String phone) {
        openApprovePhone(phone);
    }

    @Override
    public void onRegistrationFailed(Throwable throwable) {
        mCreateAccount.setEnabled(true);
        openRegistrationFailedDialog(
                MessageUtil.getResErrorOrServerErrorMessage(this, throwable));
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtil.hideSoftKeyboard(this);
    }

    @Override
    public void nameError(String nameError) {
        mName.requestFocus();
        mName.setError(nameError);
    }

    @Override
    public void loginError(String loginError) {
        mLogin.requestFocus();
        mLogin.setError(loginError);
    }

    @Override
    public void confirmPasswordError(String error) {
        mPasswordConfirmation.requestFocus();
        mPasswordConfirmation.setError(error);
    }

    @Override
    public void passwordError(String passwordError) {
        mPassword.requestFocus();
        mPassword.setError(passwordError);
    }

    @Override
    public void registrationInProgress(boolean b) {
    }

    private void addLinkableText() {
        List<Pair<String, String>> list = new ArrayList<>();
        list.add(Pair.create(getResources().getString(R.string.accept_license), null));
        list.add(Pair.create(" ", null));
        list.add(Pair.create(getResources().getString(R.string.termsOfUseLabel), UrlList.getTermsOfUsing()));
        list.add(Pair.create(" ", null));
        list.add(Pair.create(getResources().getString(R.string.and), null));
        list.add(Pair.create(" ", null));
        list.add(Pair.create(getResources().getString(R.string.policyLabel), UrlList.getPolicyOfConfidence()));

        mAccept.setMovementMethod(LinkMovementMethod.getInstance());
        mAccept.setText(SpanUtil.initLinkableString(list,
                s -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
                    startActivity(Intent.createChooser(intent, ""));
                }), TextView.BufferType.SPANNABLE);
    }

    private void initViews() {

        ColorUtil.setProgressColor(pbProgress, R.color.green_bright);

        mLogin.setOnFocusChangeListener((view, b) -> {
            if (b) {
                PhoneUtil.addPrefix(mLogin);
                mLogin.requestFocus();
            }
        });

        setUpOnEditorActionListeners();

        setViewTreeObservers();

        setLengthFilters();

        setCheckBoxListeners();

        addLinkableText();
    }

    private void setCheckBoxListeners() {
        cbShowPassword.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b) {
                ViewUtil.hidePassword(mPassword);
            } else {
                ViewUtil.showPassword(mPassword);
            }
            ViewUtil.moveCursorToEnd(mPassword);
        });

        cbShowPasswordConfirmation.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b) {
                ViewUtil.hidePassword(mPasswordConfirmation);
            } else {
                ViewUtil.showPassword(mPasswordConfirmation);
            }
            ViewUtil.moveCursorToEnd(mPasswordConfirmation);
        });
    }

    private void setLengthFilters() {
        mName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                ValidatorUtil.MAX_NAME_LENGTH)});
        mPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                ValidatorUtil.MAX_PASSWORD_LENGTH)});
        mPasswordConfirmation.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                ValidatorUtil.MAX_PASSWORD_LENGTH)});
    }

    private void setUpOnEditorActionListeners() {
        mName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                mLogin.requestFocus();
                ViewUtil.moveCursorToEnd(mLogin);
                return true;
            }
            return false;
        });

        mLogin.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                mPassword.requestFocus();
                ViewUtil.moveCursorToEnd(mPassword);
                return true;
            }
            return false;
        });

        mPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                mPasswordConfirmation.requestFocus();
                ViewUtil.moveCursorToEnd(mPasswordConfirmation);
                return true;
            }
            return false;
        });

        mPasswordConfirmation.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onCreateAccountClicked();
                return true;
            }
            return false;
        });
    }

    private void setViewTreeObservers() {
        mPassword.getViewTreeObserver().addOnGlobalLayoutListener(new ClearFocusLayoutListener(
                mPassword, new View[]{mName, mLogin, mPassword, mPasswordConfirmation}));
    }

    private void openRegistrationFailedDialog(String message) {
        RegistrationFailedDialog dialog = RegistrationFailedDialog.newInstance(message);
        dialog.show(getSupportFragmentManager(), RegistrationFailedDialog.class.getSimpleName());
    }

    private void openApprovePhone(String phone) {
        startActivity(PhoneApproveActivity.start(this, phone, null, null));
    }
}
