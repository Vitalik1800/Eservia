package com.eservia.booking.ui.booking.beauty;

import com.eservia.booking.common.view.LoadingView;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface BookingBeautyView extends LoadingView {

    @StateStrategyType(value = SkipStrategy.class)
    void openServiceGroups();

    @StateStrategyType(value = SkipStrategy.class)
    void openBookingFragment();

    @StateStrategyType(value = SkipStrategy.class)
    void openBasketSortFragment();
}
