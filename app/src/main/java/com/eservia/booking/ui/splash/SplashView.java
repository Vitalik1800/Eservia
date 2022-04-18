package com.eservia.booking.ui.splash;

import com.eservia.booking.common.view.BaseView;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface SplashView extends BaseView {

    @StateStrategyType(value = SkipStrategy.class)
    void openHomeView();

    @StateStrategyType(value = SkipStrategy.class)
    void openLoginView();

    @StateStrategyType(value = SkipStrategy.class)
    void openIntro();

    @StateStrategyType(value = SkipStrategy.class)
    void showError(String message);
}
