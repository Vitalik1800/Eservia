package com.eservia.booking.ui.suggest_business.thank_you;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.utils.KeyboardUtil;

import moxy.presenter.InjectPresenter;

public class SuggestBusinessThankYouFragment extends BaseHomeFragment implements SuggestBusinessThankYouView {

    public static final String TAG = "SuggestBusinessThankYouFragment";
    CoordinatorLayout fragmentContainer;
    Button btnAccept;
    @InjectPresenter
    SuggestBusinessThankYouPresenter mPresenter;

    private BaseActivity mActivity;

    public static SuggestBusinessThankYouFragment newInstance() {
        return new SuggestBusinessThankYouFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggest_business_thank_you, container, false);
        mActivity = (BaseActivity) getActivity();
        WindowUtils.setWhiteStatusBar(mActivity);
        fragmentContainer = view.findViewById(R.id.fragment_container);
        btnAccept = view.findViewById(R.id.btnAccept);
        setUnbinder(ButterKnife.bind(this, view));
        initViews();
        return view;
    }

    @Override
    public void refresh() {
    }

    @Override
    public void willBeDisplayed() {
        WindowUtils.setWhiteStatusBar(mActivity);
    }

    @Override
    public void willBeHidden() {
    }

    public void onAcceptClick() {
        mPresenter.onAcceptClick();
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void closeView() {
        KeyboardUtil.hideSoftKeyboard(mActivity);
        mActivity.onBackPressed();
    }

    private void initViews() {
    }
}
