package com.eservia.booking.ui.booking.beauty.thank_you;

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
import com.eservia.booking.util.RateUsUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.model.local.ContentChangesObservable;
import com.eservia.model.local.SyncEvent;
import com.eservia.utils.KeyboardUtil;

import moxy.presenter.InjectPresenter;

public class ThankYouFragment extends BaseHomeFragment implements ThankYouView {

    public static final String TAG = "thank_you_fragment_booking_beauty";

    CoordinatorLayout fragmentContainer;
    Button btnCommonBookingNextButton;

    @InjectPresenter
    ThankYouPresenter mPresenter;

    private BaseActivity mActivity;

    public static ThankYouFragment newInstance() {
        return new ThankYouFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_beauty_thank_you, container, false);
        mActivity = (BaseActivity) getActivity();
        WindowUtils.setWhiteStatusBar(mActivity);
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        btnCommonBookingNextButton = view.findViewById(R.id.btnCommonBookingNextButton);
        btnCommonBookingNextButton.setOnClickListener(v -> onAcceptClick());
        initViews();
        return view;
    }

    @Override
    public void onDestroy() {
        RateUsUtil.reservationAdded();
        if (RateUsUtil.shouldShowRateUsDialog()) {
            ContentChangesObservable.send(SyncEvent.RATE_US);
        }
        super.onDestroy();
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
        btnCommonBookingNextButton.setText(mActivity.getResources().getString(R.string.complete));
    }
}
