package com.eservia.booking.ui.booking.beauty.service_group;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.BeautyServiceGroup;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface ServiceGroupView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onServiceGroupsLoadingSuccess(List<BeautyServiceGroup> serviceGroupList);

    @StateStrategyType(value = SkipStrategy.class)
    void onServiceGroupsLoadingFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void showServiceFragment(boolean startFromSearch);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showSelectedAddress(Address address);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void refreshBasketState(int preparationsCount);
}
