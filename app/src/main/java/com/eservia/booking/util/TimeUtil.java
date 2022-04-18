package com.eservia.booking.util;

import android.annotation.SuppressLint;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static boolean isSameDay(DateTime first, DateTime second) {
        return first.getYear() == second.getYear() && first.getDayOfYear() == second.getDayOfYear();
    }

    public static boolean isSameMonth(DateTime first, DateTime second) {
        return first.getYear() == second.getYear() && first.getMonthOfYear() == second.getMonthOfYear();
    }

    public static DateTime dateTimeFromMillisOfDay(Integer millisOfDay) {
        return millisOfDay != null ? DateTime.now().withMillisOfDay(millisOfDay % 86400000) : null;
    }

    /**
     * Transform Calendar to ISO 8601 string.
     **/
    @SuppressLint("SimpleDateFormat")
    public static String fromCalendarToIso(final Calendar calendar) {
        Date date = calendar.getTime();
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

}
