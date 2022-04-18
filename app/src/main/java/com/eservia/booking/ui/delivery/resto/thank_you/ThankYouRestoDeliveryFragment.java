package com.eservia.booking.ui.delivery.resto.thank_you;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

public class ThankYouRestoDeliveryFragment extends BaseHomeFragment implements ThankYouRestoDeliveryView {

    public static final String TAG = "thank_you_fragment_resto_delivery";

    CoordinatorLayout fragmentContainer;
    TextView tvThankYouDescription;
    Button btnCommonBookingNextButton;

    @InjectPresenter
    ThankYouRestoDeliveryPresenter mPresenter;

    private BaseActivity mActivity;

    public static ThankYouRestoDeliveryFragment newInstance() {
        return new ThankYouRestoDeliveryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_beauty_thank_you, container, false);
        mActivity = (BaseActivity) getActivity();
        WindowUtils.setWhiteStatusBar(mActivity);
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        tvThankYouDescription = view.findViewById(R.id.tvThankYouDescription);
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
    public void closeView() {
        KeyboardUtil.hideSoftKeyboard(mActivity);
        mActivity.onBackPressed();
    }

    private void initViews() {
        tvThankYouDescription.setText(mActivity.getResources().getString(R.string.order_created));
        btnCommonBookingNextButton.setText(mActivity.getResources().getString(R.string.complete));
        btnCommonBookingNextButton.setBackground(ContextCompat.getDrawable(mActivity,
                R.drawable.background_common_button_red_light));
    }
}
