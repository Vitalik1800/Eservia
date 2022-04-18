package com.eservia.booking.ui.auth.register_phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.ClearFocusLayoutListener;
import com.eservia.booking.ui.auth.approve_phone.PhoneApproveActivity;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.MessageUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.common.view.SimpleTextWatcher;
import com.eservia.utils.KeyboardUtil;
import com.eservia.utils.PhoneUtil;

import moxy.presenter.InjectPresenter;

public class RegisterPhoneActivity extends BaseActivity implements RegisterPhoneView {

    private static final String SOCIAL_TOKEN_EXTRA = "socialToken";
    private static final String SOCIAL_TYPE_EXTRA = "socialType";

    @InjectPresenter
    RegisterPhonePresenter mRegisterPhonePresenter;
    EditText mPhone;
    Button btnSend;
    ProgressBar pbProgress;

    private String mProvider;
    private String mSocialToken;

    public static Intent open(Context context, String provider, String socialToken) {
        Intent intent = new Intent(context, RegisterPhoneActivity.class);
        intent.putExtra(SOCIAL_TYPE_EXTRA, provider);
        intent.putExtra(SOCIAL_TOKEN_EXTRA, socialToken);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        mPhone = findViewById(R.id.etPhone);
        btnSend = findViewById(R.id.btnSend);
        pbProgress = findViewById(R.id.pbProgress);
        btnSend.setOnClickListener(v ->
            onRegisterClicked()
        );
        prepareExtras();
        initViews();
    }

    private void prepareExtras() {
        mProvider = getIntent().getStringExtra(SOCIAL_TYPE_EXTRA);
        mSocialToken = getIntent().getStringExtra(SOCIAL_TOKEN_EXTRA);
    }
    public void onRegisterClicked() {
        mRegisterPhonePresenter.onRegister();
    }

    //@OnClick(R.id.ivBack)
    public void onCloseClicked() {
        KeyboardUtil.hideSoftKeyboard(this);
        openLoginActivity();
    }

    @Override
    public void onBackPressed() {
        onCloseClicked();
        super.onBackPressed();
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
    public void onSuccessRegisterProvider(String phone) {
        MessageUtil.showToast(this, R.string.sms_was_sent);
        openApprovePhone(phone);
    }

    @Override
    public void onErrorRegisterProvider(Throwable throwable) {
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
    public void initExtra() {
        mRegisterPhonePresenter.onExtra(mProvider, mSocialToken);
    }

    private void initViews() {

        ColorUtil.setProgressColor(pbProgress, R.color.green_bright);

        mPhone.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void textChanged(String s) {
                mRegisterPhonePresenter.onPhoneChanged(s);
            }
        });

        mPhone.setOnFocusChangeListener((view, b) -> {
            if (b) {
                PhoneUtil.addPrefix(mPhone);
                mPhone.requestFocus();
            }
        });

        setUpOnEditorActionListeners();

        setViewTreeObservers();
    }

    private void setUpOnEditorActionListeners() {
        mPhone.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onRegisterClicked();
                return true;
            }
            return false;
        });
    }

    private void setViewTreeObservers() {
        mPhone.getViewTreeObserver().addOnGlobalLayoutListener(new ClearFocusLayoutListener(
                mPhone, new View[]{mPhone}));
    }

    private void openApprovePhone(String phone) {
        startActivity(PhoneApproveActivity.start(this, phone, mProvider, mSocialToken));
    }
}
