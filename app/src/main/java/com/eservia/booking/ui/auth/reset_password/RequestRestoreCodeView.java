package com.eservia.booking.ui.auth.reset_password;

import com.eservia.booking.common.view.LoadingView;

import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = SkipStrategy.class)
public interface RequestRestoreCodeView extends LoadingView {

    void onSendSuccess();

    void onSendFailed(Throwable throwable);

    void onPhoneError(String error);

    void openResetPass();

    void hideKeyboard();
}
