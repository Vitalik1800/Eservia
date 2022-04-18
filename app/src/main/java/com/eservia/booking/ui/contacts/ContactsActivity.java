package com.eservia.booking.ui.contacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
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

public class ContactsActivity extends BaseActivity implements ContactsView {

    CoordinatorLayout coordinator;
    AppBarLayout app_bar_layout;
    Toolbar toolbar;
    ProgressBar pbProgress;
    EditText etTheme;
    EditText etMessage;
    RelativeLayout rlCardHolderContact;
    CardView cvContainerContact;

    @InjectPresenter
    ContactsPresenter mPresenter;

    private MenuItem mItemSend;

    public static void start(Context context) {
        Intent starter = new Intent(context, ContactsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        coordinator = findViewById(R.id.coordinator);
        app_bar_layout = findViewById(R.id.app_bar_layout);
        toolbar = findViewById(R.id.toolbar);
        pbProgress = findViewById(R.id.pbProgress);
        etTheme = findViewById(R.id.etTheme);
        etMessage = findViewById(R.id.etMessage);
        rlCardHolderContact = findViewById(R.id.rlCardHolderContact);
        cvContainerContact = findViewById(R.id.cvContainerContact);
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        this.mItemSend = menu.findItem(R.id.item_send);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            closeActivity();
        } else if (id == R.id.item_send) {
            trySendFeedback();
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
    public void onFeedbackSendSuccess() {
        MessageUtil.showToast(this, R.string.success_send_eservia_feedback);
    }

    @Override
    public void onFeedbackSendFailed(Throwable throwable) {
        MessageUtil.showSnackbar(coordinator, throwable);
    }

    @Override
    public void closeActivity() {
        KeyboardUtil.hideSoftKeyboard(this);
        finish();
    }

    private void trySendFeedback() {
        String theme = etTheme.getText().toString();
        String message = etMessage.getText().toString();
        if (!hasErrors()) {
            mPresenter.onSendFeedback(theme.trim(), message.trim());
        }
    }

    private boolean hasErrors() {
        etTheme.setError(ValidatorUtil.isThemeValid(this,
                etTheme.getText().toString().trim()));
        etMessage.setError(ValidatorUtil.isLongMessageValid(this,
                etMessage.getText().toString().trim()));

        boolean error = false;
        if (etMessage.getError() != null) {
            etMessage.requestFocus();
            error = true;
        }
        if (etTheme.getError() != null) {
            etTheme.requestFocus();
            error = true;
        }
        return error;
    }

    private void disableMenus() {
        if (mItemSend != null) {
            mItemSend.setEnabled(false);
        }
    }

    private void enableMenus() {
        if (mItemSend != null) {
            mItemSend.setEnabled(true);
        }
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        setOutlineProviders();

        setUpForms();
    }

    private void setUpForms() {
        setUpOnEditorActionListeners();
        setViewTreeObservers();

        etTheme.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                ValidatorUtil.MAX_SHORT_MESS_LENGTH)});

        etMessage.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                ValidatorUtil.MAX_ESERVIA_FEEDBACK_LENGTH)});

        ViewUtil.setOnTouchListenerForVerticalScroll(etMessage);
    }

    private void setOutlineProviders() {
        ViewUtil.setCardOutlineProvider(this, rlCardHolderContact, cvContainerContact);
    }

    private void setUpOnEditorActionListeners() {
        etTheme.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etMessage.requestFocus();
                ViewUtil.moveCursorToEnd(etMessage);
                return true;
            }
            return false;
        });

        etMessage.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                KeyboardUtil.hideSoftKeyboard(this);
                trySendFeedback();
                return true;
            }
            return false;
        });
    }

    private void setViewTreeObservers() {
        etTheme.getViewTreeObserver().addOnGlobalLayoutListener(new ClearFocusLayoutListener(
                etTheme, new View[]{etTheme, etMessage}));
    }
}
