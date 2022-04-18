package com.eservia.booking.ui.booking.beauty.basket;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.model.booking_status.BookingStatus;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.booking.util.BookingUtil;
import com.eservia.booking.util.ServerErrorUtil;
import com.eservia.booking.util.TimeUtil;
import com.eservia.model.entity.BeautyBooking;
import com.eservia.model.entity.Business;
import com.eservia.model.interactors.booking.BookingInteractor;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.booking_beauty.codes.BookingBeautyServerResponseState;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CreateBookingBeautyRequest;
import com.eservia.model.remote.rest.booking_beauty.services.booking.CreateBookingBeautyResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class BasketPresenter extends BasePresenter<BasketView> implements
        FilledPreparationsAdapter.OnFilledPreparationsClickListener,
        UnFilledPreparationsAdapter.OnUnFilledPreparationsClickListener,
        WarningUnfilledSheetDialog.Listener {

    private final List<Preparation> mFilledPreparations = new ArrayList<>();

    private final List<Preparation> mUnFilledPreparations = new ArrayList<>();

    private final List<FilledPreparationListItem> mFilledPreparationItems = new ArrayList<>();

    private final List<UnFilledPreparationItem> mUnFilledPreparationItems = new ArrayList<>();

    @Inject
    RestManager mRestManager;

    @Inject
    BookingInteractor mBookingInteractor;

    @Inject
    BookingStatus mBookingStatus;

    private Disposable mBookingDisposable;

    private boolean mIsEditMode = false;

    public BasketPresenter() {
        App.getAppComponent().inject(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showSelectedAddress(mBookingStatus.getBeautyStatus().getAddress());

        mFilledPreparations.addAll(filledPreparations());
        mUnFilledPreparations.addAll(notFilledPreparations());

        mFilledPreparationItems.addAll(mapToFilledItems(mFilledPreparations, mIsEditMode));
        mUnFilledPreparationItems.addAll(mapToNotFilledItems(mUnFilledPreparations));

        getViewState().onFilledPreparations(mFilledPreparationItems);
        getViewState().onUnFilledPreparations(mUnFilledPreparationItems);
    }

    @Override
    public void onFilledPreparationClick(FilledPreparationItem preparationItem) {
        getViewState().goBack();
    }

    @Override
    public void onUnFilledPreparationClick(UnFilledPreparationItem preparationItem) {
        getViewState().goBack();
    }

    @Override
    public void onDeleteFilledPreparationClick(FilledPreparationItem preparationItem) {
    }

    @Override
    public void onEditFilledPreparationClick(FilledPreparationItem preparationItem) {
    }

    @Override
    public void onWarningUnfilledFinishClick() {
        getViewState().hideWarningUnfilledDialog();
        performBooking();
    }

    @Override
    public void onWarningUnfilledFillClick() {
        getViewState().hideWarningUnfilledDialog();
        getViewState().goBack();
    }

    void onResume() {
        bindComment();
    }

    void onCommentChanged(String comment) {
        for (Preparation p : mFilledPreparations) {
            p.setComment(comment);
        }
        updateBookingStatusPreparations();
    }

    public void onAcceptClick() {
        if (paginationInProgress(mBookingDisposable)) {
            return;
        }

        if (mIsEditMode) {
            return;
        }

        if (mFilledPreparations.isEmpty()) {
            return;
        }

        if (!mUnFilledPreparations.isEmpty()) {
            getViewState().showWarningUnfilledDialog(mFilledPreparations.size(),
                    mUnFilledPreparations.size());
            return;
        }

        performBooking();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void editModeEnabled(boolean enabled) {
        if (!enabled || filledPreparations().size() > 0) {

            mIsEditMode = enabled;

            getViewState().onEditMode(mIsEditMode);
            getViewState().onFilledPreparations(mapToFilledItems(mFilledPreparations, mIsEditMode));

            if (!mIsEditMode) {
                invalidateBookingStatusPreparations();
            }
        }
    }

    private void performBooking() {
        if (mFilledPreparations.size() == 1) {
            makeBooking();
        } else {
            makeMassiveBooking();
        }
    }

    private void makeBooking() {
        getViewState().showProgress();

        Preparation preparation = mFilledPreparations.get(0);

        CreateBookingBeautyRequest request = composeBookingRequest(preparation);

        Integer businessId = getBusiness().getId();

        mBookingDisposable = mRestManager
                .registerCustomer(businessId)
                .flatMap(success -> mBookingInteractor.createBooking(getBusiness().getSector(), request))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> onCreateBookingSuccess(response, preparation),
                        error -> onCreateBookingFailed(error, preparation));
        addSubscription(mBookingDisposable);
    }

    private void makeMassiveBooking() {
        getViewState().showProgress();

        List<CreateBookingBeautyRequest> requestList =
                composeMassiveBookingRequest(mFilledPreparations);

        Integer businessId = getBusiness().getId();

        mBookingDisposable = mRestManager
                .registerCustomer(businessId)
                .flatMap(success -> Observable.fromIterable(requestList))
                .flatMap(request -> mBookingInteractor.createBooking(getBusiness().getSector(), request)
                        .map(CreateBookingResult::new)
                        .onErrorReturn(CreateBookingResult::new))
                .toList()
                .toObservable()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> onCreateMassiveBookingSuccess(response, requestList),
                        error -> onCreateMassiveBookingFailed(error, requestList));
        addSubscription(mBookingDisposable);
    }

    private void onCreateBookingSuccess(CreateBookingBeautyResponse response, Preparation p) {
        getViewState().hideProgress();
        getViewState().onCreateBookingSuccess(p);
        clearSuccessPreparation(p);
        getViewState().showThankYouFragment();
        removeCurrentBookingStatus();
        mBookingDisposable = null;
    }

    private void onCreateBookingFailed(Throwable throwable, Preparation p) {
        getViewState().hideProgress();
        if (isAlreadyBookedError(throwable)) {
            getViewState().onCreateBookingFailedAlreadyBooked(throwable, p);
        } else {
            getViewState().onCreateBookingFailed(throwable, p);
        }
        mBookingDisposable = null;
    }

    private void onCreateMassiveBookingSuccess(List<CreateBookingResult> results,
                                               List<CreateBookingBeautyRequest> requestList) {

        getViewState().hideProgress();

        if (isAllBookingResultsSuccess(results)) {
            getViewState().onCreateMassiveBookingSuccess(requestList);
            clearSuccessPreparations(results);
            getViewState().showThankYouFragment();
            removeCurrentBookingStatus();
        }
        // TODO: not all success

        mBookingDisposable = null;
    }

    private void onCreateMassiveBookingFailed(Throwable throwable,
                                              List<CreateBookingBeautyRequest> requestList) {
        getViewState().hideProgress();
        getViewState().onCreateMassiveBookingFailed(throwable, requestList);
        mBookingDisposable = null;
    }

    private CreateBookingBeautyRequest composeBookingRequest(Preparation p) {

        CreateBookingBeautyRequest request = new CreateBookingBeautyRequest();

        request.setBusinessId(getBusiness().getId());
        request.setAddressId(mBookingStatus.getBeautyStatus().getAddress().getId());

        request.setServiceId(p.getService().getId());
        request.setStaffId(p.getStaff().getId());
        request.setDate(p.getTimeSlot().getTime().toString(BookingUtil.TIME_SLOT_PATTERN));
        request.setComment(p.getComment());
        request.setPromotionId(p.getDiscount() != null ? p.getDiscount().getPromotionId() : null);

        return request;
    }

    private List<CreateBookingBeautyRequest> composeMassiveBookingRequest(
            List<Preparation> preparations) {
        List<CreateBookingBeautyRequest> requests = new ArrayList<>();
        for (Preparation p : preparations) {
            requests.add(composeBookingRequest(p));
        }
        return requests;
    }

    private boolean isAllBookingResultsSuccess(List<CreateBookingResult> results) {
        for (CreateBookingResult result : results) {
            if (result.getError() != null) {
                return false;
            }
        }
        return true;
    }

    private void clearSuccessPreparation(Preparation p) {
        mFilledPreparations.remove(p);
        updateBookingStatusPreparations();
    }

    private void clearSuccessPreparations(List<CreateBookingResult> results) {
        for (CreateBookingResult result : results) {
            if (result.getResponse() != null) {
                Preparation preparation =
                        getFilledPreparationByBooking(result.getResponse().getData());
                if (preparation != null) {
                    mFilledPreparations.remove(preparation);
                }
            }
        }
        updateBookingStatusPreparations();
    }

    private void removeCurrentBookingStatus() {
        mBookingStatus.removeBeautyStatus();
    }

    @Nullable
    private Preparation getFilledPreparationByBooking(BeautyBooking b) {
        for (Preparation p : mFilledPreparations) {

            String pattern = BookingUtil.TIME_SLOT_PATTERN;
            DateTime bookingTime = DateTime.parse(b.getDate(), DateTimeFormat.forPattern(pattern));

            boolean sameDate = p.getTimeSlot().getTime().isEqual(bookingTime);
            boolean sameStaff = p.getStaff().getId().equals(b.getStaffId());
            boolean sameService = p.getService().getId().equals(b.getServiceId());

            if (sameStaff && sameService && sameDate) {
                return p;
            }
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void invalidateBookingStatusPreparations() {
        updateBookingStatusPreparations();
        onStatusPreparationsUpdated();
    }

    private void updateBookingStatusPreparations() {
        mBookingStatus.getBeautyStatus().getPreparations().clear();
        mBookingStatus.getBeautyStatus().getPreparations().addAll(mFilledPreparations);
        mBookingStatus.getBeautyStatus().getPreparations().addAll(mUnFilledPreparations);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onStatusPreparationsUpdated() {
        getViewState().onFilledPreparations(mapToFilledItems(mFilledPreparations, mIsEditMode));
    }

    private boolean isAlreadyBookedError(Throwable error) {
        return ServerErrorUtil.getErrorCode(error)
                == BookingBeautyServerResponseState.UNPROCESSABLE_ENTITY
                || ServerErrorUtil.getErrorCode(error)
                == BookingBeautyServerResponseState.CONFLICT;
    }

    private void bindComment() {
        String comment = "";
        for (Preparation p : mFilledPreparations) {
            if (p.getComment() != null) {
                comment = p.getComment();
                break;
            }
        }
        getViewState().setComment(comment);
    }

    private List<Preparation> filledPreparations() {
        List<Preparation> allPreparations =
                mBookingStatus.getBeautyStatus().getPreparations();
        List<Preparation> result = new ArrayList<>();
        for (Preparation p : allPreparations) {
            if (p.isFull()) {
                result.add(p);
            }
        }
        return result;
    }

    private List<Preparation> notFilledPreparations() {
        List<Preparation> allPreparations =
                mBookingStatus.getBeautyStatus().getPreparations();
        List<Preparation> result = new ArrayList<>();
        for (Preparation p : allPreparations) {
            if (!p.isFull()) {
                result.add(p);
            }
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<FilledPreparationListItem> mapToFilledItems(
            List<Preparation> preparations, boolean editMode) {
        List<FilledPreparationListItem> result = new ArrayList<>();

        Collections.sort(preparations, Comparator.comparing(p -> p.getTimeSlot().getTime()));

        DateTime day = null;
        for (Preparation p : preparations) {
            if (day == null) {
                day = p.getDay();
                result.add(new FilledPreparationHeaderItem(day));
                result.add(new FilledPreparationItem(p, editMode));
            } else {
                DateTime preparationDay = p.getDay();
                if (TimeUtil.isSameDay(preparationDay, day)) {
                    result.add(new FilledPreparationItem(p, editMode));
                } else {
                    day = preparationDay;
                    result.add(new FilledPreparationHeaderItem(day));
                    result.add(new FilledPreparationItem(p, editMode));
                }
            }
        }
        return result;
    }

    private Business getBusiness() {
        return mBookingStatus.getBeautyStatus().getBusiness();
    }

    private List<UnFilledPreparationItem> mapToNotFilledItems(
            List<Preparation> preparations) {
        List<UnFilledPreparationItem> result = new ArrayList<>();
        for (Preparation p : preparations) {
            result.add(new UnFilledPreparationItem(p));
        }
        return result;
    }
}
