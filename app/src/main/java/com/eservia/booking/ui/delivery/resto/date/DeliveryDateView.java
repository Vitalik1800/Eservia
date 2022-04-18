package com.eservia.booking.ui.delivery.resto.date;

import android.util.Pair;

import androidx.annotation.Nullable;

import com.eservia.booking.common.view.LoadingView;

import org.joda.time.DateTime;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface DeliveryDateView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bindBookingTime(Pair<Integer, Integer> bookingTime);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showPageLoadingError();

    @StateStrategyType(value = SkipStrategy.class)
    void openVisitTimePicker(int hourOfDay, int minute);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setWorkDays(List<DateTime> workDays);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setCollapseCalendarTitle();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setExpandCalendarTitle();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setCalendarVisibleMonthCount(int visibleMonthCount);

    @StateStrategyType(value = SkipStrategy.class)
    void showInvalidSelectedTimeError();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void refreshAcceptState(boolean isActive);

    @StateStrategyType(value = SkipStrategy.class)
    void openThankYouScreen();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bindMinDeliveryTime(int minutes);

    @StateStrategyType(value = SkipStrategy.class)
    void showCalendarLayout();

    @StateStrategyType(value = SkipStrategy.class)
    void hideCalendarLayout();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bindOrderInformation(String clientName, String phone, String city, String street,
                              String house, String apartment, @Nullable String doorPhoneCode,
                              @Nullable String comment);

    @StateStrategyType(value = SkipStrategy.class)
    void onCreateOrderFailed(Throwable throwable);
}
