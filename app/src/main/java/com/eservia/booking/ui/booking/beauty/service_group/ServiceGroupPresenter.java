package com.eservia.booking.ui.booking.beauty.service_group;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.common.view.OnPaginationListener;
import com.eservia.booking.model.booking_status.BookingStatus;
import com.eservia.model.entity.BeautyServiceGroup;
import com.eservia.model.entity.Business;
import com.eservia.model.interactors.business.BusinessInteractor;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.business_beauty.services.service_group.BeautyServiceGroupResponse;
import com.eservia.model.remote.rest.request.KeyList;
import com.eservia.utils.SortUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class ServiceGroupPresenter extends BasePresenter<ServiceGroupView> implements
        OnPaginationListener,
        ServiceGroupAdapter.OnItemClickListener {

    private final List<BeautyServiceGroup> mServiceGroups = new ArrayList<>();

    @Inject
    RestManager mRestManager;

    @Inject
    BusinessInteractor mBusinessInteractor;

    @Inject
    BookingStatus mBookingStatus;

    private Disposable mPaginationDisposable;

    private boolean mIsAllLoaded = false;

    public ServiceGroupPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showSelectedAddress(mBookingStatus.getBeautyStatus().getAddress());
        getViewState().refreshBasketState(mBookingStatus.getBeautyStatus().getPreparations().size());
        refresh();
    }

    @Override
    public void loadMore() {
        if (!paginationInProgress() && !mIsAllLoaded) {
            makePagination();
        }
    }

    @Override
    public void onServiceGroupClick(BeautyServiceGroup serviceGroup) {
        mBookingStatus.getBeautyStatus().setServiceGroup(serviceGroup);
        getViewState().showServiceFragment(false);
    }

    public void onSearchFocus() {
        mBookingStatus.getBeautyStatus().setServiceGroup(null);
        getViewState().showServiceFragment(true);
    }

    public void onResume() {
        getViewState().refreshBasketState(mBookingStatus.getBeautyStatus().getPreparations().size());
    }

    public void refresh() {
        if (!paginationInProgress()) {
            mServiceGroups.clear();
            mIsAllLoaded = false;
            makePagination();
        }
    }

    private void makePagination() {
        getViewState().showProgress();
        cancelPagination();
        Integer addressId = mBookingStatus.getBeautyStatus().getAddress().getId();
        Integer businessId = mBookingStatus.getBeautyStatus().getBusiness().getId();
        Observable<BeautyServiceGroupResponse> observable = mBusinessInteractor
                .getServiceGroups(getBusiness().getSector(), null, null, null,
                        new KeyList().add(addressId), null, businessId, SortUtil.createdAtDesc(),
                        null, PART, mServiceGroups.size() / PART + 1, null)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        mPaginationDisposable = observable.subscribe(this::onServiceGroupsLoadingSuccess,
                this::onServiceGroupsLoadingFailed);
        addSubscription(mPaginationDisposable);
    }

    private boolean paginationInProgress() {
        return mPaginationDisposable != null && !mPaginationDisposable.isDisposed();
    }

    private void cancelPagination() {
        if (!paginationInProgress()) {
            if (mPaginationDisposable != null) {
                mPaginationDisposable.dispose();
            }
        }
    }

    private void onServiceGroupsLoadingSuccess(BeautyServiceGroupResponse response) {
        mServiceGroups.addAll(response.getData());
        getViewState().hideProgress();
        getViewState().onServiceGroupsLoadingSuccess(mServiceGroups);
        mPaginationDisposable = null;
        if (mServiceGroups.size() == response.getMeta().getTotal()) {
            mIsAllLoaded = true;
        }
    }

    private void onServiceGroupsLoadingFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onServiceGroupsLoadingFailed(throwable);
        mPaginationDisposable = null;
    }

    private Business getBusiness() {
        return mBookingStatus.getBeautyStatus().getBusiness();
    }
}
