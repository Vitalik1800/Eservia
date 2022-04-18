package com.eservia.booking.ui.home.bookings.archive_bookings;

import com.eservia.booking.common.view.LoadingView;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface ArchiveBookingsView extends LoadingView, MvpView {
}
