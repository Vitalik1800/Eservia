package com.eservia.booking.ui.booking.resto.placement;

import android.util.Pair;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.model.booking_status.BookingStatus;
import com.eservia.booking.util.BookingUtil;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.RestoDepartment;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.booking_resto.services.booking.RestoBookingRequest;
import com.eservia.model.remote.rest.booking_resto.services.booking.RestoBookingResponse;

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
public class BookingRestoPlacementPresenter extends BasePresenter<BookingRestoPlacementView>
        implements DepartmentsAdapter.OnDepartmentClickListener {

    @Inject
    BookingStatus mBookingStatus;

    @Inject
    RestManager mRestManager;

    private Disposable mDisposable;

    private Address mAddress;

    private final List<RestoDepartment> mDepartments = new ArrayList<>();

    private final List<DepartmentAdapterItem> mDepartmentsItems = new ArrayList<>();

    private DepartmentAdapterItem mSelectedDepartmentItem;

    public BookingRestoPlacementPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mAddress = mBookingStatus.getRestoStatus().getAddress();
        Business mBusiness = mBookingStatus.getRestoStatus().getBusiness();
        refresh();
    }

    @Override
    public void onDepartmentClicked(DepartmentAdapterItem item, int position) {
        mSelectedDepartmentItem = item;
        getViewState().matchSelectedDepartment(item);
        getViewState().refreshAcceptState(canMakeBooking());
    }

    public void onAcceptClick() {
        if (paginationInProgress(mDisposable)) {
            return;
        }
        if (!canMakeBooking()) {
            return;
        }
        saveBookingParametersToState();
        postBooking();
    }

    private boolean canMakeBooking() {
        return mSelectedDepartmentItem != null;
    }

    private void saveBookingParametersToState() {
        mBookingStatus.getRestoStatus().setDepartment(mSelectedDepartmentItem != null
                ? mSelectedDepartmentItem.getDepartment() : null);
    }

    private void refresh() {
        mDepartments.clear();
        mDepartmentsItems.clear();
        mSelectedDepartmentItem = null;

        getViewState().showSelectedAddressAndTime(mAddress, hourMinBookingStart(), hourMinBookingEnd());
        getViewState().refreshAcceptState(canMakeBooking());

        if (mDepartments.isEmpty()) {
            loadDepartments();
        } else {
            onDepartmentsLoaded();
        }
    }

    private void initDepartments(List<RestoDepartment> departments) {
        mDepartments.clear();
        mDepartments.addAll(departments);
        mDepartmentsItems.clear();
        mDepartmentsItems.addAll(mapToAdapterItems(mDepartments));
        onDepartmentsLoaded();
    }

    private void onDepartmentsLoaded() {
        getViewState().setDepartments(mDepartmentsItems);
    }

    private void loadDepartments() {
        getViewState().showProgress();
        cancelPagination(mDisposable);
        Observable<List<RestoDepartment>> observable = mRestManager
                .getRestoDepartments((long) mAddress.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mDisposable = observable.subscribe(this::onDepartmentsLoaded,
                this::onDepartmentsLoadingFailed);
        addSubscription(mDisposable);
    }

    private void postBooking() {
        getViewState().showProgress();
        cancelPagination(mDisposable);
        Observable<RestoBookingResponse> observable = mRestManager
                .postRestoBooking(composeBookingRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        mDisposable = observable.subscribe(success -> onCreateBookingSuccess(),
                this::onCreateBookingFailed);
        addSubscription(mDisposable);
    }

    private void onDepartmentsLoaded(List<RestoDepartment> departments) {
        getViewState().hideProgress();
        initDepartments(departments);
        mDisposable = null;
    }

    private void onDepartmentsLoadingFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().showPageLoadingError();
        mDisposable = null;
    }

    private List<DepartmentAdapterItem> mapToAdapterItems(List<RestoDepartment> departments) {
        List<DepartmentAdapterItem> result = new ArrayList<>();
        for (RestoDepartment department : departments) {
            result.add(new DepartmentAdapterItem(department, DepartmentAdapterItem.State.UNSELECTED));
        }
        return result;
    }

    private Pair<Integer, Integer> hourMinBookingStart() {
        return mBookingStatus.getRestoStatus().getBookingHourMin();
    }

    private Pair<Integer, Integer> hourMinBookingEnd() {
        DateTime visitEndTime = bookingEndTime();
        return new Pair<>(visitEndTime.getHourOfDay(), visitEndTime.getMinuteOfHour());
    }

    private void onCreateBookingSuccess() {
        getViewState().hideProgress();
        getViewState().showThankYouFragment();
        removeCurrentBookingStatus();
        mDisposable = null;
    }

    private void onCreateBookingFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onCreateBookingFailed(throwable);
        mDisposable = null;
    }

    private void removeCurrentBookingStatus() {
        mBookingStatus.removeRestoStatus();
    }

    private RestoBookingRequest composeBookingRequest() {
        RestoBookingRequest request = new RestoBookingRequest();
        request.setAddressId((long) mBookingStatus.getRestoStatus().getAddress().getId());
        request.setDepartmentId(mBookingStatus.getRestoStatus().getDepartment().getId());
        request.setPeopleCount((long) mBookingStatus.getRestoStatus().getNumberOfPersons());
        request.setBookingDateTime(BookingUtil.formatRestoBookingTime(bookingDateTime()));
        request.setBookingEndTime(BookingUtil.formatRestoBookingTime(bookingEndTime()));
        return request;
    }

    private DateTime bookingDateTime() {
        int hour = mBookingStatus.getRestoStatus().getBookingHourMin().first;
        int minute = mBookingStatus.getRestoStatus().getBookingHourMin().second;
        DateTime day = mBookingStatus.getRestoStatus().getBookingDay();
        return day.withHourOfDay(hour).withMinuteOfHour(minute);
    }

    private DateTime bookingEndTime() {
        int visitDuration = mBookingStatus.getRestoStatus().getVisitDuration();
        return bookingDateTime().plusMinutes(visitDuration);
    }
}
