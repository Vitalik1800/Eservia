package com.eservia.booking.ui.booking.resto.date_time;

import android.content.Context;
import android.util.Pair;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.model.booking_status.BookingStatus;
import com.eservia.booking.util.BookingUtil;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.RestoBookingSettings;
import com.eservia.model.entity.RestoBookingWorkSchedule;
import com.eservia.model.interactors.resto.RestoBookingDateTimeInteractor;
import com.eservia.simplecalendar.SimpleCalendarListener;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class BookingDateTimePresenter extends BasePresenter<BookingDateTimeView> implements
        SimpleCalendarListener {

    @Inject
    RestoBookingDateTimeInteractor mRestoBookingDateTimeInteractor;

    @Inject
    BookingStatus mBookingStatus;

    @Inject
    Context mContext;

    private Disposable mDisposable;

    private Disposable mWorkDaysDisposable;

    private Address mAddress;

    private final List<Pair<Integer, String>> mVisitDurationsList = new ArrayList<>();

    private int mNumberOfPersons;

    private Integer mVisitDuration;

    private Pair<Integer, Integer> mBookingTime;

    private RestoBookingSettings mBookingSettings;

    private List<DateTime> mWorkDays = new ArrayList<>();

    private DateTime mSelectedDay;

    public BookingDateTimePresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mAddress = mBookingStatus.getRestoStatus().getAddress();
        Business mBusiness = mBookingStatus.getRestoStatus().getBusiness();
        mBookingSettings = mBookingStatus.getRestoStatus().getBookingSettings();
        getViewState().showSelectedAddress(mAddress);
        refresh();
    }

    @Override
    public void onDaySelected(DateTime day) {
        mSelectedDay = day;
        getViewState().refreshAcceptState(canGoToNextStep());
        for (RestoBookingWorkSchedule workTime : mBookingSettings.getWorkSchedule()) {
            if (workTime.getDay().equals((long) day.getDayOfWeek())) {
                getViewState().showSelectedDayWorkTime(workTime);
                return;
            }
        }
        getViewState().showSelectedDayWorkTime(null);
    }

    @Override
    public void onDaySelectionCancelled(DateTime day) {
    }

    void onPersonsNumberChanged(int persons) {
        mNumberOfPersons = persons;
    }

    void onSetVisitDurationClick() {
        getViewState().openVisitDurationPicker(mVisitDurationsList, visitDurationSelectedItemPosition());
    }

    void onSetVisitTimeClick() {
        Pair<Integer, Integer> hourMin = hourMinForBookingTimePicker();
        getViewState().openVisitTimePicker(hourMin.first, hourMin.second);
    }

    void onVisitDurationPicked(Pair<Integer, String> visitDuration) {
        if (isValidBookingTime(mSelectedDay, mBookingTime, visitDuration.first)) {
            onSelectedBookingTimeIsInvalid();
        }
        mVisitDuration = visitDuration.first;
        getViewState().bindVisitDuration(mVisitDuration);
        getViewState().refreshAcceptState(canGoToNextStep());
    }

    void onVisitTimePicked(int hourOfDay, int minute) {
        if (isValidBookingTime(mSelectedDay, new Pair<>(hourOfDay, minute), mVisitDuration)) {
            onSelectedBookingTimeIsInvalid();
        }
        mBookingTime = new Pair<>(hourOfDay, minute);
        getViewState().bindBookingTime(mBookingTime);
        getViewState().refreshAcceptState(canGoToNextStep());
    }

    void onCalendarExpanded() {
        getViewState().setCollapseCalendarTitle();
    }

    void onCalendarCollapsed() {
        getViewState().setExpandCalendarTitle();
    }

    public void onAcceptClick() {
        if (!canGoToNextStep()) {
            return;
        }
        if (isValidBookingTime(mSelectedDay, mBookingTime, mVisitDuration)) {
            onSelectedBookingTimeIsInvalid();
            return;
        }
        saveBookingParametersToState();
        getViewState().openPlacementScreen();
    }

    private void refresh() {
        mNumberOfPersons = 1;
        mVisitDuration = null;
        mBookingTime = null;
        mVisitDurationsList.clear();
        mWorkDays.clear();
        mSelectedDay = null;

        getViewState().bindNumberOfPersons(mNumberOfPersons);
        getViewState().bindVisitDuration(mVisitDuration);
        getViewState().bindBookingTime(mBookingTime);
        getViewState().refreshAcceptState(canGoToNextStep());

        if (mBookingSettings == null) {
            loadBookingSettings();
        } else {
            onNewBookingSettings();
        }
    }

    private void initBookingSettings(RestoBookingSettings bookingSettings) {
        mBookingSettings = bookingSettings;
        onNewBookingSettings();
    }

    private void onNewBookingSettings() {
        fillVisitDurationsList();
        fillWorkDays();
        getViewState().setCalendarVisibleMonthCount(getCalendarVisibleMonthCount());
        getViewState().setMaxNumberOfPerson(getMaxNumberOfPerson());
    }

    private int getCalendarVisibleMonthCount() {
        long maxAmountOfDays = mBookingSettings.getMaxAmountOfDaysAdvanceForBooking();
        return (int) ((maxAmountOfDays / 30) + 1);
    }

    private int getMaxNumberOfPerson() {
        return (int) mBookingSettings.getMaxAmountPeopleForBooking().longValue();
    }

    private void fillVisitDurationsList() {
        int visitDuration = (int) (mBookingSettings.getMinimumDurationOfBooking() / 1000 / 60);
        int step = 30;
        int maxVisitDuration = 480;
        while (visitDuration <= maxVisitDuration) {
            String formattedDuration = BookingUtil.formattedDuration(mContext, visitDuration);
            mVisitDurationsList.add(new Pair<>(visitDuration, formattedDuration));
            visitDuration += step;
        }
    }

    private void saveBookingParametersToState() {
        mBookingStatus.getRestoStatus().setBookingDay(mSelectedDay);
        mBookingStatus.getRestoStatus().setBookingHourMin(mBookingTime);
        mBookingStatus.getRestoStatus().setVisitDuration(mVisitDuration);
        mBookingStatus.getRestoStatus().setNumberOfPersons(mNumberOfPersons);
    }

    private void fillWorkDays() {
        getViewState().showProgress();
        cancelPagination(mWorkDaysDisposable);
        Observable<List<DateTime>> observable = mRestoBookingDateTimeInteractor
                .computeWorkDays(mBookingSettings)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        mWorkDaysDisposable = observable.subscribe(this::onWorkDaysLoaded,
                this::onWorkDaysLoadingFailed);
        addSubscription(mWorkDaysDisposable);
    }

    private boolean isValidBookingTime(DateTime day, Pair<Integer, Integer> time,
                                       Integer visitDuration) {
        if (day == null || time == null || visitDuration == null) {
            return false;
        }
        DateTime dateTimeVisitStart = day.withHourOfDay(time.first).withMinuteOfHour(time.second);
        DateTime dateTimeVisitEnd = dateTimeVisitStart.plusMinutes(visitDuration);

        Observable<Boolean> observableStartValid = mRestoBookingDateTimeInteractor.validateBookingTime(
                dateTimeVisitStart, mBookingSettings);
        Observable<Boolean> observableEndValid = mRestoBookingDateTimeInteractor.validateBookingTime(
                dateTimeVisitEnd, mBookingSettings);

        Boolean isStartValid = observableStartValid.blockingFirst();
        Boolean isEndValid = observableEndValid.blockingFirst();

        return !isStartValid || !isEndValid;
    }

    private void onSelectedBookingTimeIsInvalid() {
        getViewState().showInvalidSelectedTimeError();
    }

    private int visitDurationSelectedItemPosition() {
        if (mVisitDuration == null) {
            return 0;
        }
        for (int i = 0; i < mVisitDurationsList.size(); i++) {
            if (mVisitDurationsList.get(i).first.equals(mVisitDuration)) {
                return i;
            }
        }
        return 0;
    }

    private Pair<Integer, Integer> hourMinForBookingTimePicker() {
        if (mBookingTime != null) {
            return mBookingTime;
        }
        Calendar calendar = Calendar.getInstance();
        return new Pair<>(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    private boolean canGoToNextStep() {
        return mSelectedDay != null && mBookingTime != null && mVisitDuration != null
                && mNumberOfPersons > 0;
    }

    private void onWorkDaysLoaded(List<DateTime> workDays) {
        getViewState().hideProgress();
        mWorkDays = workDays;
        getViewState().setWorkDays(mWorkDays);
        mWorkDaysDisposable = null;
    }

    private void onWorkDaysLoadingFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().showPageLoadingError();
        mWorkDaysDisposable = null;
    }

    private void loadBookingSettings() {
        getViewState().showProgress();
        cancelPagination(mDisposable);
        Observable<RestoBookingSettings> observable = mRestoBookingDateTimeInteractor
                .getRestoBookingSettings(Long.valueOf(mAddress.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mDisposable = observable.subscribe(this::onBookingSettingsLoaded,
                this::onBookingSettingsLoadingFailed);
        addSubscription(mDisposable);
    }

    private void onBookingSettingsLoaded(RestoBookingSettings bookingSettings) {
        getViewState().hideProgress();
        initBookingSettings(bookingSettings);
        mDisposable = null;
    }

    private void onBookingSettingsLoadingFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().showPageLoadingError();
        mDisposable = null;
    }
}
