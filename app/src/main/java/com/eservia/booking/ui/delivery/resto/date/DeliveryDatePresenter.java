package com.eservia.booking.ui.delivery.resto.date;

import android.util.Pair;

import androidx.annotation.Nullable;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.model.booking_status.BookingStatus;
import com.eservia.booking.model.booking_status.delivery.DeliveryStatus;
import com.eservia.booking.util.BookingUtil;
import com.eservia.common.view.CommonSegmentedBar;
import com.eservia.model.interactors.resto.RestoDeliveryDateTimeInteractor;
import com.eservia.model.interactors.resto.RestoDeliveryRules;
import com.eservia.simplecalendar.SimpleCalendarListener;
import com.eservia.utils.StringUtil;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class DeliveryDatePresenter extends BasePresenter<DeliveryDateView> implements
        SimpleCalendarListener, CommonSegmentedBar.CommonSegmentedBarListener {

    @Inject
    BookingStatus mBookingStatus;

    @Inject
    RestoDeliveryDateTimeInteractor mRestoDeliveryDateTimeInteractor;

    private Disposable mDisposable;

    @Nullable
    private Pair<Integer, Integer> mBookingTime;

    @Nullable
    private DateTime mSelectedDay;

    private List<DateTime> mWorkDays = new ArrayList<>();

    private boolean mMinDeliveryTime = true;

    public DeliveryDatePresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        refresh();
    }

    @Override
    public void onDaySelected(DateTime day) {
        mSelectedDay = day;
        getViewState().refreshAcceptState(canMakeBooking());
    }

    @Override
    public void onDaySelectionCancelled(DateTime day) {
    }

    @Override
    public void onLeftSegmentSelected() {
        mMinDeliveryTime = true;
        getViewState().refreshAcceptState(canMakeBooking());
        getViewState().hideCalendarLayout();
    }

    @Override
    public void onRightSegmentSelected() {
        mMinDeliveryTime = false;
        getViewState().refreshAcceptState(canMakeBooking());
        getViewState().showCalendarLayout();
    }

    void onSetVisitTimeClick() {
        Pair<Integer, Integer> hourMin = hourMinForTimePicker();
        getViewState().openVisitTimePicker(hourMin.first, hourMin.second);
    }

    void onVisitTimePicked(int hourOfDay, int minute) {
        if (!isValidDeliveryTime(mSelectedDay, new Pair<>(hourOfDay, minute))) {
            onSelectedBookingTimeIsInvalid();
        }
        mBookingTime = new Pair<>(hourOfDay, minute);
        getViewState().bindBookingTime(mBookingTime);
        getViewState().refreshAcceptState(canMakeBooking());
    }

    void onCalendarExpanded() {
        getViewState().setCollapseCalendarTitle();
    }

    void onCalendarCollapsed() {
        getViewState().setExpandCalendarTitle();
    }

    public void onAcceptClick() {
        if (paginationInProgress(mDisposable)) {
            return;
        }
        if (!mMinDeliveryTime && !isValidDeliveryTime(mSelectedDay, mBookingTime)) {
            onSelectedBookingTimeIsInvalid();
            return;
        }
        if (!canMakeBooking()) {
            return;
        }
        saveBookingParametersToState();
        createOrder();
    }

    private void onCreateOrderSuccess() {
        getViewState().hideProgress();
        getViewState().openThankYouScreen();
        removeCurrentBookingStatus();
        mDisposable = null;
    }

    private void onCreateOrderFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onCreateOrderFailed(throwable);
        mDisposable = null;
    }

    private void removeCurrentBookingStatus() {
        mBookingStatus.removeDeliveryStatus();
    }

    private void refresh() {
        mBookingTime = null;
        mSelectedDay = null;
        mMinDeliveryTime = true;
        mWorkDays.clear();

        getViewState().bindBookingTime(mBookingTime);
        getViewState().refreshAcceptState(canMakeBooking());
        getViewState().bindMinDeliveryTime(RestoDeliveryRules.MIN_MINUTES_FOR_DELIVERY_RESTO_ORDER);
        getViewState().bindOrderInformation(deliveryStatus().getName(), deliveryStatus().getPhone(),
                deliveryStatus().getCity(), deliveryStatus().getDeliveryAddress(),
                deliveryStatus().getHouse(), deliveryStatus().getApartment(),
                deliveryStatus().getDoorPhoneCode(), deliveryStatus().getComment());

        fillWorkDays();
        getViewState().setCalendarVisibleMonthCount(getCalendarVisibleMonthCount());
    }

    private void fillWorkDays() {
        cancelPagination(mDisposable);
        Observable<List<DateTime>> observable = mRestoDeliveryDateTimeInteractor
                .computeWorkDays()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        mDisposable = observable.subscribe(this::onWorkDaysLoaded,
                this::onWorkDaysLoadingFailed);
        addSubscription(mDisposable);
    }

    private void createOrder() {
        getViewState().showProgress();
        cancelPagination(mDisposable);
        Observable<Boolean> observable = mRestoDeliveryDateTimeInteractor
                .createOrder((long) deliveryStatus().getBusiness().getId(),
                        (long) deliveryStatus().getAddress().getId(),
                        getExpectedDeliveryTime(),
                        deliveryStatus().getStreet().getId(),
                        formatDeliveryLocation(),
                        deliveryStatus().getComment(),
                        deliveryStatus().getPhone(),
                        deliveryStatus().getName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mDisposable = observable.subscribe(success -> onCreateOrderSuccess(),
                this::onCreateOrderFailed);
        addSubscription(mDisposable);
    }

    private void onWorkDaysLoaded(List<DateTime> workDays) {
        mWorkDays = workDays;
        getViewState().setWorkDays(mWorkDays);
        mDisposable = null;
    }

    private void onWorkDaysLoadingFailed(Throwable throwable) {
        getViewState().showPageLoadingError();
        mDisposable = null;
    }

    @Nullable
    private String getExpectedDeliveryTime() {
        if (mMinDeliveryTime) {
            return null;
        }
        int hour = deliveryStatus().getExpectedDeliveryHourMin().first;
        int minute = deliveryStatus().getExpectedDeliveryHourMin().second;
        DateTime day = deliveryStatus().getExpectedDeliveryDay();
        return BookingUtil.formatRestoBookingTime(day.withHourOfDay(hour).withMinuteOfHour(minute));
    }

    private String formatDeliveryLocation() {
        String apartment = !StringUtil.isEmpty(deliveryStatus().getApartment())
                ? deliveryStatus().getApartment() : "";
        String doorCode = !StringUtil.isEmpty(deliveryStatus().getDoorPhoneCode())
                ? "КОД " + deliveryStatus().getDoorPhoneCode() : "";

        return deliveryStatus().getCity()
                + " " + deliveryStatus().getDeliveryAddress()
                + " " + deliveryStatus().getHouse()
                + " " + apartment
                + " " + doorCode;
    }

    private int getCalendarVisibleMonthCount() {
        long maxAmountOfDays = RestoDeliveryRules.MAX_DAYS_FOR_DELIVERY_RESTO_ORDER;
        return (int) ((maxAmountOfDays / 30) + 1);
    }

    private void saveBookingParametersToState() {
        if (!mMinDeliveryTime) {
            deliveryStatus().setExpectedDeliveryDay(mSelectedDay);
            deliveryStatus().setExpectedDeliveryHourMin(mBookingTime);
        }
    }

    private boolean isValidDeliveryTime(DateTime day, Pair<Integer, Integer> time) {
        if (day == null || time == null) {
            return true;
        }
        DateTime orderTime = day.withHourOfDay(time.first).withMinuteOfHour(time.second);
        return mRestoDeliveryDateTimeInteractor.validateDeliveryTime(orderTime).blockingFirst();
    }

    private void onSelectedBookingTimeIsInvalid() {
        getViewState().showInvalidSelectedTimeError();
    }

    private Pair<Integer, Integer> hourMinForTimePicker() {
        if (mBookingTime != null) {
            return mBookingTime;
        }
        DateTime minDeliveryTime = DateTime.now().plusMinutes(
                RestoDeliveryRules.MIN_MINUTES_FOR_DELIVERY_RESTO_ORDER + 1);
        return new Pair<>(minDeliveryTime.getHourOfDay(), minDeliveryTime.getMinuteOfHour());
    }

    private boolean canMakeBooking() {
        return mMinDeliveryTime || (mSelectedDay != null && mBookingTime != null);
    }

    private DeliveryStatus deliveryStatus() {
        return mBookingStatus.getDeliveryStatus();
    }
}
