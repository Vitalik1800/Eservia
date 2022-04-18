package com.eservia.booking.ui.suggest_business;

import com.eservia.booking.common.view.LoadingView;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface SuggestBusinessView extends LoadingView {

    @StateStrategyType(value = SkipStrategy.class)
    void openSuggestBusinessFragmentStandard();

    @StateStrategyType(value = SkipStrategy.class)
    void openSuggestBusinessFragmentModal();

    @StateStrategyType(value = SkipStrategy.class)
    void requiredExtra();
}
