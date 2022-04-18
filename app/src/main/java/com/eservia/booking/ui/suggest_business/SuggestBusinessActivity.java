package com.eservia.booking.ui.suggest_business;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;

import moxy.presenter.InjectPresenter;

public class SuggestBusinessActivity extends BaseActivity implements SuggestBusinessView,
        FragmentManager.OnBackStackChangedListener {

    private static final String KEY_MODAL = "modal";

    FrameLayout suggestBusinessContainer;

    @InjectPresenter
    SuggestBusinessPresenter mPresenter;

    public static void start(Context context, boolean modal) {
        Intent starter = new Intent(context, SuggestBusinessActivity.class);
        starter.putExtra(KEY_MODAL, modal);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_business);
        suggestBusinessContainer = findViewById(R.id.suggestBusinessContainer);
        WindowUtils.setLightStatusBar(this);
        setUnbinder(ButterKnife.bind(this));
        getSupportFragmentManager().addOnBackStackChangedListener(SuggestBusinessActivity.this);
    }

    @Override
    public void onBackStackChanged() {
    }

    @Override
    public void onBackPressed() {
        if (!popFragment()) {
            finish();
        }
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void openSuggestBusinessFragmentStandard() {
        FragmentUtil.showSuggestBusinessStandartFragment(getSupportFragmentManager(),
                R.id.suggestBusinessContainer);
    }

    @Override
    public void openSuggestBusinessFragmentModal() {
        FragmentUtil.showSuggestBusinessModalFragment(getSupportFragmentManager(),
                R.id.suggestBusinessContainer);
    }

    @Override
    public void requiredExtra() {
        mPresenter.onExtra(getIntent().getBooleanExtra(KEY_MODAL, true));
    }

    private boolean popFragment() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
            return true;
        }
        return false;
    }
}
