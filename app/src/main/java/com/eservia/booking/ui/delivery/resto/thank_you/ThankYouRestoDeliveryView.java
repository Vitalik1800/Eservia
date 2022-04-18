package com.eservia.booking.ui.delivery.resto.thank_you;

import com.eservia.booking.common.view.BaseView;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface ThankYouRestoDeliveryView extends BaseView {

    @StateStrategyType(value = SkipStrategy.class)
    void closeView();
}
