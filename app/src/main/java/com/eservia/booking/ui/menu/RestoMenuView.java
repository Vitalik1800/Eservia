package com.eservia.booking.ui.menu;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.Business;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface RestoMenuView extends LoadingView {

    @StateStrategyType(value = SkipStrategy.class)
    void openFirstMenuFragment(Long categoryId);

    @StateStrategyType(value = SkipStrategy.class)
    void goBack();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void refreshBasketState(int orderItemsCount);

    @StateStrategyType(value = SkipStrategy.class)
    void openBasket(Business mBusiness);
}
