package com.eservia.booking.ui.booking.resto.thank_you;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

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

public class ThankYouRestoFragment extends BaseHomeFragment implements ThankYouRestoView {

    public static final String TAG = "thank_you_fragment_booking_resto";

    CoordinatorLayout fragmentContainer;
    Button btnCommonBookingNextButton;

    @InjectPresenter
    ThankYouRestoPresenter mPresenter;

    private BaseActivity mActivity;

    public static ThankYouRestoFragment newInstance() {
        return new ThankYouRestoFragment();
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
        btnCommonBookingNextButton.setBackground(ContextCompat.getDrawable(mActivity,
                R.drawable.background_common_button_red_light));
    }
}
