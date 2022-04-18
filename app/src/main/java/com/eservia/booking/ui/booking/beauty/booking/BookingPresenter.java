package com.eservia.booking.ui.booking.beauty.booking;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.model.booking_status.BookingStatus;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.booking.model.booking_status.beauty.TimeSlot;
import com.eservia.booking.util.BookingUtil;
import com.eservia.booking.util.TimeUtil;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.BeautyDiscount;
import com.eservia.model.entity.BeautyGradualTimeSlot;
import com.eservia.model.entity.BeautyStaff;
import com.eservia.model.entity.BeautyTimeSlot;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.WorkingDay;
import com.eservia.model.interactors.booking.BookingInteractor;
import com.eservia.model.interactors.business.BusinessInteractor;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.business_beauty.services.staff.BusinessBeautyStaffResponse;
import com.eservia.model.remote.rest.request.KeyList;
import com.eservia.model.remote.socket.WebSocketManager;
import com.eservia.simplecalendar.SimpleCalendarListener;
import com.eservia.utils.Contract;
import com.eservia.utils.LogUtils;
import com.eservia.utils.RRuleUtil;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class BookingPresenter extends BasePresenter<BookingView> implements
        PreparedServicesAdapter.OnItemClickListener,
        StaffAdapter.OnStaffAdapterClickListener,
        StaffAdapter.OnStaffPaginationListener,
        SimpleCalendarListener,
        TimeSlotAdapter.OnTimeSlotClickListener,
        AutoSelectedStaffAdapter.AutoSelectedStaffClickListener {

    private final List<BeautyStaff> mStaffList = new ArrayList<>();

    private final List<BeautyTimeSlot> mTimeSlots = new ArrayList<>();

    private final List<BeautyGradualTimeSlot> mGradualTimeSlots = new ArrayList<>();

    @Inject
    RestManager mRestManager;

    @Inject
    BusinessInteractor mBusinessInteractor;

    @Inject
    BookingInteractor mBookingInteractor;

    @Inject
    BookingStatus mBookingStatus;

    @Inject
    WebSocketManager mWebSocketManager;

    private Disposable mStaffPaginationDisposable;

    private Disposable mWorkDaysDisposable;

    private Disposable mTimeSlotsDisposable;

    private boolean mIsAllStaffLoaded = false;

    private Preparation mSelectedPreparation;

    private Address mAddress;

    private Business mBusiness;

    private List<DateTime> mWorkingDays = new ArrayList<>();

    private Priority mPriority = Priority.STAFF;

    @ColorInt
    private int mCurrentColor;

    public BookingPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mAddress = mBookingStatus.getBeautyStatus().getAddress();
        mBusiness = mBookingStatus.getBeautyStatus().getBusiness();
        getViewState().showSelectedAddress(mAddress);
        List<Preparation> preparations = new ArrayList<>(getAllPreparations());
        if (preparations.size() > 0) {
            getViewState().onPreparedServicesLoadingSuccess(preparations);
            if (isAllPreparationsFull()) {
                getViewState().selectFirstPreparedServiceItem();
            } else {
                selectNextNotFilledPreparation();
            }
        }
    }

    @Override
    public void onPreparedServiceItemClick(PreparedServicesAdapterItem adapterItem) {
        if (mPriority.equals(Priority.DATE) && isMultiServiceBooking() && isAllPreparationsFull()) {

            mSelectedPreparation = adapterItem.getPreparation();
            mCurrentColor = mSelectedPreparation.getColorId();
            getViewState().onNewColor(mCurrentColor);
            getViewState().setSelectedPreparation(adapterItem.getPreparation()
                    .getService().getId());
            getViewState().setSelectedDay(adapterItem.getPreparation().getDay());

            return;
        }

        processServiceClick(adapterItem);

        if (mPriority.equals(Priority.STAFF)) {
            processServiceClickStaffPriority();

        } else if (mPriority.equals(Priority.DATE)) {
            processServiceClickDatePriority();
        }
    }

    @Override
    public void onStaffAdapterItemClick(StaffAdapterItem adapterItem) {
        processStaffClick(adapterItem);

        if (mPriority.equals(Priority.STAFF)) {
            processStaffClickStaffPriority();

        } else if (mPriority.equals(Priority.DATE)) {
            processStaffClickDatePriority();
        }
    }

    @Override
    public void onAutoSelectedStaffClick(AutoSelectedStaffAdapterItem item, int position) {
    }

    @Override
    public void onAutoSelectedStaffEditClick(AutoSelectedStaffAdapterItem item, int position) {
    }

    @Override
    public void onDaySelected(DateTime day) {
        processDayClick(day);

        if (mPriority.equals(Priority.STAFF)) {
            processDayClickStaffPriority();

        } else if (mPriority.equals(Priority.DATE)) {
            processDayClickDatePriority();
        }
    }

    @Override
    public void onDaySelectionCancelled(DateTime day) {
        mSelectedPreparation.setDay(null);
        onSelectedPreparationUpdated();
    }

    @Override
    public void onTimeSlotItemClick(TimeSlotAdapterItem adapterItem) {
        processTimeSlotClick(adapterItem);

        if (mPriority.equals(Priority.STAFF)) {
            processTimeSlotClickStaffPriority(adapterItem);

        } else if (mPriority.equals(Priority.DATE)) {
            processTimeSlotClickDatePriority(adapterItem);
        }
    }

    void onPriorityStaffClicked() {
        if (mPriority.equals(Priority.STAFF)) {
            return;
        }
        mPriority = Priority.STAFF;
        cancelDisposables();
        clearAllPreparationsDetails();
        resetStaffs();
        resetSelectedStaffDescription();
        resetAutoSelectedStaffs();
        resetTimeSlots();
        resetCalendar();
        getViewState().performStaffPriority();
        processServiceClickStaffPriority();
        onSelectedPreparationUpdated();
        getViewState().setSelectedPreparation(mSelectedPreparation.getService().getId());
    }

    void onPriorityDateClicked() {
        if (mPriority.equals(Priority.DATE)) {
            return;
        }
        mPriority = Priority.DATE;
        cancelDisposables();
        clearAllPreparationsDetails();
        resetStaffs();
        resetSelectedStaffDescription();
        resetAutoSelectedStaffs();
        resetTimeSlots();
        resetCalendar();
        getViewState().performDatePriority(isMultiServiceBooking());
        processServiceClickDatePriority();
        onSelectedPreparationUpdated();
        getViewState().setAllPreparationsSelected();
    }

    void onCalendarExpanded() {
        getViewState().setCollapseCalendarTitle();
    }

    void onCalendarCollapsed() {
        getViewState().setExpandCalendarTitle();
    }

    private void cancelDisposables() {
        mTimeSlots.clear();
        mStaffList.clear();
        mWorkingDays.clear();
        cancelPagination(mStaffPaginationDisposable);
        cancelPagination(mTimeSlotsDisposable);
        cancelPagination(mWorkDaysDisposable);
    }

    private void clearCurrentPreparationDetails() {
        mSelectedPreparation.setStaff(null);
        mSelectedPreparation.setDay(null);
        mSelectedPreparation.setTimeSlot(null);
        mSelectedPreparation.setDiscount(null);
    }

    private void clearAllPreparationsDetails() {
        for (Preparation preparation : getAllPreparations()) {

            preparation.setStaff(null);
            preparation.setDay(null);
            preparation.setTimeSlot(null);
            preparation.setDiscount(null);
        }
    }

    private void resetStaffs() {
        getViewState().onStaffLoadingSuccess(new ArrayList<>(), true, mCurrentColor);
    }

    private void resetSelectedStaffDescription() {
        getViewState().refreshSelectedStaffInfo(null);
    }

    private void resetAutoSelectedStaffs() {
        getViewState().onAutoSelectedStaffLoadingSuccess(new ArrayList<>());
    }

    private void resetTimeSlots() {
        getViewState().onTimeSlotsLoadingSuccess(new ArrayList<>());
    }

    private void resetCalendar() {
        getViewState().initCalendar(mWorkingDays);
        onSelectedPreparationUpdated();
    }

    public void onAcceptClick() {
        if (isAllPreparationsFull()) {
            getViewState().showBasketFragment();
        } else if (mSelectedPreparation.isFull()) {
            selectNextNotFilledPreparation();
        }
    }

    @Override
    public void loadMoreStaff() {
        if (mPriority.equals(Priority.STAFF)) {
            if (!paginationInProgress(mStaffPaginationDisposable) && !mIsAllStaffLoaded) {
                makeStaffPagination();
            }
        }
    }

    void refreshStaffList() {
        if (mPriority.equals(Priority.STAFF)) {
            if (!paginationInProgress(mStaffPaginationDisposable)) {
                mStaffList.clear();
                mIsAllStaffLoaded = false;
                getViewState().onStaffLoadingSuccess(new ArrayList<>(), true, mCurrentColor);
                makeStaffPagination();
            }

        } else if (mPriority.equals(Priority.DATE)) {
            if (!paginationInProgress(mStaffPaginationDisposable)) {
                if (isSingleServiceBooking()) {
                    mStaffList.clear();
                    mIsAllStaffLoaded = false;
                    getViewState().onStaffLoadingSuccess(new ArrayList<>(), true, mCurrentColor);
                    loadStaffBySelectedSlot();
                } else {
                    mStaffList.clear();
                    mIsAllStaffLoaded = false;
                    getViewState().onAutoSelectedStaffLoadingSuccess(new ArrayList<>());
                    loadAutoSelectedStaffs();
                }
            }
        }
    }

    void refreshDateTime() {
        if (mPriority.equals(Priority.STAFF)) {
            loadStaffWorkDays();

        } else if (mPriority.equals(Priority.DATE)) {
            loadAddressWorkDays();
        }
    }

    void refreshTimeSlots() {
        if (mPriority.equals(Priority.STAFF)) {
            loadDayTimeSlots();

        } else if (mPriority.equals(Priority.DATE)) {
            if (isSingleServiceBooking()) {
                loadTimeSlotsByAddress();
            } else {
                loadTimeSlotsGradual();
            }
        }
    }

    private void onSelectedPreparationUpdated() {
        getViewState().revalidatePreparationsFinishedStates();
        getViewState().revalidateAcceptLayout(mSelectedPreparation.isFull(), isAllPreparationsFull());
    }

    private boolean isAllPreparationsFull() {
        for (Preparation preparation : getAllPreparations()) {
            if (!preparation.isFull()) {
                return false;
            }
        }
        return true;
    }

    private void selectNextNotFilledPreparation() {
        Preparation notFilledPreparation = null;
        for (Preparation preparation : getAllPreparations()) {
            if (!preparation.isFull()) {
                notFilledPreparation = preparation;
                break;
            }
        }
        if (notFilledPreparation != null) {
            getViewState().selectPreparationWithId(notFilledPreparation.getId());
        }
    }

    private void makeStaffPagination() {
        if (mStaffList.size() == 0) {
            getViewState().showStaffProgress();
        }
        cancelPagination(mStaffPaginationDisposable);

        KeyList services = new KeyList();
        services.add(mSelectedPreparation.getService().getId());

        Observable<BusinessBeautyStaffResponse> observable = mBusinessInteractor
                .getBusinessStaffs(mBusiness.getSector(), null, null, mBusiness.getId(), mAddress.getId(),
                        services, BeautyStaff.STATUS_ACTIVE, null, null,
                        PART, mStaffList.size() / PART + 1, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mStaffPaginationDisposable = observable.subscribe(this::onStaffLoadingSuccess,
                this::onStaffLoadingFailed);
        addSubscription(mStaffPaginationDisposable);
    }

    private void loadStaffBySelectedSlot() {
        if (mSelectedPreparation.getTimeSlot() == null) {
            return;
        }

        getViewState().showStaffProgress();

        cancelPagination(mStaffPaginationDisposable);

        KeyList staffs = new KeyList();
        staffs.addAll(staffsByTimeSlot(mSelectedPreparation.getTimeSlot().getTime()));

        Observable<BusinessBeautyStaffResponse> observable = mBusinessInteractor
                .getBusinessStaffs(mBusiness.getSector(), null, staffs, mBusiness.getId(), mAddress.getId(),
                        null, BeautyStaff.STATUS_ACTIVE, null, null,
                        PART, mStaffList.size() / PART + 1, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mStaffPaginationDisposable = observable.subscribe(this::onSlotStaffLoadingSuccess,
                this::onSlotStaffLoadingFailed);
        addSubscription(mStaffPaginationDisposable);
    }

    private void loadAutoSelectedStaffs() {
        for (Preparation preparation : getAllPreparations()) {
            if (preparation.getTimeSlot() == null) {
                return;
            }
        }

        getViewState().showAutoSelectedStaffProgress();

        cancelPagination(mStaffPaginationDisposable);

        HashSet<Integer> staffIds = new HashSet<>();

        for (Preparation p : getAllPreparations()) {

            BeautyTimeSlot beautyTimeSlot = p.getTimeSlot().getBeautyTimeSlot();
            if (!beautyTimeSlot.getStaffs().isEmpty()) {

                int randomIndex = new Random().nextInt(beautyTimeSlot.getStaffs().size());
                staffIds.add(beautyTimeSlot.getStaffs().get(randomIndex));
            }
        }

        Observable<BusinessBeautyStaffResponse> observable = mBusinessInteractor
                .getBusinessStaffs(mBusiness.getSector(), null,
                        new KeyList().addAll(staffIds),
                        null, null,
                        null, null, null, null,
                        null, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mStaffPaginationDisposable = observable.subscribe(this::onAutoSelectedStaffLoadingSuccess,
                this::onAutoSelectedStaffLoadingFailed);
        addSubscription(mStaffPaginationDisposable);
    }

    private List<Integer> staffsByTimeSlot(DateTime slot) {
        List<Integer> staffs = new ArrayList<>();
        for (BeautyTimeSlot beautyTimeSlot : mTimeSlots) {
            DateTime bSlot = DateTime.parse(beautyTimeSlot.getDate(),
                    DateTimeFormat.forPattern(BookingUtil.TIME_SLOT_PATTERN));
            if (bSlot.isEqual(slot)) {
                staffs.addAll(beautyTimeSlot.getStaffs());
                break;
            }
        }
        return staffs;
    }

    private void onStaffLoadingSuccess(BusinessBeautyStaffResponse response) {
        boolean firstPart = mStaffList.size() == 0;
        mStaffList.addAll(response.getData());
        getViewState().hideStaffProgress();
        getViewState().onStaffLoadingSuccess(mapToStaffAdapterItems(response.getData()),
                firstPart, mCurrentColor);
        if (firstPart && mStaffList.size() > 0 && mSelectedPreparation.getStaff() == null) {
            getViewState().selectFirstStaffItem();
        }
        getViewState().refreshSelectedStaffInfo(mSelectedPreparation.getStaff());
        mStaffPaginationDisposable = null;
        if (mStaffList.size() == response.getMeta().getTotal()) {
            mIsAllStaffLoaded = true;
        }
    }

    private void onStaffLoadingFailed(Throwable throwable) {
        getViewState().hideStaffProgress();
        getViewState().onStaffLoadingFailed(throwable);
        mStaffPaginationDisposable = null;
    }

    private void onSlotStaffLoadingSuccess(BusinessBeautyStaffResponse response) {
        mStaffList.addAll(response.getData());
        getViewState().hideStaffProgress();
        getViewState().onStaffLoadingSuccess(mapToStaffAdapterItems(response.getData()),
                true, mCurrentColor);
        if (mStaffList.size() > 0 && mSelectedPreparation.getStaff() == null) {
            getViewState().selectFirstStaffItem();
        }
        getViewState().refreshSelectedStaffInfo(mSelectedPreparation.getStaff());
        mStaffPaginationDisposable = null;
        mIsAllStaffLoaded = true;
    }

    private void onSlotStaffLoadingFailed(Throwable throwable) {
        getViewState().hideStaffProgress();
        getViewState().onStaffLoadingFailed(throwable);
        mStaffPaginationDisposable = null;
    }

    private void onAutoSelectedStaffLoadingSuccess(BusinessBeautyStaffResponse response) {
        mStaffList.addAll(response.getData());

        for (Preparation p : getAllPreparations()) {

            BeautyTimeSlot beautyTimeSlot = p.getTimeSlot().getBeautyTimeSlot();
            if (!beautyTimeSlot.getStaffs().isEmpty()) {
                for (BeautyStaff staff : response.getData()) {
                    for (Integer staffId : beautyTimeSlot.getStaffs()) {
                        if (staff.getId().equals(staffId)) {
                            p.setStaff(staff);
                        }
                    }
                }
            }
        }

        getViewState().hideAutoSelectedStaffProgress();
        getViewState().onAutoSelectedStaffLoadingSuccess(mapToAutoSelectedStaffAdapterItems(
                getAllPreparations()));

        onSelectedPreparationUpdated();

        mStaffPaginationDisposable = null;
        mIsAllStaffLoaded = true;
    }

    private void onAutoSelectedStaffLoadingFailed(Throwable throwable) {
        getViewState().hideAutoSelectedStaffProgress();
        getViewState().onAutoSelectedStaffLoadingFailed(throwable);
        mStaffPaginationDisposable = null;
    }

    private List<StaffAdapterItem> mapToStaffAdapterItems(List<BeautyStaff> staffList) {
        List<StaffAdapterItem> adapterItems = new ArrayList<>();

        boolean isSelectedStaffInThisList = false;

        for (BeautyStaff staff : staffList) {

            boolean isSelected = false;

            if (mSelectedPreparation.getStaff() != null
                    && mSelectedPreparation.getStaff().getId().equals(staff.getId())) {
                isSelected = true;
                isSelectedStaffInThisList = true;
            }
            adapterItems.add(new StaffAdapterItem(isSelected, staff));
        }

        if (!isSelectedStaffInThisList) {
            mSelectedPreparation.setStaff(null);
        }
        return adapterItems;
    }

    private List<AutoSelectedStaffAdapterItem> mapToAutoSelectedStaffAdapterItems(
            List<Preparation> preparations) {

        List<AutoSelectedStaffAdapterItem> adapterItems = new ArrayList<>();

        for (Preparation p : preparations) {

            adapterItems.add(new AutoSelectedStaffAdapterItem(p, p.getColorId()));
        }
        return adapterItems;
    }

    private void loadStaffWorkDays() {
        if (mSelectedPreparation == null || mSelectedPreparation.getStaff() == null) return;
        if (paginationInProgress(mWorkDaysDisposable)) {
            cancelPagination(mWorkDaysDisposable);
        }
        getViewState().showDateTimeProgress();
        mWorkDaysDisposable = mBusinessInteractor
                .getStaffWorkingDays(mBusiness.getSector(), mSelectedPreparation.getStaff().getId())
                .map(response -> mapToRules(response.getData()))
                .map(rules -> RRuleUtil.getDaysByRuleList(rules, BookingUtil.MAX_BOOKING_MONTH_COUNT))
                .map(this::mapToDateTimes)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::workingDaysLoadingSuccess, this::workingDaysLoadingError);
        addSubscription(mWorkDaysDisposable);
    }

    private void loadAddressWorkDays() {
        if (mSelectedPreparation == null) return;
        if (paginationInProgress(mWorkDaysDisposable)) {
            cancelPagination(mWorkDaysDisposable);
        }
        getViewState().showDateTimeProgress();
        mWorkDaysDisposable = mRestManager
                .getBusinessAddressWorkingDays(mAddress.getId())
                .map(response -> mapToRules(response.getData()))
                .map(rules -> RRuleUtil.getDaysByRuleList(rules, BookingUtil.MAX_BOOKING_MONTH_COUNT))
                .map(this::mapToDateTimes)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onAddressWorkingDaysLoadingSuccess,
                        this::onAddressWorkingDaysLoadingError);
        addSubscription(mWorkDaysDisposable);
    }

    private void loadDayTimeSlots() {
        if (mSelectedPreparation == null
                || mSelectedPreparation.getService() == null
                || mSelectedPreparation.getDay() == null
                || mSelectedPreparation.getStaff() == null) {
            return;
        }
        if (paginationInProgress(mTimeSlotsDisposable)) {
            cancelPagination(mTimeSlotsDisposable);
        }
        mTimeSlots.clear();
        getViewState().onTimeSlotsLoadingSuccess(new ArrayList<>());
        getViewState().showTimeSlotsProgress();
        mTimeSlotsDisposable = mBookingInteractor
                .getBusinessTimeSlots(mBusiness.getSector(), mBusiness.getId(),
                        mAddress.getId(),
                        mSelectedPreparation.getService().getId(),
                        mSelectedPreparation.getStaff().getId(),
                        mSelectedPreparation.getDay().toString("YYYY-MM-dd"))
                .map(r -> {
                    mTimeSlots.addAll(r.getData());
                    return mapToTimeSlotItems(mTimeSlots);
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTimeSlotsLoadingSuccess, this::onTimeSlotsLoadingError);
        addSubscription(mTimeSlotsDisposable);
    }

    private void loadTimeSlotsByAddress() {
        if (mSelectedPreparation == null
                || mSelectedPreparation.getService() == null
                || mSelectedPreparation.getDay() == null) {
            return;
        }
        if (paginationInProgress(mTimeSlotsDisposable)) {
            cancelPagination(mTimeSlotsDisposable);
        }
        mTimeSlots.clear();
        getViewState().onTimeSlotsLoadingSuccess(new ArrayList<>());
        getViewState().showTimeSlotsProgress();
        mTimeSlotsDisposable = mBookingInteractor
                .getBusinessTimeSlots(mBusiness.getSector(), mBusiness.getId(),
                        mAddress.getId(),
                        mSelectedPreparation.getService().getId(),
                        null,
                        mSelectedPreparation.getDay().toString("YYYY-MM-dd"))
                .map(r -> {
                    mTimeSlots.addAll(r.getData());
                    return mapToTimeSlotItems(mTimeSlots);
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onAddressTimeSlotsLoadingSuccess,
                        this::onAddressTimeSlotsLoadingError);
        addSubscription(mTimeSlotsDisposable);
    }

    private void loadTimeSlotsGradual() {
        if (paginationInProgress(mTimeSlotsDisposable)) {
            cancelPagination(mTimeSlotsDisposable);
        }
        mGradualTimeSlots.clear();
        getViewState().onTimeSlotsLoadingSuccess(new ArrayList<>());
        getViewState().showTimeSlotsProgress();

        KeyList serviceIds = new KeyList();
        for (Preparation preparation : getAllPreparations()) {
            serviceIds.add(preparation.getService().getId());
        }

        mTimeSlotsDisposable = mBookingInteractor
                .getBusinessGradualTimeSlots(mBusiness.getSector(), mBusiness.getId(),
                        mAddress.getId(),
                        serviceIds,
                        mSelectedPreparation.getDay().toString("YYYY-MM-dd"))
                .map(r -> {
                    mGradualTimeSlots.addAll(r.getData());
                    return mapToGradualTimeSlotItems(mGradualTimeSlots);
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTimeSlotsGradualLoadingSuccess,
                        this::onTimeSlotsGradualLoadingError);
        addSubscription(mTimeSlotsDisposable);
    }

    private List<TimeSlotAdapterItem> mapToTimeSlotItems(List<BeautyTimeSlot> timeSlots) {
        List<TimeSlotAdapterItem> timeSlotAdapterItems = new ArrayList<>();
        boolean isSelectedTimeSlotInThisList = false;

        for (BeautyTimeSlot timeSlotEntity : timeSlots) {

            String timeSlot = timeSlotEntity.getDate();

            DateTime dateTime = DateTime.parse(timeSlot,
                    DateTimeFormat.forPattern(BookingUtil.TIME_SLOT_PATTERN));

            boolean isTimeSlotSelected = false;
            if (mSelectedPreparation.getTimeSlot() != null &&
                    isTimeSlotsSimilar(mSelectedPreparation.getTimeSlot().getTime(), dateTime)) {
                isTimeSlotSelected = true;
                isSelectedTimeSlotInThisList = true;
            }

            boolean isAvailableTimeSlot = true;
            for (Preparation preparation : getAllPreparations()) {

                if (preparation.getTimeSlot() != null
                        && !preparation.getId().equals(mSelectedPreparation.getId())) {

                    int selectedServiceDuration = mSelectedPreparation.getService().getDuration();
                    if (hasTimeCollision(dateTime,
                            selectedServiceDuration,
                            preparation.getTimeSlot().getTime(),
                            preparation.getService().getDuration())) {
                        isAvailableTimeSlot = false;
                        break;
                    }
                }
            }

            TimeSlotAdapterItem adapterItem = new TimeSlotAdapterItem(dateTime, getDiscount(timeSlotEntity));
            if (isTimeSlotSelected) {
                adapterItem.setSelected(true);
            }

            if (isAvailableTimeSlot) {
                timeSlotAdapterItems.add(adapterItem);
            }
        }
        if (!isSelectedTimeSlotInThisList) {
            mSelectedPreparation.setTimeSlot(null);
            mSelectedPreparation.setDiscount(null);
        }
        return timeSlotAdapterItems;
    }

    private List<TimeSlotAdapterItem> mapToGradualTimeSlotItems(List<BeautyGradualTimeSlot> timeSlots) {

        List<TimeSlotAdapterItem> timeSlotAdapterItems = new ArrayList<>();

        boolean isSelectedTimeSlotInThisList = false;

        for (BeautyGradualTimeSlot timeSlotEntity : timeSlots) {

            DateTime dateTime = DateTime.parse(timeSlotEntity.getTimeSlots().get(0).getDate(),
                    DateTimeFormat.forPattern(BookingUtil.TIME_SLOT_PATTERN));

            TimeSlotAdapterItem adapterItem = new TimeSlotAdapterItem(dateTime);

            if (mSelectedPreparation.getTimeSlot() != null &&
                    isTimeSlotsSimilar(mSelectedPreparation.getTimeSlot().getTime(), dateTime)) {
                isSelectedTimeSlotInThisList = true;
                adapterItem.setSelected(true);
            }

            timeSlotAdapterItems.add(adapterItem);
        }

        if (!isSelectedTimeSlotInThisList) {
            for (Preparation p : getAllPreparations()) {
                p.setTimeSlot(null);
                p.setDiscount(null);
            }
        }

        return timeSlotAdapterItems;
    }

    private boolean isTimeSlotsSimilar(DateTime first, DateTime second) {
        if (first == null || second == null) {
            return false;
        }
        return first.getHourOfDay() == second.getHourOfDay()
                && first.getMinuteOfHour() == second.getMinuteOfHour();
    }

    private boolean hasTimeCollision(DateTime timeCurrent, Integer currentDuration,
                                     DateTime otherTime, Integer otherDuration) {

        if (!TimeUtil.isSameDay(timeCurrent, otherTime)) {
            return false;
        }

        int otherHour = Integer.parseInt(otherTime.toString("HH"));
        int otherMinute = Integer.parseInt(otherTime.toString("mm"));

        int minutesOfStartService = otherHour * 60 + otherMinute;
        int minutesOfEndService = minutesOfStartService + otherDuration;

        int currentHour = Integer.parseInt(timeCurrent.toString("HH"));
        int currentMinute = Integer.parseInt(timeCurrent.toString("mm"));

        int minutesOfTimeSlot = currentHour * 60 + currentMinute;
        int minutesOfTimeSlotEnd = minutesOfTimeSlot + currentDuration;

        return !(minutesOfEndService <= minutesOfTimeSlot
                || minutesOfStartService >= minutesOfTimeSlotEnd);
    }

    private void onTimeSlotsLoadingSuccess(List<TimeSlotAdapterItem> timeSlotAdapterItems) {
        getViewState().hideTimeSlotsProgress();
        getViewState().onTimeSlotsLoadingSuccess(timeSlotAdapterItems);
        onSelectedPreparationUpdated();
        mTimeSlotsDisposable = null;
    }

    private void onTimeSlotsLoadingError(Throwable throwable) {
        getViewState().hideTimeSlotsProgress();
        getViewState().onTimeSlotsLoadingError(throwable);
        mTimeSlotsDisposable = null;
    }

    private void onAddressTimeSlotsLoadingSuccess(List<TimeSlotAdapterItem> timeSlotAdapterItems) {
        getViewState().hideTimeSlotsProgress();
        getViewState().onTimeSlotsLoadingSuccess(timeSlotAdapterItems);
        onSelectedPreparationUpdated();
        if (mSelectedPreparation.getTimeSlot() != null) {
            refreshStaffList();
        }
        mTimeSlotsDisposable = null;
    }

    private void onAddressTimeSlotsLoadingError(Throwable throwable) {
        getViewState().hideTimeSlotsProgress();
        getViewState().onTimeSlotsLoadingError(throwable);
        mTimeSlotsDisposable = null;
    }

    private void onTimeSlotsGradualLoadingSuccess(List<TimeSlotAdapterItem> timeSlotAdapterItems) {
        getViewState().hideTimeSlotsProgress();
        getViewState().onTimeSlotsLoadingSuccess(timeSlotAdapterItems);
        onSelectedPreparationUpdated();
        mTimeSlotsDisposable = null;
    }

    private void onTimeSlotsGradualLoadingError(Throwable throwable) {
        getViewState().hideTimeSlotsProgress();
        getViewState().onTimeSlotsLoadingError(throwable);
        mTimeSlotsDisposable = null;
    }

    private void workingDaysLoadingSuccess(List<DateTime> workingDays) {
        getViewState().hideDateTimeProgress();

        mWorkingDays = workingDays;

        getViewState().initCalendar(workingDays);
        onSelectedPreparationUpdated();

        if (mSelectedPreparation.getDay() != null) {
            loadDayTimeSlots();
        } else {
            autoAssignDay();
        }
        mWorkDaysDisposable = null;
    }

    private void workingDaysLoadingError(Throwable t) {
        LogUtils.debug(Contract.LOG_TAG, "Failed to get working days: " + t.getMessage());
        getViewState().hideDateTimeProgress();
        getViewState().workingDaysLoadingError(t);
        mWorkDaysDisposable = null;
    }

    private void onAddressWorkingDaysLoadingSuccess(List<DateTime> workingDays) {
        getViewState().hideDateTimeProgress();

        mWorkingDays = workingDays;

        getViewState().initCalendar(workingDays);
        onSelectedPreparationUpdated();

        if (mSelectedPreparation.getDay() != null) {
            if (isSingleServiceBooking()) {
                loadTimeSlotsByAddress();
            } else {
                loadTimeSlotsGradual();
            }

        } else {
            autoAssignDay();
        }
        mWorkDaysDisposable = null;
    }

    private void onAddressWorkingDaysLoadingError(Throwable t) {
        LogUtils.debug(Contract.LOG_TAG, "Failed to get working days: " + t.getMessage());
        getViewState().hideDateTimeProgress();
        getViewState().workingDaysLoadingError(t);
        mWorkDaysDisposable = null;
    }

    private void processServiceClick(PreparedServicesAdapterItem adapterItem) {
        mSelectedPreparation = adapterItem.getPreparation();
        mCurrentColor = mSelectedPreparation.getColorId();
        getViewState().onNewColor(mCurrentColor);
        getViewState().setSelectedPreparation(adapterItem.getPreparation()
                .getService().getId());
        getViewState().hideTimeSlotLayout(mSelectedPreparation.getDay() == null);
        getViewState().refreshSelectedServiceInfo(mSelectedPreparation.getService(),
                mSelectedPreparation.getDiscount());
        getViewState().setSelectedDay(adapterItem.getPreparation().getDay());
        onSelectedPreparationUpdated();
    }

    private void processServiceClickStaffPriority() {
        refreshStaffList();
        if (mSelectedPreparation.getStaff() != null) {
            initVisibleDay();
            loadStaffWorkDays();
        } else {
            getViewState().initCalendar(mWorkingDays);
            onSelectedPreparationUpdated();
        }
    }

    private void processServiceClickDatePriority() {
        loadAddressWorkDays();
    }

    private void processStaffClick(StaffAdapterItem adapterItem) {
        mSelectedPreparation.setStaff(adapterItem.getStaff());
        getViewState().setSelectedStaffItem(adapterItem.getStaff().getId());
        getViewState().refreshSelectedStaffInfo(adapterItem.getStaff());
        onSelectedPreparationUpdated();
    }

    private void processStaffClickStaffPriority() {
        getViewState().hideTimeSlotLayout(mSelectedPreparation.getDay() == null);
        loadStaffWorkDays();
    }

    private void processStaffClickDatePriority() {
    }

    private void processDayClick(DateTime day) {
        mSelectedPreparation.setDay(day);
    }

    private void processDayClickStaffPriority() {
        onSelectedPreparationUpdated();
        loadDayTimeSlots();
    }

    private void processDayClickDatePriority() {
        if (isSingleServiceBooking()) {
            loadTimeSlotsByAddress();
        } else {
            loadTimeSlotsGradual();
        }
    }

    private void processTimeSlotClick(TimeSlotAdapterItem adapterItem) {
        mSelectedPreparation.setTimeSlot(new TimeSlot(adapterItem.getDateTime()));
        mSelectedPreparation.setDiscount(adapterItem.getDiscount());
        getViewState().refreshSelectedServiceInfo(mSelectedPreparation.getService(),
                mSelectedPreparation.getDiscount());
        getViewState().setSelectedTimeSlotItem(adapterItem.getDateTime());
        onSelectedPreparationUpdated();
    }

    private void processTimeSlotClickStaffPriority(TimeSlotAdapterItem adapterItem) {
    }

    private void processTimeSlotClickDatePriority(TimeSlotAdapterItem adapterItem) {
        if (isSingleServiceBooking()) {
            refreshStaffList();
        } else {

            List<BeautyTimeSlot> beautyTimeSlots = new ArrayList<>();
            for (BeautyGradualTimeSlot gradualTimeSlot : mGradualTimeSlots) {
                if (adapterItem.getDateTime().toString(BookingUtil.TIME_SLOT_PATTERN)
                        .equals(gradualTimeSlot.getDate())) {
                    beautyTimeSlots.clear();
                    beautyTimeSlots.addAll(gradualTimeSlot.getTimeSlots());
                }
            }

            for (Preparation preparation : getAllPreparations()) {
                for (BeautyTimeSlot beautyTimeSlot : beautyTimeSlots) {
                    if (beautyTimeSlot.getServiceId().equals(preparation.getService().getId())) {

                        String pattern = BookingUtil.TIME_SLOT_PATTERN;
                        DateTime slot = DateTime.parse(beautyTimeSlot.getDate(),
                                DateTimeFormat.forPattern(pattern));

                        preparation.setDay(slot);

                        TimeSlot timeSlot = new TimeSlot(slot);
                        timeSlot.setBeautyTimeSlot(beautyTimeSlot);

                        preparation.setTimeSlot(timeSlot);
                        preparation.setDiscount(getDiscount(beautyTimeSlot));
                    }
                }
            }

            refreshStaffList();
        }
    }

    private void autoAssignDay() {
        DateTime autoAssignedDay = getAutoAssignedDay();
        if (autoAssignedDay != null) {
            getViewState().setSelectedDay(autoAssignedDay);
        }
    }

    @Nullable
    private DateTime getAutoAssignedDay() {

        DateTime defaultDayToSelect = getDefaultDayToSelect();

        for (DateTime dayTime : mWorkingDays) {

            boolean sameDay = TimeUtil.isSameDay(dayTime, defaultDayToSelect);

            boolean isAfter = dayTime.isAfter(defaultDayToSelect);

            boolean sameMonth = TimeUtil.isSameMonth(dayTime, defaultDayToSelect);

            if ((sameDay) || (isAfter && sameMonth)) {
                return dayTime;
            }
        }
        return null;
    }

    private DateTime getDefaultDayToSelect() {

        if (getAllPreparationsWithSelectedDay().isEmpty()) {

            return DateTime.now();

        } else {

            int lastIndex = getAllPreparationsWithSelectedDay().size() - 1;

            Preparation lastPrepWithDay =
                    getAllPreparationsWithSelectedDay().get(lastIndex);

            return lastPrepWithDay.getDay();
        }
    }

    private List<Preparation> getAllPreparations() {
        return mBookingStatus.getBeautyStatus().getPreparations();
    }

    private List<Preparation> getAllFullPreparations() {

        List<Preparation> fullPreparations = new ArrayList<>();

        for (Preparation p : getAllPreparations()) {
            if (p.isFull()) {
                fullPreparations.add(p);
            }
        }

        return fullPreparations;
    }

    private List<Preparation> getAllPreparationsWithSelectedDay() {

        List<Preparation> preparations = new ArrayList<>();

        for (Preparation p : getAllPreparations()) {
            if (p.getDay() != null) {
                preparations.add(p);
            }
        }

        return preparations;
    }

    private boolean isSingleServiceBooking() {
        return getAllPreparations().size() == 1;
    }

    private boolean isMultiServiceBooking() {
        return getAllPreparations().size() > 1;
    }

    private List<RRuleUtil.Rule> mapToRules(List<WorkingDay> workingDays) {
        List<RRuleUtil.Rule> rules = new ArrayList<>();
        for (WorkingDay w : workingDays) {
            rules.add(new RRuleUtil.Rule(w.getRule(), w.isExclusion()));
        }
        return rules;
    }

    private List<DateTime> mapToDateTimes(List<RRuleUtil.Day> workingDays) {
        List<DateTime> dateTimes = new ArrayList<>();
        for (RRuleUtil.Day d : workingDays) {
            dateTimes.add(d.getDateTime());
        }
        return dateTimes;
    }

    private void initVisibleDay() {
        if (mSelectedPreparation.getDay() != null) {
            getViewState().setVisibleDay(mSelectedPreparation.getDay());
        }
    }

    @Nullable
    private BeautyDiscount getDiscount(BeautyTimeSlot timeSlot) {
        if (timeSlot.getDiscounts() == null || timeSlot.getDiscounts().isEmpty()) {
            return null;
        }
        BeautyDiscount biggestDiscount = timeSlot.getDiscounts().get(0);
        for (BeautyDiscount discount : timeSlot.getDiscounts()) {
            if (biggestDiscount.getPriceValue() > discount.getPriceValue()) {
                biggestDiscount = discount;
            }
        }
        return biggestDiscount;
    }
}
