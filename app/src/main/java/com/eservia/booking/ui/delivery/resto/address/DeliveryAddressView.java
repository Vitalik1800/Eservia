package com.eservia.booking.ui.delivery.resto.address;

import com.eservia.booking.common.view.LoadingView;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface DeliveryAddressView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setItems(List<DeliveryAddressAdapterItem> items);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showPageLoadingError();

    @StateStrategyType(value = SkipStrategy.class)
    void closePage();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setCityTitles();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setStreetTitles();
}
