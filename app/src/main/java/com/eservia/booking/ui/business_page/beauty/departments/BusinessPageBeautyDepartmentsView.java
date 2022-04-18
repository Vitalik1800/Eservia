package com.eservia.booking.ui.business_page.beauty.departments;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface BusinessPageBeautyDepartmentsView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onAddressesLoadingSuccess(List<Address> addressList);

    @StateStrategyType(value = SkipStrategy.class)
    void onAddressesLoadingFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void openBooking(Business business, Address address);
}
