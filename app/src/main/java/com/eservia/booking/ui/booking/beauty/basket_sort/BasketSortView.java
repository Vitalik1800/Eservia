package com.eservia.booking.ui.booking.beauty.basket_sort;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.Address;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface BasketSortView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showSelectedAddress(Address address);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onPreparations(List<BasketSortAdapterItem> items);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onEditMode(boolean enabled);

    @StateStrategyType(value = SkipStrategy.class)
    void showBookingFragment();

    @StateStrategyType(value = SkipStrategy.class)
    void goBack();
}
