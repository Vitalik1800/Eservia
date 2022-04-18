package com.eservia.booking.ui.suggest_business.thank_you;

import com.eservia.booking.common.view.LoadingView;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface SuggestBusinessThankYouView extends LoadingView {

    @StateStrategyType(value = SkipStrategy.class)
    void closeView();
}
