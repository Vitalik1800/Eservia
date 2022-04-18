package com.eservia.booking.ui.search_business_map;

import android.content.Context;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.util.LocationUtil;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessSector;
import com.eservia.model.interactors.business.BusinessSearchableHelper;
import com.eservia.model.remote.rest.RestManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class SearchBusinessesMapPresenter extends BasePresenter<SearchBusinessesMapView> implements
        GoogleMap.OnMarkerClickListener {

    @Inject
    RestManager mRestManager;

    @Inject
    Context mContext;

    private Disposable mBusinessesPaginationDisposable;

    private final List<Business> mBusinesses = new ArrayList<>();

    private final List<AddressMarker> mAddressMarkers = new ArrayList<>();

    private BusinessSector mSector = new BusinessSector();

    private boolean mIsAllBusinessesLoaded = false;

    private AddressMarker mSelectedAddressMarker;

    public SearchBusinessesMapPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        SearchBusinessesMapExtra mExtra = EventBus.getDefault().getStickyEvent(SearchBusinessesMapExtra.class);
        if (mExtra != null) {
            mBusinesses.addAll(mExtra.businesses);
            mSector = mExtra.sector;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        setSelectedMarker(marker);
        return true;
    }

    void onMapReady() {
        getViewState().setKyivCameraPosition();
        getViewState().onNewMarkers(toAddressMarkers(mBusinesses, mSector));
        if (LocationUtil.isFineLocationPermissionGranted(mContext)) {
            getViewState().setMyLocationEnabled(true);
        }
        refreshBusinesses();
    }

    void onBackClicked() {
        getViewState().finishActivity();
    }

    void onBusinessReserveClick() {
        if (mSelectedAddressMarker == null) {
            return;
        }
        if (mSelectedAddressMarker.getBusiness().getAddresses().isEmpty()) {
            return;
        }
        getViewState().showBookingBeautyActivity(mSelectedAddressMarker.getBusiness(),
                mSelectedAddressMarker.getAddress());
    }

    void onBusinessInfoClick() {
        if (mSelectedAddressMarker == null) {
            return;
        }
        getViewState().showBusinessBeautyActivity(mSelectedAddressMarker.getBusiness());
    }

    private void loadMoreBusinesses() {
        if (!paginationInProgress(mBusinessesPaginationDisposable) && !mIsAllBusinessesLoaded) {
            makeBusinessesPagination();
        }
    }

    private void refreshBusinesses() {
        if (paginationInProgress(mBusinessesPaginationDisposable)) {
            cancelPagination(mBusinessesPaginationDisposable);
        }
        mBusinesses.clear();
        mIsAllBusinessesLoaded = false;
        makeBusinessesPagination();
    }

    private void makeBusinessesPagination() {
        cancelPagination(mBusinessesPaginationDisposable);
        Observable<List<Business>> observable = mRestManager
                .getBusinessesWithAddresses(null,
                        null,
                        String.valueOf(mSector.getId()),
                        String.valueOf(Business.STATUS_ACTIVE),
                        null,
                        null,
                        null,
                        Business.WITHOUT_TRASHED,
                        null,
                        PART,
                        mBusinesses.size() / PART + 1,
                        BusinessSearchableHelper.getSearchableQuery())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        mBusinessesPaginationDisposable = observable.subscribe(this::onBusinessesLoadingSuccess,
                this::onBusinessesLoadingFailed);
        addSubscription(mBusinessesPaginationDisposable);
    }

    private void onBusinessesLoadingSuccess(List<Business> businesses) {
        mBusinesses.addAll(businesses);
        getViewState().onNewMarkers(toAddressMarkers(businesses, mSector));
        mBusinessesPaginationDisposable = null;
        if (businesses.size() != PART) {
            mIsAllBusinessesLoaded = true;
        } else {
            loadMoreBusinesses();
        }
    }

    private void onBusinessesLoadingFailed(Throwable throwable) {
        getViewState().onNewMarkersLoadingFailed(throwable);
        mBusinessesPaginationDisposable = null;
    }

    private List<AddressMarker> toAddressMarkers(List<Business> businesses, BusinessSector sector) {
        List<AddressMarker> addressMarkers = new ArrayList<>();
        for (Business business : businesses) {
            business.setSector(sector);
            for (Address address : business.getAddresses()) {
                addressMarkers.add(new AddressMarker(business, address));
            }
        }
        return addressMarkers;
    }

    private void setSelectedMarker(Marker marker) {
        mSelectedAddressMarker = (AddressMarker) marker.getTag();
        getViewState().setSelectedAddressMarker(mSelectedAddressMarker);
        getViewState().showAddressInfo(mSelectedAddressMarker);
        getViewState().moveCameraToMarker(mSelectedAddressMarker);
    }
}
