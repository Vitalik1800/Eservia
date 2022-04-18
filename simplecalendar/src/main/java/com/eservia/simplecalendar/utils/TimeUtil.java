package com.eservia.simplecalendar.utils;

import org.joda.time.DateTime;

public class TimeUtil {

    public static boolean isSameDay(DateTime first, DateTime second) {
        return first.getYear() == second.getYear() && first.getDayOfYear() == second.getDayOfYear();
    }
}
