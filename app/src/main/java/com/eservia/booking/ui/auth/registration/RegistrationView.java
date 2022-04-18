package com.eservia.booking.ui.auth.registration;

import com.eservia.booking.common.view.LoadingView;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface RegistrationView extends LoadingView {

    @StateStrategyType(value = SkipStrategy.class)
    void onRegistrationSuccess(String phone);

    @StateStrategyType(value = SkipStrategy.class)
    void onRegistrationFailed(Throwable throwable);

    void nameError(String nameError);

    void loginError(String loginError);

    void confirmPasswordError(String error);

    void passwordError(String passwordError);

    void registrationInProgress(boolean b);

    void hideKeyboard();
}
