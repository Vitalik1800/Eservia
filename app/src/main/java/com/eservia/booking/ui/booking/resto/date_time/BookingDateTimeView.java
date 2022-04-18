package com.eservia.booking.ui.booking.resto.date_time;

import android.util.Pair;

import androidx.annotation.Nullable;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.RestoBookingWorkSchedule;

import org.joda.time.DateTime;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface BookingDateTimeView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bindNumberOfPersons(int numberOfPersons);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bindVisitDuration(Integer visitDuration);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bindBookingTime(Pair<Integer, Integer> bookingTime);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showSelectedAddress(Address address);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showPageLoadingError();

    @StateStrategyType(value = SkipStrategy.class)
    void openVisitDurationPicker(List<Pair<Integer, String>> visitDurations, int selectedItemPos);

    @StateStrategyType(value = SkipStrategy.class)
    void openVisitTimePicker(int hourOfDay, int minute);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setWorkDays(List<DateTime> workDays);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setCollapseCalendarTitle();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setExpandCalendarTitle();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showSelectedDayWorkTime(@Nullable RestoBookingWorkSchedule workTime);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setCalendarVisibleMonthCount(int visibleMonthCount);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setMaxNumberOfPerson(int maxNumberOfPerson);

    @StateStrategyType(value = SkipStrategy.class)
    void showInvalidSelectedTimeError();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void refreshAcceptState(boolean isActive);

    @StateStrategyType(value = SkipStrategy.class)
    void openPlacementScreen();
}
