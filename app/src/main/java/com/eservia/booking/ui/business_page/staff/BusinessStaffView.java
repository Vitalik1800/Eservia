package com.eservia.booking.ui.business_page.staff;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.BeautyStaff;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface BusinessStaffView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onStaffLoadingSuccess(List<BeautyStaff> staffList);

    @StateStrategyType(value = SkipStrategy.class)
    void onStaffLoadingFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void showStaffDetailBeautyPage(BeautyStaff staff);
}
