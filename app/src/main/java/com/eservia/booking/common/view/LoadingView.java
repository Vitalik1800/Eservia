package com.eservia.booking.common.view;


import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;


public interface LoadingView extends BaseView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showProgress();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideProgress();
}
