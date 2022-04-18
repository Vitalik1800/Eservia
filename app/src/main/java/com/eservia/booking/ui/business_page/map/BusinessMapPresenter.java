package com.eservia.booking.ui.business_page.map;

import android.content.Context;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.util.LocationUtil;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.utils.IntentUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import javax.inject.Inject;

import moxy.InjectViewState;

@InjectViewState
public class BusinessMapPresenter extends BasePresenter<BusinessMapView> implements
        GoogleMap.OnMarkerClickListener {

    @Inject
    RestManager mRestManager;

    @Inject
    Context mContext;

    private boolean mIsExtraReady = false;

    private boolean mIsMapReady = false;

    private String mTitle;

    private double mLat;

    private double mLng;

    public BusinessMapPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().initExtra();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        getViewState().animateCamera(new LatLng(mLat, mLng));
        marker.showInfoWindow();
        return true;
    }

    void onExtra(String title, double lat, double lng) {
        mTitle = title;
        mLat = lat;
        mLng = lng;
        mIsExtraReady = true;
        setUpBusinessMarker();
    }

    void onMapReady() {
        mIsMapReady = true;
        setUpBusinessMarker();
    }

    void onRouteClick() {
        getViewState().startRouteIntent(IntentUtil.googleMapRouteIntent(mLat, mLng));
    }

    void onBackClick() {
        getViewState().finishActivity();
    }

    private void setUpBusinessMarker() {
        if (!mIsExtraReady || !mIsMapReady) {
            return;
        }
        LatLng destination = new LatLng(mLat, mLng);
        getViewState().setMarker(destination, mTitle);
        getViewState().moveCamera(destination);
        getViewState().setRoutButtonVisible(true);
        if (LocationUtil.isFineLocationPermissionGranted(mContext)) {
            getViewState().setMyLocationEnabled(true);
        }
    }
}
