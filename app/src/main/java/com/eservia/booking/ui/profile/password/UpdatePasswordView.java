package com.eservia.booking.ui.profile.password;

import com.eservia.booking.common.view.LoadingView;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface UpdatePasswordView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onPasswordUpdated();

    @StateStrategyType(value = SkipStrategy.class)
    void onPasswordUpdateFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void closeActivity();
}
