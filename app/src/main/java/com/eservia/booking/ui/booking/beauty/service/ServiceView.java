package com.eservia.booking.ui.booking.beauty.service;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.Address;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface ServiceView extends LoadingView {

    @StateStrategyType(value = AddToEndStrategy.class)
    void onServicesLoadingSuccess(List<ServiceAdapterItem> serviceList, boolean replaceAll);

    @StateStrategyType(value = SkipStrategy.class)
    void onServicesLoadingFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void showBookingOneServiceFragment();

    @StateStrategyType(value = SkipStrategy.class)
    void showBasketSortFragment();

    @StateStrategyType(value = SkipStrategy.class)
    void showBookingFewServicesFragment();

    @StateStrategyType(value = SkipStrategy.class)
    void requiredArgs();

    @StateStrategyType(value = SkipStrategy.class)
    void requestFocus();

    @StateStrategyType(value = AddToEndStrategy.class)
    void setSelected(boolean selected, Integer serviceId);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showSelectedAddress(Address address);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void refreshAcceptState(boolean isActive);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void refreshBasketState(int preparationsCount);

    @StateStrategyType(value = SkipStrategy.class)
    void showErrorExceedDialog();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideErrorExceedDialog();
}
