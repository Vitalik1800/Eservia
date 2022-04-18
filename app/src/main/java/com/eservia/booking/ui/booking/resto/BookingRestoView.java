package com.eservia.booking.ui.booking.resto;

import com.eservia.booking.common.view.LoadingView;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface BookingRestoView extends LoadingView {

    @StateStrategyType(value = SkipStrategy.class)
    void openDateTimeSelectFragment();
}
