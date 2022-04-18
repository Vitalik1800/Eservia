package com.eservia.booking.ui.profile.password;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.ClearFocusLayoutListener;
import com.eservia.booking.util.MessageUtil;
import com.eservia.booking.util.ValidatorUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.utils.KeyboardUtil;
import com.google.android.material.appbar.AppBarLayout;

import moxy.presenter.InjectPresenter;

public class UpdatePasswordActivity extends BaseActivity implements UpdatePasswordView {

    CoordinatorLayout coordinator;
    AppBarLayout app_bar_layout;
    Toolbar toolbar;
    ProgressBar pbProgress;
    EditText etCurrentPassword;
    EditText etNewPassword;
    EditText etNewPasswordAgain;
    RelativeLayout rlCardHolderPassword;
    CardView cvContainerPassword;

    @InjectPresenter
    UpdatePasswordPresenter mPresenter;

    private MenuItem mItemSave;

    public static void start(Context context) {
        Intent starter = new Intent(context, UpdatePasswordActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        coordinator = findViewById(R.id.coordinator);
        app_bar_layout = findViewById(R.id.app_bar_layout);
        toolbar = findViewById(R.id.toolbar);
        pbProgress = findViewById(R.id.pbProgress);
        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etNewPasswordAgain = findViewById(R.id.etNewPasswordAgain);
        rlCardHolderPassword = findViewById(R.id.rlCardHolderPassword);
        cvContainerPassword = findViewById(R.id.cvContainerPassword);
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_change_password, menu);
        this.mItemSave = menu.findItem(R.id.item_save);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            closeActivity();
        } else if (id == R.id.item_save) {
            tryUpdatePassword();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        disableMenus();
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        enableMenus();
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void onPasswordUpdated() {
        MessageUtil.showToast(this, R.string.password_updated);
    }

    @Override
    public void onPasswordUpdateFailed(Throwable throwable) {
        String errorMess = MessageUtil.getResErrorOrServerErrorMessage(this, throwable);
        MessageUtil.showSnackbar(coordinator, errorMess);
    }

    @Override
    public void closeActivity() {
        KeyboardUtil.hideSoftKeyboard(this);
        finish();
    }

    private void tryUpdatePassword() {
        String currentPass = etCurrentPassword.getText().toString();
        String newPass = etNewPassword.getText().toString();
        if (!hasErrors()) {
            KeyboardUtil.hideSoftKeyboard(this);
            mPresenter.onChangePassword(currentPass, newPass);
        }
    }

    private boolean hasErrors() {
        etCurrentPassword.setError(ValidatorUtil.isPasswordValid(this,
                etCurrentPassword.getText().toString()));
        etNewPassword.setError(ValidatorUtil.isPasswordValid(this,
                etNewPassword.getText().toString()));
        etNewPasswordAgain.setError(ValidatorUtil.isConfirmPasswordValid(this,
                etNewPassword.getText().toString(), etNewPasswordAgain.getText().toString()));

        boolean error = false;
        if (etNewPasswordAgain.getError() != null) {
            etNewPasswordAgain.requestFocus();
            error = true;
        }
        if (etNewPassword.getError() != null) {
            etNewPassword.requestFocus();
            error = true;
        }
        if (etCurrentPassword.getError() != null) {
            etCurrentPassword.requestFocus();
            error = true;
        }
        return error;
    }

    private void disableMenus() {
        if (mItemSave != null) {
            mItemSave.setEnabled(false);
        }
    }

    private void enableMenus() {
        if (mItemSave != null) {
            mItemSave.setEnabled(true);
        }
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        setOutlineProviders();
        setUpOnEditorActionListeners();
        setViewTreeObservers();
    }

    private void setOutlineProviders() {
        ViewUtil.setCardOutlineProvider(this, rlCardHolderPassword, cvContainerPassword);
    }

    private void setUpOnEditorActionListeners() {
        etCurrentPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etNewPassword.requestFocus();
                ViewUtil.moveCursorToEnd(etNewPassword);
                return true;
            }
            return false;
        });

        etNewPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etNewPasswordAgain.requestFocus();
                ViewUtil.moveCursorToEnd(etNewPasswordAgain);
                return true;
            }
            return false;
        });

        etNewPasswordAgain.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                tryUpdatePassword();
                return true;
            }
            return false;
        });
    }

    private void setViewTreeObservers() {
        etNewPasswordAgain.getViewTreeObserver().addOnGlobalLayoutListener(
                new ClearFocusLayoutListener(etNewPasswordAgain,
                        new View[]{etCurrentPassword, etNewPassword, etNewPasswordAgain}));
    }
}
