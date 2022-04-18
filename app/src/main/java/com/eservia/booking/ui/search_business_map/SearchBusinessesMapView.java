package com.eservia.booking.ui.search_business_map;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface SearchBusinessesMapView extends LoadingView {

    @StateStrategyType(value = AddToEndStrategy.class)
    void onNewMarkers(List<AddressMarker> addressMarkers);

    @StateStrategyType(value = SkipStrategy.class)
    void onNewMarkersLoadingFailed(Throwable throwable);

    @StateStrategyType(value = AddToEndStrategy.class)
    void clearMarkers();

    @StateStrategyType(value = AddToEndStrategy.class)
    void setSelectedAddressMarker(AddressMarker addressMarker);

    @StateStrategyType(value = SkipStrategy.class)
    void finishActivity();

    @StateStrategyType(value = AddToEndStrategy.class)
    void showAddressInfo(AddressMarker addressMarker);

    @StateStrategyType(value = AddToEndStrategy.class)
    void hideAddressInfo();

    @StateStrategyType(value = SkipStrategy.class)
    void showBookingBeautyActivity(Business business, Address address);

    @StateStrategyType(value = SkipStrategy.class)
    void showBusinessBeautyActivity(Business business);

    @StateStrategyType(value = AddToEndStrategy.class)
    void setKyivCameraPosition();

    @StateStrategyType(value = AddToEndStrategy.class)
    void moveCameraToMarker(AddressMarker addressMarker);

    @StateStrategyType(value = AddToEndStrategy.class)
    void setMyLocationEnabled(boolean enabled);
}
