package com.eservia.booking.ui.auth.login;

import com.eservia.booking.common.view.LoadingView;

import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface LoginView extends LoadingView {

    @StateStrategyType(value = SkipStrategy.class)
    void onLoginSuccess();

    @StateStrategyType(value = SkipStrategy.class)
    void onLoginFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void openPhoneApprove(String phone);

    @StateStrategyType(value = SkipStrategy.class)
    void onLoginWithProviderSuccess();

    @StateStrategyType(value = SkipStrategy.class)
    void onLoginWithProviderFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void providerUserNotFound(String provider, String token);
}
