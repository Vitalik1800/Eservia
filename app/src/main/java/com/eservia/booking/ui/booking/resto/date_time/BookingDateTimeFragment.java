package com.eservia.booking.ui.booking.resto.date_time;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.eservia.booking.common.view.CommonNumberPickerDialog;
import com.eservia.booking.common.view.CommonPlaceHolder;
import com.eservia.booking.ui.home.BaseHomeFragment;
import com.eservia.booking.util.BookingUtil;
import com.eservia.booking.util.BusinessUtil;
import com.eservia.booking.util.ColorUtil;
import com.eservia.booking.util.FragmentUtil;
import com.eservia.booking.util.TimeUtil;
import com.eservia.booking.util.ViewUtil;
import com.eservia.booking.util.WindowUtils;
import com.eservia.butterknife.ButterKnife;
import com.eservia.common.view.CommonAlert;
import com.eservia.common.view.CommonCounter;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.RestoBookingWorkSchedule;
import com.eservia.simplecalendar.Appearance;
import com.eservia.simplecalendar.SimpleCalendar;
import com.eservia.utils.KeyboardUtil;

import org.joda.time.DateTime;

import java.util.List;

import moxy.presenter.InjectPresenter;

public class BookingDateTimeFragment extends BaseHomeFragment implements BookingDateTimeView {

    public static final String TAG = "date_time_fragment_booking_resto";

    CoordinatorLayout fragmentContainer;
    Toolbar toolbar;
    ProgressBar pbProgress;
    CommonPlaceHolder phlPlaceholder;
    SimpleCalendar scCalendar;
    RelativeLayout rlCardHolderNumberOfPersons;
    CardView cvNumberOfPersonsContainer;
    RelativeLayout rlCardHolderVisitDuration;
    CardView cvVisitDurationContainer;
    RelativeLayout rlCardHolderChooseDateTime;
    CardView cvChooseDateTimeContainer;
    CommonCounter number_of_persons_selector;
    ImageView ivVisitDurationSelectIcon;
    TextView tvVisitDurationDescription;
    ImageView ivTimeSelectIcon;
    TextView tvSelectedTime;
    TextView tvToolbarSubTitle;
    RelativeLayout rlPage;
    NestedScrollView nsvContentHolder;
    Button btnCommonBookingNextButton;
    TextView tvExpandCalendar;
    CommonAlert alert;
    RelativeLayout rlTimeSelectorContainer;

    @InjectPresenter
    BookingDateTimePresenter mPresenter;

    private BaseActivity mActivity;

    public static BookingDateTimeFragment newInstance() {
        return new BookingDateTimeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_resto, container, false);
        mActivity = (BaseActivity) getActivity();
        WindowUtils.setLightStatusBar(mActivity);
        setUnbinder(ButterKnife.bind(this, view));
        fragmentContainer = view.findViewById(R.id.fragment_container);
        toolbar = view.findViewById(R.id.toolbar);
        pbProgress = view.findViewById(R.id.pbProgress);
        phlPlaceholder = view.findViewById(R.id.phlPlaceholder);
        scCalendar = view.findViewById(R.id.scCalendar);
        rlCardHolderNumberOfPersons = view.findViewById(R.id.rlCardHolderNumberOfPersons);
        cvNumberOfPersonsContainer = view.findViewById(R.id.cvNumberOfPersonsContainer);
        rlCardHolderVisitDuration = view.findViewById(R.id.rlCardHolderVisitDuration);
        cvVisitDurationContainer = view.findViewById(R.id.cvVisitDurationContainer);
        rlCardHolderChooseDateTime = view.findViewById(R.id.rlCardHolderChooseDateTime);
        cvChooseDateTimeContainer = view.findViewById(R.id.cvChooseDateTimeContainer);
        number_of_persons_selector = view.findViewById(R.id.number_of_persons_selector);
        ivVisitDurationSelectIcon = view.findViewById(R.id.ivVisitDurationSelectIcon);
        tvVisitDurationDescription = view.findViewById(R.id.tvVisitDurationDescription);
        ivTimeSelectIcon = view.findViewById(R.id.ivTimeSelectIcon);
        tvSelectedTime = view.findViewById(R.id.tvSelectedTime);
        tvToolbarSubTitle = view.findViewById(R.id.tvToolbarSubTitle);
        rlPage = view.findViewById(R.id.rlPage);
        nsvContentHolder = view.findViewById(R.id.nsvContentHolder);
        btnCommonBookingNextButton = view.findViewById(R.id.btnCommonBookingNextButton);
        tvExpandCalendar = view.findViewById(R.id.tvExpandCalendar);
        alert = view.findViewById(R.id.alert);
        btnCommonBookingNextButton.setOnClickListener(v -> onAcceptClick());
        rlCardHolderVisitDuration.setOnClickListener(v -> onVisitDurationClick());
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

