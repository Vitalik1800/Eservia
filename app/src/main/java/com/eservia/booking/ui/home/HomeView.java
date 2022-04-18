package com.eservia.booking.ui.home;

import com.eservia.booking.common.view.BaseView;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationSettingsRequest;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface HomeView extends BaseView {

    @StateStrategyType(value = SkipStrategy.class)
    void showSuggestBusinessModal();

    @StateStrategyType(value = SkipStrategy.class)
    void shouldShowFineLocationRationale();

    @StateStrategyType(value = SkipStrategy.class)
    void requestFineLocationPermission();

    @StateStrategyType(value = SkipStrategy.class)
    void showFineLocationExplanationDialog();

    @StateStrategyType(value = SkipStrategy.class)
    void hideFineLocationExplanationDialog();

    @StateStrategyType(value = SkipStrategy.class)
    void checkLocationSettings(LocationSettingsRequest.Builder builder);

    @StateStrategyType(value = SkipStrategy.class)
    void showLocationSettingsResolutionDialog(ResolvableApiException resolvable);

    @StateStrategyType(value = SkipStrategy.class)
    void prepareExtra();

    @StateStrategyType(value = SkipStrategy.class)
    void showBookingsTab();

    @StateStrategyType(value = SkipStrategy.class)
    void showMarketingTab();
}
