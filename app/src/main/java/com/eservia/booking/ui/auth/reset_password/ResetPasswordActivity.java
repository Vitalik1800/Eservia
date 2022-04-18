package com.eservia.booking.ui.auth.reset_password;

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

import androidx.annotation.Nullable;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.ClearFocusLayoutListener;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.MessageUtil;
import com.eservia.booking.util.ValidatorUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.common.view.SimpleTextWatcher;
import com.eservia.utils.KeyboardUtil;

import moxy.presenter.InjectPresenter;

public class ResetPasswordActivity extends BaseActivity implements ResetPasswordView {

    @InjectPresenter
    ResetPasswordPresenter mResetPasswordPresenter;

    EditText mCode;
    EditText mPassword;
    EditText mPasswordConfirm;
    Button mSend;
    ProgressBar pbProgress;
    CheckBox cbShowPassword;
    CheckBox cbShowPasswordConfirmation;
    ImageView ivBack;

    public static void start(Context context) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        mCode = findViewById(R.id.etCode);
        ivBack = findViewById(R.id.ivBack);
        mPassword = findViewById(R.id.etPassword);
        mPasswordConfirm = findViewById(R.id.etPasswordConfirmation);
        mSend = findViewById(R.id.btnSend);
        pbProgress = findViewById(R.id.pbProgress);
        cbShowPassword = findViewById(R.id.cbShowPassword);
        cbShowPasswordConfirmation = findViewById(R.id.cbShowPasswordConfirmation);
        init();
        ivBack.setOnClickListener(v -> onCloseClicked());
        mSend.setOnClickListener(v -> onSendClicked());
    }

    private void init() {
        initViews();
        mPassword.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void textChanged(String s) {
                mResetPasswordPresenter.onPasswordChanged(s);
            }
        });
        mPasswordConfirm.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void textChanged(String s) {
                mResetPasswordPresenter.onPasswordConfirmChanged(s);
            }
        });
        mCode.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void textChanged(String s) {
                mResetPasswordPresenter.onCodeChanged(s);
            }
        });
    }

    public void onSendClicked() {
        mResetPasswordPresenter.onReset();
    }

    public void onCloseClicked() {
        close();
    }

    @Override
    public void showProgress() {
        mSend.setEnabled(false);
        WindowUtils.setTouchable(this, false);
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mSend.setEnabled(true);
        WindowUtils.setTouchable(this, true);
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void onResetSuccess() {
        MessageUtil.showToast(this, R.string.recover_password_success);
    }

    @Override
    public void onResetFailed(Throwable throwable) {
        MessageUtil.showSnackbar(mCode, throwable);
    }

    @Override
    public void onPasswordError(String error) {
        mPassword.setError(error);
        mPassword.requestFocus();
    }

    @Override
    public void onPasswordConfirmError(String error) {
        mPasswordConfirm.setError(error);
        mPasswordConfirm.requestFocus();
    }

    @Override
    public void onCodeError(String error) {
        mCode.setError(error);
        mCode.requestFocus();
    }

    @Override
    public void close() {
        KeyboardUtil.hideSoftKeyboard(this);
        finish();
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtil.hideSoftKeyboard(this);
    }

    private void initViews() {
        ColorUtil.setProgressColor(pbProgress, R.color.green_bright);

        setUpOnEditorActionListeners();

        setViewTreeObservers();

        setLengthFilters();

        setCheckBoxListeners();
    }

    private void setUpOnEditorActionListeners() {
        mCode.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                mPassword.requestFocus();
                ViewUtil.moveCursorToEnd(mPassword);
                return true;
            }
            return false;
        });

        mPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                mPasswordConfirm.requestFocus();
                ViewUtil.moveCursorToEnd(mPasswordConfirm);
                return true;
            }
            return false;
        });

        mPasswordConfirm.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onSendClicked();
                return true;
            }
            return false;
        });
    }

    private void setViewTreeObservers() {
        mPasswordConfirm.getViewTreeObserver().addOnGlobalLayoutListener(new ClearFocusLayoutListener(
                mPasswordConfirm, new View[]{mCode, mPassword, mPasswordConfirm}));
    }

    private void setLengthFilters() {
        mCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                ValidatorUtil.CONFIRM_CODE_LENGTH)});
        mPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                ValidatorUtil.MAX_PASSWORD_LENGTH)});
        mPasswordConfirm.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                ValidatorUtil.MAX_PASSWORD_LENGTH)});
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
                ViewUtil.hidePassword(mPasswordConfirm);
            } else {
                ViewUtil.showPassword(mPasswordConfirm);
            }
            ViewUtil.moveCursorToEnd(mPasswordConfirm);
        });
    }
}
