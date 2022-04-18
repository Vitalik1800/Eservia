package com.eservia.booking.ui.delivery.resto.date;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;

import com.eservia.booking.R;
import com.eservia.booking.common.view.BaseActivity;
import com.eservia.booking.common.view.CommonPlaceHolder;
import com.eservia.booking.ui.booking.dialog.BookingErrorDialog;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.util.BookingUtil;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.common.view.CommonAlert;
import com.eservia.common.view.CommonSegmentedBar;
import com.eservia.simplecalendar.Appearance;
import com.eservia.simplecalendar.SimpleCalendar;
import com.eservia.utils.KeyboardUtil;
import com.eservia.utils.StringUtil;

import org.joda.time.DateTime;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class DeliveryDateFragment extends BaseHomeFragment implements DeliveryDateView {

    public static final String TAG = "date_time_fragment_delivery_resto";

    CoordinatorLayout fragmentContainer;
    Toolbar toolbar;
    ProgressBar pbProgress;
    CommonPlaceHolder phlPlaceholder;
    SimpleCalendar scCalendar;
    ImageView ivTimeSelectIcon;
    TextView tvSelectedTime;
    TextView tvToolbarSubTitle;
    RelativeLayout rlCardHolderDeliveryTime;
    CardView cvDeliveryTimeContainer;
    RelativeLayout rlCardHolderChooseDateTime;
    CardView cvChooseDateTimeContainer;
    RelativeLayout rlCardHolderOrderInfo;
    CardView cvOrderInfoContainer;
    RelativeLayout rlPage;
    NestedScrollView nsvContentHolder;
    Button btnCommonBookingButton;
    TextView tvExpandCalendar;
    LinearLayout llBookingContentPage;
    TextView tvOrderInfo;
    CommonAlert alert;
    CommonSegmentedBar segmented_bar;
    RelativeLayout rlTimeSelectorContainer;

    @InjectPresenter
    DeliveryDatePresenter mPresenter;

    private BaseActivity mActivity;

    public static DeliveryDateFragment newInstance() {
        return new DeliveryDateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery_resto_date, container, false);
        mActivity = (BaseActivity) getActivity();
        WindowUtils.setLightStatusBar(mActivity);
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        toolbar = view.findViewById(R.id.toolbar);
        pbProgress = view.findViewById(R.id.pbProgress);
        phlPlaceholder = view.findViewById(R.id.phlPlaceholder);
        scCalendar = view.findViewById(R.id.scCalendar);
        ivTimeSelectIcon = view.findViewById(R.id.ivTimeSelectIcon);
        tvSelectedTime = view.findViewById(R.id.tvSelectedTime);
        tvToolbarSubTitle = view.findViewById(R.id.tvToolbarSubTitle);
        rlCardHolderDeliveryTime = view.findViewById(R.id.rlCardHolderDeliveryTime);
        cvDeliveryTimeContainer = view.findViewById(R.id.cvDeliveryTimeContainer);
        rlCardHolderChooseDateTime = view.findViewById(R.id.rlCardHolderChooseDateTime);
        cvChooseDateTimeContainer = view.findViewById(R.id.cvChooseDateTimeContainer);
        rlCardHolderOrderInfo = view.findViewById(R.id.rlCardHolderOrderInfo);
        cvOrderInfoContainer = view.findViewById(R.id.cvOrderInfoContainer);
        rlPage = view.findViewById(R.id.rlPage);
        nsvContentHolder = view.findViewById(R.id.nsvContentHolder);
        btnCommonBookingButton = view.findViewById(R.id.btnCommonBookingButton);
        tvExpandCalendar = view.findViewById(R.id.tvExpandCalendar);
        llBookingContentPage = view.findViewById(R.id.llBookingContentPage);
        tvOrderInfo = view.findViewById(R.id.tvOrderInfo);
        alert = view.findViewById(R.id.alert);
        segmented_bar = view.findViewById(R.id.segmented_bar);
        btnCommonBookingButton.setOnClickListener(v -> onAcceptClick());
        rlTimeSelectorContainer = view.findViewById(R.id.rlTimeSelectorContainer);
        rlTimeSelectorContainer.setOnClickListener(v -> onTimeSelectClick());
        tvExpandCalendar.setOnClickListener(v -> onExpandCalendarClick());
        initViews();
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            KeyboardUtil.hideSoftKeyboard(mActivity);
            mActivity.onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void refresh() {
    }

    @Override
    public void willBeDisplayed() {
        WindowUtils.setLightStatusBar(mActivity);
    }

    @Override
    public void willBeHidden() {
    }

    public void onAcceptClick() {
        mPresenter.onAcceptClick();
    }

    public void onTimeSelectClick() {
        mPresenter.onSetVisitTimeClick();
    }

    public void onExpandCalendarClick() {
        Appearance newAppearance = scCalendar.getAppearance().equals(Appearance.WEEK) ?
                Appearance.MONTH : Appearance.WEEK;
        if (scCalendar.setAppearance(newAppearance)) {
            if (newAppearance.equals(Appearance.WEEK)) {
                mPresenter.onCalendarCollapsed();
            } else if (newAppearance.equals(Appearance.MONTH)) {
                mPresenter.onCalendarExpanded();
            }
        }
    }

    @Override
    public void setCollapseCalendarTitle() {
        tvExpandCalendar.setText(mActivity.getResources().getString(R.string.collapse_calendar));
    }

    @Override
    public void setExpandCalendarTitle() {
        tvExpandCalendar.setText(mActivity.getResources().getString(R.string.expand_calendar));
    }

    @Override
    public void showProgress() {
        pbProgress.setVisibility(View.VISIBLE);
        rlPage.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        pbProgress.setVisibility(View.GONE);
        rlPage.setVisibility(View.VISIBLE);
    }

    @Override
    public void bindBookingTime(Pair<Integer, Integer> bookingTime) {
        if (bookingTime == null) {
            ivTimeSelectIcon.setImageDrawable(ContextCompat.getDrawable(mActivity,
                    R.drawable.ic_service_time_booking_beauty));
            tvSelectedTime.setTextColor(ContextCompat.getColor(mActivity,
                    R.color.colorInactive));
            tvSelectedTime.setText(mActivity.getResources().getString(
                    R.string.delivery_time));
        } else {
            ivTimeSelectIcon.setImageDrawable(ContextCompat.getDrawable(mActivity,
                    R.drawable.service_time_visit));
            tvSelectedTime.setTextColor(ContextCompat.getColor(mActivity,
                    R.color.resto));
            tvSelectedTime.setText(BookingUtil.formatTime(bookingTime.first, bookingTime.second));
        }
    }

    @Override
    public void showPageLoadingError() {
        btnCommonBookingButton.setVisibility(View.INVISIBLE);
        nsvContentHolder.setVisibility(View.INVISIBLE);
        phlPlaceholder.setState(CommonPlaceHolder.STATE_EMPTY);
    }

    @Override
    public void openVisitTimePicker(int hourOfDay, int minute) {
        TimePickerDialog dialog = new TimePickerDialog(mActivity,
                (view, hourOfDay1, minute1) -> mPresenter.onVisitTimePicked(hourOfDay1, minute1),
                hourOfDay,
                minute,
                DateFormat.is24HourFormat(mActivity));
        dialog.show();
    }

    @Override
    public void setWorkDays(List<DateTime> workDays) {
        scCalendar.initCalendar(workDays);
    }

    @Override
    public void setCalendarVisibleMonthCount(int visibleMonthCount) {
        scCalendar.setVisibleMonthAfterNow(visibleMonthCount);
    }

    @Override
    public void showInvalidSelectedTimeError() {
        alert.setMessageText(mActivity.getResources().getString(R.string.error_invalid_delivery_time_resto));
        alert.setType(CommonAlert.Type.WARNING);
        alert.show();
    }

    @Override
    public void refreshAcceptState(boolean isActive) {
        btnCommonBookingButton.setVisibility(isActive ? View.VISIBLE : View.GONE);
    }

    @Override
    public void bindMinDeliveryTime(int minutes) {
        tvToolbarSubTitle.setVisibility(View.VISIBLE);
        tvToolbarSubTitle.setText(getResources().getString(
                R.string.min_delivery_time_min, minutes));
    }

    @Override
    public void showCalendarLayout() {
        TransitionManager.beginDelayedTransition(llBookingContentPage, new ChangeBounds());
        rlCardHolderChooseDateTime.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCalendarLayout() {
        TransitionManager.beginDelayedTransition(llBookingContentPage, new ChangeBounds());
        rlCardHolderChooseDateTime.setVisibility(View.GONE);
    }

    @Override
    public void bindOrderInformation(String clientName, String phone, String city, String street,
                                     String house, @Nullable String apartment,
                                     @Nullable String doorPhoneCode, @Nullable String comment) {
        tvOrderInfo.setText(formatOrderInfo(clientName, phone, city, street, house, apartment,
                doorPhoneCode, comment));
    }

    @Override
    public void openThankYouScreen() {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentUtil.popAllBackStack(fragmentManager);
        openThankYouFragment();
    }

    @Override
    public void onCreateOrderFailed(Throwable throwable) {
        openBookingErrorDialog();
    }

    private void openBookingErrorDialog() {
        if (mActivity == null) return;
        FragmentManager fm = mActivity.getSupportFragmentManager();
        BookingErrorDialog.newInstance().show(fm, BookingErrorDialog.class.getSimpleName());
    }

    private String formatOrderInfo(String clientName, String phone, String city, String street,
                                   String house, @Nullable String apartment,
                                   @Nullable String doorPhoneCode, @Nullable String comment) {
        String namePhoneFormat = "%s %s";
        String cityStreetHouseApartmentFormat = !StringUtil.isEmpty(apartment)
                ? "\n\n%s, %s, %s/%s"
                : "\n\n%s, %s, %s";
        String doorCodeFormat = !StringUtil.isEmpty(doorPhoneCode)
                ? ("\n" + mActivity.getResources().getString(R.string.door_phone_code) + " - %s")
                : "";
        String commentFormat = !StringUtil.isEmpty(comment)
                ? ("\n\n" + mActivity.getResources().getString(R.string.your_comment) + "\n%s")
                : "";
        return String.format(namePhoneFormat, clientName, phone)
                + String.format(cityStreetHouseApartmentFormat, city, street, house, apartment)
                + String.format(doorCodeFormat, doorPhoneCode)
                + String.format(commentFormat, comment);
    }

    private void openThankYouFragment() {
        if (mActivity == null) return;
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentUtil.openDeliveryRestoThankYouFragment(fragmentManager, R.id.deliveryRestoContainer);
    }

    private void revalidatePlaceHolder() {
        phlPlaceholder.setState(CommonPlaceHolder.STATE_HIDE);
    }

    private void initViews() {
        initToolbar();
        initSpinner();
        initCalendar();
        setOutlineProviders();
        initAccept();
        initSegmentedBar();
        hideCalendarLayout();
    }

    private void initToolbar() {
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setTitle("");
        mActivity.getSupportActionBar().setElevation(0);
    }

    private void setOutlineProviders() {
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderChooseDateTime, cvChooseDateTimeContainer);
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderDeliveryTime, cvDeliveryTimeContainer);
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderOrderInfo, cvOrderInfoContainer);
    }

    private void initAccept() {
        btnCommonBookingButton.setText(mActivity.getResources().getString(R.string.make_order));
        btnCommonBookingButton.setBackground(ContextCompat.getDrawable(mActivity,
                R.drawable.background_common_button_red_light));
    }

    private void initSegmentedBar() {
        segmented_bar.selectLeft();
        segmented_bar.setListener(mPresenter);
        segmented_bar.setLeftSegmentText(mActivity.getResources().getString(R.string.minimal));
        segmented_bar.setRightSegmentText(mActivity.getResources().getString(R.string.specify));
    }

    private void initSpinner() {
        ColorUtil.setProgressColor(pbProgress, R.color.colorPrimary);
    }

    private void initCalendar() {
        scCalendar.setCalendarListener(mPresenter);
        scCalendar.setVisibleMonthAfterNow(6);
        scCalendar.setVisibleMonthBeforeNow(2);
        scCalendar.setSelectedColor(ContextCompat.getColor(mActivity, R.color.resto));
    }
}
