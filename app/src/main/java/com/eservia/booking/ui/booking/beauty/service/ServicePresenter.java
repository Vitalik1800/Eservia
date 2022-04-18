package com.eservia.booking.ui.booking.beauty.service;

import android.content.Context;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.common.view.OnPaginationListener;
import com.eservia.booking.model.booking_status.BookingStatus;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.booking.util.BookingUtil;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.BeautyService;
import com.eservia.model.entity.BeautyServiceGroup;
import com.eservia.model.entity.Business;
import com.eservia.model.interactors.business.BusinessInteractor;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.business_beauty.services.service.BeautyServiceResponse;
import com.eservia.utils.SortUtil;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class ServicePresenter extends BasePresenter<ServiceView> implements
        OnPaginationListener,
        ServiceAdapter.OnItemClickListener,
        ExceededMaxSelectedServicesSheetDialog.Listener {

    private final List<BeautyService> mServices = new ArrayList<>();

    @Inject
    RestManager mRestManager;

    @Inject
    BusinessInteractor mBusinessInteractor;

    @Inject
    BookingStatus mBookingStatus;

    @Inject
    Context mContext;

    private Disposable mPaginationDisposable;

    private boolean mIsAllLoaded = false;

    @Nullable
    private String mQuery;

    private Address mAddress;

    @Nullable
    private Integer mServiceGroupId;

    public ServicePresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mAddress = mBookingStatus.getBeautyStatus().getAddress();
        BeautyServiceGroup serviceGroup = mBookingStatus.getBeautyStatus().getServiceGroup();
        if (serviceGroup != null) {
            mServiceGroupId = serviceGroup.getId();
        }
        getViewState().showSelectedAddress(mAddress);
        getViewState().requiredArgs();
    }

    @Override
    public void onServiceItemClick(ServiceAdapterItem adapterItem) {
        if (!adapterItem.isSelected() && getAllPreparations().size() >= BookingUtil.MAX_SERVICES_FLAT_SLOT) {
            getViewState().showErrorExceedDialog();
            return;
        }

        getViewState().setSelected(!adapterItem.isSelected(),
                adapterItem.getService().getId());
        if (!removeService(adapterItem.getService())) {
            Preparation preparation = new Preparation(mContext);
            preparation.setService(adapterItem.getService());
            getAllPreparations().add(preparation);
        }
        getViewState().refreshBasketState(getAllPreparations().size());
        getViewState().refreshAcceptState(canGoToBooking());
    }

    @Override
    public void onExceededMaxSelectedServicesDoneClick() {
        getViewState().hideErrorExceedDialog();
    }

    @Override
    public void loadMore() {
        if (paginationInProgress() && !mIsAllLoaded) {
            makePagination();
        }
    }

    void onArgs(boolean startFromSearch) {
        if (startFromSearch) {
            getViewState().requestFocus();
        }
        getViewState().refreshBasketState(getAllPreparations().size());
        getViewState().refreshAcceptState(canGoToBooking());
        refresh();
    }

    void onQuery(String query) {
        mQuery = query;
        refresh();
    }

    public void refresh() {
        if (paginationInProgress()) {
            mServices.clear();
            mIsAllLoaded = false;
            makePagination();
        }
    }

    private void makePagination() {
        if (mServices.size() == 0) {
            getViewState().showProgress();
        }
        cancelPagination();

        Integer businessId = mBookingStatus.getBeautyStatus().getBusiness().getId();

        Observable<BeautyServiceResponse> observable = mBusinessInteractor
                .getAddressServices(getBusiness().getSector(), mAddress.getId(), mQuery, businessId,
                        mServiceGroupId, SortUtil.createdAtDesc(), BeautyService.STATUS_ACTIVE, null,
                        PART, mServices.size() / PART + 1, null)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

        mPaginationDisposable = observable.subscribe(this::onServicesLoadingSuccess,
                this::onServicesLoadingFailed);
        addSubscription(mPaginationDisposable);
    }

    private boolean paginationInProgress() {
        return mPaginationDisposable == null || mPaginationDisposable.isDisposed();
    }

    private void cancelPagination() {
        if (paginationInProgress()) {
            if (mPaginationDisposable != null) {
                mPaginationDisposable.dispose();
            }
        }
    }

    private void onServicesLoadingSuccess(BeautyServiceResponse response) {
        boolean firstPart = mServices.size() == 0;
        mServices.addAll(response.getData());
        getViewState().hideProgress();
        getViewState().onServicesLoadingSuccess(mapToAdapterItems(response.getData()), firstPart);
        mPaginationDisposable = null;
        if (mServices.size() == response.getMeta().getTotal()) {
            mIsAllLoaded = true;
        }
    }

    private void onServicesLoadingFailed(Throwable throwable) {
        getViewState().hideProgress();
        getViewState().onServicesLoadingFailed(throwable);
        mPaginationDisposable = null;
    }

    public void onAcceptClick() {
        if (!canGoToBooking()) {
            return;
        }
        if (preparationsSize() == 1) {
            getViewState().showBookingOneServiceFragment();
        } else {
            getViewState().showBasketSortFragment();
        }
    }

    private int preparationsSize() {
        return getAllPreparations().size();
    }

    private List<Preparation> getAllPreparations() {
        return mBookingStatus.getBeautyStatus().getPreparations();
    }

    private boolean canGoToBooking() {
        return preparationsSize() > 0;
    }

    private List<ServiceAdapterItem> mapToAdapterItems(List<BeautyService> services) {
        List<ServiceAdapterItem> adapterItems = new ArrayList<>();
        for (BeautyService service : services) {
            boolean isSelected = false;
            for (Preparation preparation : getAllPreparations()) {
                if (preparation.getService().getId().equals(service.getId())) {
                    isSelected = true;
                    break;
                }
            }
            adapterItems.add(new ServiceAdapterItem(service, isSelected));
        }
        return adapterItems;
    }

    private boolean removeService(BeautyService serviceToRemove) {
        ListIterator<Preparation> iterator = getAllPreparations().listIterator();
        while (iterator.hasNext()) {
            Preparation preparation = iterator.next();
            if (preparation.getService().getId().equals(serviceToRemove.getId())) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private Business getBusiness() {
        return mBookingStatus.getBeautyStatus().getBusiness();
    }
}
