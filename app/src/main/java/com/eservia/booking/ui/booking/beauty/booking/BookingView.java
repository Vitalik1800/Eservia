package com.eservia.booking.ui.booking.beauty.booking;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.eservia.booking.common.view.LoadingView;
import com.eservia.booking.model.booking_status.beauty.Preparation;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.BeautyDiscount;
import com.eservia.model.entity.BeautyService;
import com.eservia.model.entity.BeautyStaff;

import org.joda.time.DateTime;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface BookingView extends LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onPreparedServicesLoadingSuccess(List<Preparation> preparations);

    @StateStrategyType(value = SkipStrategy.class)
    void onPreparedServicesLoadingFailed(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void showBasketFragment();

    @StateStrategyType(value = SkipStrategy.class)
    void selectFirstPreparedServiceItem();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setSelectedPreparation(Integer serviceId);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setAllPreparationsSelected();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void revalidatePreparationsFinishedStates();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void selectPreparationWithId(String id);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void refreshSelectedServiceInfo(BeautyService service, @Nullable BeautyDiscount discount);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onNewColor(@ColorInt int colorId);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showSelectedAddress(Address address);

    @StateStrategyType(value = AddToEndStrategy.class)
    void onStaffLoadingSuccess(List<StaffAdapterItem> items, boolean replaceAll,
                               @ColorInt int currentColor);

    @StateStrategyType(value = SkipStrategy.class)
    void onStaffLoadingFailed(Throwable throwable);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showStaffProgress();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideStaffProgress();

    @StateStrategyType(value = SkipStrategy.class)
    void selectFirstStaffItem();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setSelectedStaffItem(Integer staffIf);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void refreshSelectedStaffInfo(@Nullable BeautyStaff staff);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showDateTimeProgress();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideDateTimeProgress();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void workingDaysLoadingError(Throwable t);

    @StateStrategyType(value = SkipStrategy.class)
    void setSelectedDay(DateTime selectedDay);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void initCalendar(List<DateTime> days);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setVisibleDay(DateTime day);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showTimeSlotsProgress();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideTimeSlotsProgress();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onTimeSlotsLoadingSuccess(List<TimeSlotAdapterItem> timeSlotAdapterItems);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void onTimeSlotsLoadingError(Throwable throwable);

    @StateStrategyType(value = SkipStrategy.class)
    void selectFirstTimeSlotItem();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setSelectedTimeSlotItem(DateTime time);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideTimeSlotLayout(boolean gone);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void revalidateAcceptLayout(boolean currentPreparationFinished, boolean allPreparationsFinished);

    @StateStrategyType(value = AddToEndStrategy.class)
    void performStaffPriority();

    @StateStrategyType(value = AddToEndStrategy.class)
    void performDatePriority(boolean multiServices);

    @StateStrategyType(value = AddToEndStrategy.class)
    void onAutoSelectedStaffLoadingSuccess(List<AutoSelectedStaffAdapterItem> items);

    @StateStrategyType(value = SkipStrategy.class)
    void onAutoSelectedStaffLoadingFailed(Throwable throwable);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showAutoSelectedStaffProgress();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void hideAutoSelectedStaffProgress();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setCollapseCalendarTitle();

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setExpandCalendarTitle();
}
