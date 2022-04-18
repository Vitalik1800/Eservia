package com.eservia.booking.ui.auth.reset_password;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.ClearFocusLayoutListener;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.MessageUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.common.view.SimpleTextWatcher;
import com.eservia.utils.KeyboardUtil;
import com.eservia.utils.PhoneUtil;

import moxy.presenter.InjectPresenter;

public class RequestRestoreCodeActivity extends BaseActivity implements RequestRestoreCodeView {

    @InjectPresenter
    RequestRestoreCodePresenter mRequestCodePresenter;
    EditText mPhone;
    Button btnSend;
    ProgressBar pbProgress;
    ImageView ivBack;

    public static void start(Context context) {
        Intent starter = new Intent(context, RequestRestoreCodeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_restore_code);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        mPhone = findViewById(R.id.etPhone);
        btnSend = findViewById(R.id.btnSend);
        pbProgress = findViewById(R.id.pbProgress);
        btnSend.setOnClickListener(v -> onSendClicked());
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> onCloseClicked());
        initViews();
    }
    public void onSendClicked() {
        mRequestCodePresenter.onSendSms();
    }

    public void onCloseClicked() {
        KeyboardUtil.hideSoftKeyboard(this);
        finish();
    }

    @Override
    public void showProgress() {
        btnSend.setEnabled(false);
        WindowUtils.setTouchable(this, false);
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        btnSend.setEnabled(true);
        WindowUtils.setTouchable(this, true);
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void onSendSuccess() {
        MessageUtil.showToast(this, R.string.sms_was_sent);
    }

    @Override
    public void onSendFailed(Throwable throwable) {
        MessageUtil.showSnackbar(mPhone, throwable);
    }

    @Override
    public void onPhoneError(String error) {
        mPhone.setError(error);
        mPhone.requestFocus();
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtil.hideSoftKeyboard(this);
    }

    @Override
    public void openResetPass() {
        openResetPassActivity();
    }

    private void initViews() {

        ColorUtil.setProgressColor(pbProgress, R.color.green_bright);

        mPhone.setOnFocusChangeListener((view, b) -> {
            if (b) {
                PhoneUtil.addPrefix(mPhone);
                mPhone.requestFocus();
            }
        });

        mPhone.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void textChanged(String s) {
                mRequestCodePresenter.onPhoneChanged(s);
            }
        });

        setUpOnEditorActionListeners();

        setViewTreeObservers();
    }

    private void setUpOnEditorActionListeners() {
        mPhone.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onSendClicked();
                return true;
            }
            return false;
        });
    }

    private void setViewTreeObservers() {
        mPhone.getViewTreeObserver().addOnGlobalLayoutListener(new ClearFocusLayoutListener(
                mPhone, new View[]{mPhone}));
    }

    private void openResetPassActivity() {
        finish();
        ResetPasswordActivity.start(this);
    }
}
