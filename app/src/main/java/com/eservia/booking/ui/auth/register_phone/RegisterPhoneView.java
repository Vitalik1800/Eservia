package com.eservia.booking.ui.auth.register_phone;

import com.eservia.booking.common.view.LoadingView;

import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = SkipStrategy.class)
public interface RegisterPhoneView extends LoadingView {

    void onSuccessRegisterProvider(String phone);

    void onErrorRegisterProvider(Throwable throwable);

    void onPhoneError(String error);

    void hideKeyboard();

    @StateStrategyType(value = SkipStrategy.class)
    void initExtra();
}
