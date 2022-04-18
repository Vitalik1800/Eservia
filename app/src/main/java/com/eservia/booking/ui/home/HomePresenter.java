package com.eservia.booking.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import androidx.annotation.Nullable;

import com.eservia.booking.App;
import com.eservia.booking.common.presenter.BasePresenter;
import com.eservia.booking.model.event_bus.EventFineLocationNotGranted;
import com.eservia.booking.model.event_bus.EventFineLocationResult;
import com.eservia.booking.util.BusinessSuggestionUtil;
import com.eservia.booking.util.LocationUtil;
import com.eservia.booking.util.RateUsUtil;
import com.eservia.model.prefs.Profile;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.socket.WebSocketManager;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import moxy.InjectViewState;

@InjectViewState
public class HomePresenter extends BasePresenter<HomeView> implements
        LocationUtil.LocationCallbackImplListener,
        LocationAccessExplanationSheetDialog.Listener {

    private final static int LOCATION_REQUEST_INTERVAL = 5000;

    @Inject
    RestManager mRestManager;

    @Inject
    WebSocketManager mWebSocketManager;

    @Inject
    Context mContext;

    private FusedLocationProviderClient mFusedLocationClient;

    private LocationRequest mLocationRequest;

    private LocationUtil.LocationCallbackImpl mLocationCallback;

    private boolean mRequestingLocationUpdates = false;

    public HomePresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().prepareExtra();

        mWebSocketManager.connect();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);

        mLocationCallback = new LocationUtil.LocationCallbackImpl(this);

        if (LocationUtil.isFineLocationPermissionGranted(mContext)) {
            onFineLocationPermissionGranted();
        } else {
            getViewState().shouldShowFineLocationRationale();
        }

        onLaunch();
    }

    @Override
    public void onDestroy() {
        mWebSocketManager.disconnect();
        super.onDestroy();
    }

    void onPause() {
        stopLocationUpdates();
    }

    void onResume() {
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    void onStartTabBookings() {
        getViewState().showBookingsTab();
    }

    void onStartTabMarketing() {
        getViewState().showMarketingTab();
    }

    void onShouldShowFineLocationRationale() {
        //getViewState().requestFineLocationPermission();
        getViewState().showFineLocationExplanationDialog();
    }

    void onShouldNotShowFineLocationRationale() {
        getViewState().showFineLocationExplanationDialog();
    }

    @Override
    public void onLocationExplanationDialogOkClick() {
        getViewState().hideFineLocationExplanationDialog();
    }

    @Override
    public void onLocationExplanationDialogDismissed() {
        getViewState().requestFineLocationPermission();
    }

    void onFineLocationPermissionGranted() {
        initLastLocation();
    }

    void onFineLocationPermissionNotGranted() {
        // permission denied, boo!
        EventBus.getDefault().post(new EventFineLocationNotGranted());
    }

    void onLocationSettingsSatisfied() {
        onLocationSettingsResolved();
    }

    void onLocationSettingsNotSatisfied(@Nullable ResolvableApiException resolvable) {
        if (resolvable != null) {
            // Location settings are not satisfied, but this can be fixed
            // by showing the user a dialog
            getViewState().showLocationSettingsResolutionDialog(resolvable);
        }
    }

    void onLocationSettingsResolved() {
        mRequestingLocationUpdates = true;
        startLocationUpdates();
    }

    void onLocationSettingsNotResolved() {
        mRequestingLocationUpdates = false;
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        if (locationResult == null) {
            return;
        }
        onNewCurrentLocation(locationResult.getLastLocation());
    }

    @Override
    public void onLocationAvailability(LocationAvailability locationAvailability) {
    }

    @SuppressLint("MissingPermission")
    private void initLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    onNewCurrentLocation(location);
                    createLocationRequest();
                    checkLocationSettings();
                });
    }

    @SuppressLint("RestrictedApi")
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(LOCATION_REQUEST_INTERVAL);
        mLocationRequest.setFastestInterval(LOCATION_REQUEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void checkLocationSettings() {
        if (mLocationRequest == null) {
            return;
        }
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        getViewState().checkLocationSettings(builder);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    private void onNewCurrentLocation(Location location) {

        if (location != null) {
            Profile.setUserLastLocationLat((float) location.getLatitude());
            Profile.setUserLastLocationLng((float) location.getLongitude());
            EventBus.getDefault().post(new EventFineLocationResult());
        }
    }

    private void onLaunch() {
        RateUsUtil.appLaunched();
        if (BusinessSuggestionUtil.shouldShowBusinessSuggestionModal()) {
            getViewState().showSuggestBusinessModal();
            BusinessSuggestionUtil.onBusinessSuggestionModalShowed();
        }
    }
}
