package com.eservia.booking.ui.business_page.beauty.contacts;

import com.eservia.booking.common.view.LoadingView;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface BusinessPageBeautyContactsView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onAddressesLoadingSuccess(List<AddressAdapterItem> adapterItems);

    @StateStrategyType(value = SkipStrategy.class)
    void onAddressesLoadingFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void openMap(String title, Double lat, Double lng);

    @StateStrategyType(value = SkipStrategy.class)
    void openPhone(String number);
}
