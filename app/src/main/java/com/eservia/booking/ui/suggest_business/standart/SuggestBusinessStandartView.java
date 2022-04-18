package com.eservia.booking.ui.suggest_business.standart;

import com.eservia.booking.common.view.LoadingView;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface SuggestBusinessStandartView extends LoadingView {

    @StateStrategyType(value = SkipStrategy.class)
    void onSendSuggestionSuccess();

    @StateStrategyType(value = SkipStrategy.class)
    void onSendSuggestionFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void showSuccess();
}
