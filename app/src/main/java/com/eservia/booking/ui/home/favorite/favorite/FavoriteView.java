package com.eservia.booking.ui.home.favorite.favorite;

import com.eservia.booking.common.view.LoadingView;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface FavoriteView extends LoadingView {

    @StateStrategyType(value = SkipStrategy.class)
    void showBusinessSuggestion();
}
