package com.eservia.booking.ui.contacts;

import com.eservia.booking.common.view.LoadingView;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface ContactsView extends LoadingView {

    @StateStrategyType(value = SkipStrategy.class)
    void onFeedbackSendSuccess();

    @StateStrategyType(value = SkipStrategy.class)
    void onFeedbackSendFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void closeActivity();
}
