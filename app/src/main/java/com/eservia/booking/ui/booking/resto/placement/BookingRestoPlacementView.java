package com.eservia.booking.ui.booking.resto.placement;

import android.util.Pair;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.Address;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface BookingRestoPlacementView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showSelectedAddressAndTime(Address address, Pair<Integer, Integer> hourMinStart,
                                    Pair<Integer, Integer> hourMinEnd);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showPageLoadingError();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setDepartments(List<DepartmentAdapterItem> departments);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void refreshAcceptState(boolean isActive);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void matchSelectedDepartment(DepartmentAdapterItem item);

    @StateStrategyType(value = SkipStrategy.class)
    void showThankYouFragment();

    @StateStrategyType(value = SkipStrategy.class)
    void onCreateBookingFailed(Throwable throwable);
}
