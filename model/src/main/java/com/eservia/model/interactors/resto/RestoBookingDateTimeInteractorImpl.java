package com.eservia.model.interactors.resto;

import com.eservia.model.entity.RestoBookingSettings;
import com.eservia.model.entity.RestoBookingWorkSchedule;
import com.eservia.model.remote.rest.RestManager;
import com.eservia.model.remote.rest.booking_resto.services.booking_settings.BookingSettingsResponse;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import io.reactivex.Observable;

public class RestoBookingDateTimeInteractorImpl implements RestoBookingDateTimeInteractor {

    private final RestManager restManager;

    public RestoBookingDateTimeInteractorImpl(RestManager restManager) {
        this.restManager = restManager;
    }

    @Override
    public Observable<RestoBookingSettings> getRestoBookingSettings(Long addressId) {
        return restManager.getRestoBookingSettings(addressId)
                .map(BookingSettingsResponse::getData);
    }

    @Override
    public Observable<List<DateTime>> computeWorkDays(RestoBookingSettings settings) {
        return Observable.fromCallable(() -> getWorkDays(settings));
    }

    @Override
    public Observable<Boolean> validateBookingTime(DateTime time, RestoBookingSettings settings) {
        return Observable.fromCallable(() -> isBookingTimeValid(time, settings));
    }

    private Boolean isBookingTimeValid(DateTime time, RestoBookingSettings settings) {
        if (isLessThanAvailableTimeForBooking(time, settings)) {
            return false;
        }
        if (isTimeValidForWorkSchedule(time, settings.getWorkSchedule())) {
            return true;
        }
        RestoBookingWorkSchedule prevDayWorkSchedule
                = workTimeForDay(time.minusDays(1).getDayOfWeek(), settings.getWorkSchedule());
        if (prevDayWorkSchedule == null) {
            return false;
        }
        DateTime closeTimeForPrevDay = closeDateTimeForPrevDay(time, prevDayWorkSchedule);
        return isAfterOrEqualInMin(closeTimeForPrevDay, time);
    }

    private DateTime closeDateTimeForPrevDay(DateTime time, RestoBookingWorkSchedule schedule) {
        DateTime prevDay = new DateTime(
                time.minusDays(1).getYear(),
                time.minusDays(1).getMonthOfYear(),
                time.minusDays(1).getDayOfMonth(),
                0,
                0);
        return prevDay.plusMillis((int) schedule.getEndTime().longValue());
    }

    private boolean isTimeValidForWorkSchedule(DateTime time, List<RestoBookingWorkSchedule> workSchedules) {
        RestoBookingWorkSchedule workTime = workTimeForDay(time.getDayOfWeek(), workSchedules);
        if (workTime != null) {
            return !(time.getMillisOfDay() < workTime.getStartTime())
                    && !(time.getMillisOfDay() > workTime.getEndTime());
        }
        return false;
    }

    private boolean isLessThanAvailableTimeForBooking(DateTime time, RestoBookingSettings settings) {
        return time.getMillis() <
                DateTime.now().getMillis() + settings.getAvailableTimeForCreateBooking();
    }

    private List<DateTime> getWorkDays(RestoBookingSettings settings) {
        List<DateTime> workDays = new ArrayList<>();
        DateTime day = DateTime.now();
        DateTime maxDayForBooking = maxBookingDate(day, settings);
        while (day.isBefore(maxDayForBooking) || isSameDay(day, maxDayForBooking)) {
            if (isWorkDay(day, settings.getWorkSchedule())) {
                workDays.add(day);
            }
            day = day.plusDays(1);
        }
        return workDays;
    }

    private boolean isWorkDay(DateTime day, List<RestoBookingWorkSchedule> schedule) {
        RestoBookingWorkSchedule workDay = workTimeForDay(day.getDayOfWeek(), schedule);
        if (workDay != null) {
            return true;
        }
        DateTime prevDay = day.minusDays(1);
        RestoBookingWorkSchedule prevWorkDay = workTimeForDay(prevDay.getDayOfWeek(), schedule);
        if (prevWorkDay == null) {
            return false;
        }
        DateTime openTime = new DateTime(prevWorkDay.getStartTime());
        DateTime closeTime = new DateTime(prevWorkDay.getEndTime());
        int offset = openTime.getZone().getOffset(openTime);
        return openTime.minusMillis(offset).getDayOfWeek()
                != closeTime.minusMillis(offset).getDayOfWeek();
    }

    @Nullable
    private RestoBookingWorkSchedule workTimeForDay(int dayOfWeek,
                                                    List<RestoBookingWorkSchedule> workSchedules) {
        for (RestoBookingWorkSchedule schedule : workSchedules) {
            if (schedule.getDay().equals((long) dayOfWeek)) {
                return schedule;
            }
        }
        return null;
    }

    private DateTime maxBookingDate(DateTime from, RestoBookingSettings settings) {
        return from.plusDays((int) settings.getMaxAmountOfDaysAdvanceForBooking().longValue());
    }

    private boolean isSameDay(DateTime first, DateTime second) {
        return first.getYear() == second.getYear() && first.getDayOfYear() == second.getDayOfYear();
    }

    private boolean isAfterOrEqualInMin(DateTime first, DateTime second) {
        if (first.getYear() == second.getYear()
                && first.getDayOfYear() == second.getDayOfYear()
                && first.getMinuteOfDay() == second.getMinuteOfDay()) {
            return true;
        }
        return first.isAfter(second);
    }
}
