package com.eservia.booking.ui.auth.reset_password;

import com.eservia.booking.common.view.LoadingView;

import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = SkipStrategy.class)
public interface ResetPasswordView extends LoadingView {

    void onResetSuccess();

    void onResetFailed(Throwable throwable);

    void onPasswordError(String error);

    void onPasswordConfirmError(String error);

    void onCodeError(String error);

    void close();

    void hideKeyboard();
}
