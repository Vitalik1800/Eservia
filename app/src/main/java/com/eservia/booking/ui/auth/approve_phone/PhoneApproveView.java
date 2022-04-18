package com.eservia.booking.ui.auth.approve_phone;


import com.eservia.booking.common.view.BaseView;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface PhoneApproveView extends BaseView {

    @StateStrategyType(value = SkipStrategy.class)
    void onSmsSendSuccess();

    @StateStrategyType(value = SkipStrategy.class)
    void onSmsSendFailed(Throwable throwable);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void approveStarted();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void approveFinished();

    @StateStrategyType(value = SkipStrategy.class)
    void onApproveFail(Throwable throwable);

    void updateTime(int secondsLeft);

    void enableTimer(boolean enabled);

    void setPhoneNumber();

    void requestSent(boolean sent);

    @StateStrategyType(SkipStrategy.class)
    void openHomeActivity();

    @StateStrategyType(value = SkipStrategy.class)
    void initExtra();
}
