package com.eservia.booking.ui.business_page.map;

import android.content.Intent;

import com.eservia.booking.common.view.LoadingView;
import com.google.android.gms.maps.model.LatLng;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface BusinessMapView extends LoadingView {

    @StateStrategyType(value = SkipStrategy.class)
    void initExtra();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setMarker(LatLng destination, String title);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void animateCamera(LatLng destination);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void moveCamera(LatLng destination);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setRoutButtonVisible(boolean visible);

    @StateStrategyType(value = SkipStrategy.class)
    void startRouteIntent(Intent intent);

    @StateStrategyType(value = SkipStrategy.class)
    void finishActivity();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setMyLocationEnabled(boolean enabled);
}
