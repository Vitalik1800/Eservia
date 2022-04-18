package com.eservia.booking.ui.booking.resto.thank_you;

import com.eservia.booking.common.view.LoadingView;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface ThankYouRestoView extends LoadingView {

    @StateStrategyType(value = SkipStrategy.class)
    void closeView();
}
