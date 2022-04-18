package com.eservia.booking.ui.auth.approve_phone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.ClearFocusLayoutListener;
import com.eservia.booking.ui.home.HomeActivity;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.MessageUtil;
import com.eservia.booking.util.ValidatorUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.utils.KeyboardUtil;

import moxy.presenter.InjectPresenter;

public class PhoneApproveActivity extends BaseActivity implements PhoneApproveView {

    private static final String PHONE_EXTRA = "phone";
    private static final String SOCIAL_TOKEN_EXTRA = "socialToken";
    private static final String SOCIAL_TYPE_EXTRA = "socialType";

    @InjectPresenter
    PhoneApprovePresenter mPhoneApprovePresenter;
    EditText mCode;
    TextView mMessage;
    TextView mTimer;
    Button mSend;
    TextView mSendAgain;
    ProgressBar pbProgress;

    private String mPhone;
    private String mProvider;
    private String mSocialToken;

    public static Intent start(Context context, String phone, String provider, String socialToken) {
        Intent intent = new Intent(context, PhoneApproveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(PHONE_EXTRA, phone);
        intent.putExtra(SOCIAL_TYPE_EXTRA, provider);
        intent.putExtra(SOCIAL_TOKEN_EXTRA, socialToken);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_approve_phone);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        mCode = findViewById(R.id.etApproveCode);
        mMessage = findViewById(R.id.tvMessage);
        mTimer = findViewById(R.id.tvTimer);
        mSend = findViewById(R.id.btnSend);
        mSendAgain = findViewById(R.id.tvSendAgain);
        pbProgress = findViewById(R.id.pbProgress);
        mSendAgain.setOnClickListener(v -> onSendAgainClick());
        mSend.setOnClickListener(v -> onSendClick());
        prepareExtras();
        initViews();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void onSendAgainClick() {
        mPhoneApprovePresenter.requestSms(mPhone);
    }

    public void onSendClick() {
        trySend();
    }

    @Override
    public void initExtra() {
        mPhoneApprovePresenter.onExtra(mPhone, mProvider, mSocialToken);
    }

    private void prepareExtras() {
        mPhone = getIntent().getStringExtra(PHONE_EXTRA);
        mProvider = getIntent().getStringExtra(SOCIAL_TYPE_EXTRA);
        mSocialToken = getIntent().getStringExtra(SOCIAL_TOKEN_EXTRA);
    }

    private void trySend() {
        mCode.setError(ValidatorUtil.isConfirmCodeValid(this, mCode.getText().toString()));
        if (!hasErrors()) {
            KeyboardUtil.hideSoftKeyboard(this);
            mPhoneApprovePresenter.approveSmsCode(mCode.getText().toString());
        }
    }

    private boolean hasErrors() {
        boolean error = false;
        if (mCode.getError() != null) {
            mCode.requestFocus();
            error = true;
        }
        return error;
    }

    @Override
    public void onSmsSendSuccess() {
        MessageUtil.showToast(this, R.string.sms_was_sent);
    }

    @Override
    public void onSmsSendFailed(Throwable throwable) {
        MessageUtil.showSnackbar(mCode, throwable);
    }

    @Override
    public void approveStarted() {
        mSend.setEnabled(false);
    }

    @Override
    public void approveFinished() {
        mSend.setEnabled(true);
    }

    @Override
    public void onApproveFail(Throwable throwable) {
        MessageUtil.showSnackbar(mCode, throwable);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateTime(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;
        String minutesValue = minutes < 10 ? "0" + minutes : "" + minutes;
        String secondsValue = seconds < 10 ? "0" + seconds : "" + seconds;
        mTimer.setText(minutesValue + ":" + secondsValue);
    }

    @Override
    public void enableTimer(boolean enabled) {
        mTimer.setVisibility(enabled ? View.VISIBLE : View.GONE);
        mSendAgain.setVisibility(enabled ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setPhoneNumber() {
        String text = getString(R.string.activity_approve_message, mPhone);
        mMessage.setText(text);
    }

    @Override
    public void requestSent(boolean sent) {
        if (sent) {
            WindowUtils.setTouchable(this, false);
            pbProgress.setVisibility(View.VISIBLE);
        } else {
            WindowUtils.setTouchable(this, true);
            pbProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void openHomeActivity() {
        HomeActivity.start(this);
    }

    private void initViews() {
        ColorUtil.setProgressColor(pbProgress, R.color.green_bright);

        setUpOnEditorActionListeners();

        setViewTreeObservers();

        setLengthFilters();
    }

    private void setUpOnEditorActionListeners() {
        mCode.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onSendClick();
                return true;
            }
            return false;
        });
    }

    private void setViewTreeObservers() {
        mCode.getViewTreeObserver().addOnGlobalLayoutListener(new ClearFocusLayoutListener(
                mCode, new View[]{mCode}));
    }

    private void setLengthFilters() {
        mCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                ValidatorUtil.CONFIRM_CODE_LENGTH)});
    }
}