    public void onVisitDurationClick() {
        mPresenter.onSetVisitDurationClick();
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
    public void showSelectedAddress(Address address) {
        String street = address.getStreet();
        String number = address.getNumber();
        String addressName = BusinessUtil.getFullAddress(street, number);
        tvToolbarSubTitle.setVisibility(View.VISIBLE);
        tvToolbarSubTitle.setText(addressName);
    }

    @Override
    public void bindNumberOfPersons(int numberOfPersons) {
        number_of_persons_selector.setValue(numberOfPersons);
    }

    @Override
    public void bindVisitDuration(Integer visitDuration) {
        if (visitDuration == null) {
            ivVisitDurationSelectIcon.setImageDrawable(ContextCompat.getDrawable(mActivity,
                    R.drawable.ic_service_time_booking_beauty));
            tvVisitDurationDescription.setTextColor(ContextCompat.getColor(mActivity,
                    R.color.colorInactive));
            tvVisitDurationDescription.setText(mActivity.getResources().getString(
                    R.string.time_of_stay_in_the_institution));
        } else {
            ivVisitDurationSelectIcon.setImageDrawable(ContextCompat.getDrawable(mActivity,
                    R.drawable.service_time_visit));
            tvVisitDurationDescription.setTextColor(ContextCompat.getColor(mActivity,
                    R.color.resto));
            tvVisitDurationDescription.setText(BookingUtil.formattedDuration(mActivity,
                    visitDuration));
        }
    }

    @Override
    public void bindBookingTime(Pair<Integer, Integer> bookingTime) {
        if (bookingTime == null) {
            ivTimeSelectIcon.setImageDrawable(ContextCompat.getDrawable(mActivity,
                    R.drawable.ic_service_time_booking_beauty));
            tvSelectedTime.setTextColor(ContextCompat.getColor(mActivity,
                    R.color.colorInactive));
            tvSelectedTime.setText(mActivity.getResources().getString(
                    R.string.table_reservation_time));
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
        btnCommonBookingNextButton.setVisibility(View.INVISIBLE);
        nsvContentHolder.setVisibility(View.INVISIBLE);
        phlPlaceholder.setState(CommonPlaceHolder.STATE_EMPTY);
    }

    @Override
    public void openVisitDurationPicker(List<Pair<Integer, String>> visitDurations,
                                        int selectedItemPos) {
        CommonNumberPickerDialog dialog = CommonNumberPickerDialog.newInstance(
                mActivity.getResources().getString(R.string.visit_duration),
                mActivity.getResources().getString(R.string.time_of_stay_in_the_institution));
        dialog.setMinMaxValue(0, visitDurations.size() - 1);
        dialog.setSelectedItemAtPosition(selectedItemPos);
        dialog.setPickerFormatter(value -> visitDurations.get(value).second);
        dialog.setListener(value -> mPresenter.onVisitDurationPicked(visitDurations.get(value)));
        dialog.show(mActivity.getSupportFragmentManager(),
                CommonNumberPickerDialog.class.getSimpleName());
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
    public void showSelectedDayWorkTime(@Nullable RestoBookingWorkSchedule workTime) {
        tvToolbarSubTitle.setVisibility(View.VISIBLE);
        if (workTime == null) {
            tvToolbarSubTitle.setText("");
            return;
        }
        DateTime start = TimeUtil.dateTimeFromMillisOfDay((int) workTime.getStartTime().longValue());
        DateTime end = TimeUtil.dateTimeFromMillisOfDay((int) workTime.getEndTime().longValue());
        tvToolbarSubTitle.setText(BusinessUtil.formatHoursPeriod(mActivity, start, end));
    }

    @Override
    public void setCalendarVisibleMonthCount(int visibleMonthCount) {
        scCalendar.setVisibleMonthAfterNow(visibleMonthCount);
    }

    @Override
    public void setMaxNumberOfPerson(int maxNumberOfPerson) {
        number_of_persons_selector.setMaxValue(maxNumberOfPerson);
    }

    @Override
    public void showInvalidSelectedTimeError() {
        alert.setMessageText(mActivity.getResources().getString(R.string.error_invalid_booking_time_resto));
        alert.setType(CommonAlert.Type.WARNING);
        alert.show();
    }

    @Override
    public void refreshAcceptState(boolean isActive) {
        btnCommonBookingNextButton.setVisibility(isActive ? View.VISIBLE : View.GONE);
    }

    @Override
    public void openPlacementScreen() {
        if (mActivity == null) return;
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentUtil.showRestoBookingPlacementFragment(fragmentManager, R.id.bookingRestoContainer);
    }

    private void revalidatePlaceHolder() {
        phlPlaceholder.setState(CommonPlaceHolder.STATE_HIDE);
    }

    private void initViews() {
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setTitle("");

        initSpinner();
        initCalendar();

        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderChooseDateTime, cvChooseDateTimeContainer);
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderNumberOfPersons, cvNumberOfPersonsContainer);
        ViewUtil.setCardOutlineProvider(mActivity, rlCardHolderVisitDuration, cvVisitDurationContainer);

        btnCommonBookingNextButton.setBackground(ContextCompat.getDrawable(mActivity,
                R.drawable.background_common_button_red_light));

        number_of_persons_selector.setListener(new PersonsCounterListener());
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

    private class PersonsCounterListener implements CommonCounter.CommonCounterListener {

        @Override
        public void onCounterValueIncreased(int count) {
            mPresenter.onPersonsNumberChanged(count);
        }

        @Override
        public void onCounterValueDecreased(int count) {
            mPresenter.onPersonsNumberChanged(count);
        }

        @Override
        public void onUnableToIncrease() {
        }

        @Override
        public void onUnableToDecrease() {
        }
    }
}
