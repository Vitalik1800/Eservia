package com.eservia.booking.ui.home.bookings.active_bookings;

import androidx.fragment.app.Fragment;

import com.eservia.booking.common.view.LoadingView;

import java.util.Map;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface ActiveBookingsView extends LoadingView, MvpView {

    @StateStrategyType(value = SkipStrategy.class)
    void showArchiveBookings();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setFragments(Map<String, Fragment> fragments);
}
