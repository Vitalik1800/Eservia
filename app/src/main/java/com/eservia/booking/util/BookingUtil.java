package com.eservia.booking.util;

import android.content.Context;

import androidx.annotation.Nullable;

import com.eservia.booking.R;
import com.eservia.model.entity.Marketing;
import com.eservia.utils.StringUtil;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;

import java.text.DecimalFormat;
import java.util.Locale;

public class BookingUtil {

    public static final String TIME_SLOT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DAY_PATTERN = "dd.MM.yyyy";

    public static final int MAX_BOOKING_MONTH_COUNT = 4;

    public static final int MAX_SERVICES_FLAT_SLOT = 10;

    public static final long BOOKING_EXP_TIME = 10 * 60 * 1000;

    public static String duration(Context context, String duration) {
        String minutes = context.getResources().getString(R.string.minutes);
        return duration + " " + minutes;
    }

    public static String formattedDuration(Context context, int durationMinutes) {
        String hourStr = context.getResources().getString(R.string.hours_short);
        String minStr = context.getResources().getString(R.string.minutes);
        String formatted;
        if (durationMinutes > 60) {
            int hours = durationMinutes / 60;
            int minutes = durationMinutes % 60;
            if (minutes != 0) {
                formatted = String.format(Locale.US, "%s %s %s %s", hours, hourStr, minutes, minStr);
            } else {
                formatted = String.format(Locale.US, "%s %s", hours, hourStr);
            }
        } else {
            formatted = String.format(Locale.US, "%s %s", durationMinutes, minStr);
        }
        return formatted;
    }

    public static String formatTime(int hours, int minutes) {
        String hoursStr = hours < 10 ? "0" + hours : "" + hours;
        String minutesStr = minutes < 10 ? "0" + minutes : "" + minutes;
        return hoursStr + ":" + minutesStr;
    }

    public static String formatTimeStartEnd(int hoursStart, int minStart, int hoursEnd, int minEnd) {
        return formatTime(hoursStart, minStart) + "-" + formatTime(hoursEnd, minEnd);
    }

    public static boolean servicePriceIsEmpty(Float price) {
        return price == null || price.equals(0.0f);
    }

    public static boolean serviceDurationIsEmpty(Integer duration) {
        return duration != null && !duration.equals(0);
    }

    public static boolean serviceCurrencyIsEmpty(String currency) {
        return currency == null || currency.isEmpty();
    }

    public static String formatPrice(Float price) {
        return String.valueOf(Math.round(price));
    }

    public static Float getDiscountPrice(Marketing marketing, Float price) {
        Integer discountType = marketing.getDiscountTypeId();
        Float discount = marketing.getDiscount();
        if (discountType == null || discount == null) {
            return price;
        }
        if (discountType.equals(Marketing.DISCOUNT_FIXED)) {
            return price - discount;
        } else if (discountType.equals(Marketing.DISCOUNT_PERCENT)) {
            return price - (price / 100 * discount);
        } else {
            return price;
        }
    }

    public static String oldPriceText(Marketing marketing, @Nullable Float price, @Nullable String currency) {
        if (marketing.getDiscount() == null || BookingUtil.servicePriceIsEmpty(price)) {
            return "";
        }
        String priceStr = BookingUtil.formatPrice(price);
        return !StringUtil.isEmpty(currency) ? priceStr + " " + currency.toUpperCase() : priceStr;
    }

    public static String dayToDescription(Context context, DateTime day) {
        DateTime today = DateTime.now();
        DateTime tomorrow = today.plusDays(1);
        if (day.getYear() == today.getYear()
                && day.getDayOfYear() == today.getDayOfYear()) {
            return context.getResources().getString(R.string.today_bookings);
        } else if (day.getYear() == tomorrow.getYear()
                && day.getDayOfYear() == tomorrow.getDayOfYear()) {
            return context.getResources().getString(R.string.tomorrow_bookings);
        } else {
            return day.toString(DAY_PATTERN);
        }
    }

    public static String bookingServiceDescription(Context context, String service, Integer duration) {
        String min = context.getResources().getString(R.string.minutes);
        if (serviceDurationIsEmpty(duration)) {
            String durationStr = String.valueOf(duration);
            return String.format("%s, %s %s", service, durationStr, min);
        } else {
            return String.format("%s", service);
        }
    }

    public static DateTime serviceEndTime(DateTime startTime, Integer duration) {
        return startTime.plusMinutes(duration);
    }

    public static String formatRestoBookingTime(DateTime time) {
        String s1 = time.toString("yyyy-MM-dd");
        String s2 = time.toString("HH:mm:ss.SSSS");
        return s1 + "T" + s2;
    }

    public static DateTime restoBookingTimeFromFormatted(String formattedString) {
        String[] array = formattedString.split("T");
        DateTime day = DateTime.parse(array[0], DateTimeFormat.forPattern("yyyy-MM-dd"));
        DateTime time;
        try {
            time = DateTime.parse(array[1], DateTimeFormat.forPattern("HH:mm:ss.SSSS"));
        } catch (IllegalArgumentException e1) {
            try {
                time = DateTime.parse(array[1], DateTimeFormat.forPattern("HH:mm:ss"));
            } catch (IllegalArgumentException e2) {
                return DateTime.now();
            }
        }
        return day.withHourOfDay(time.getHourOfDay())
                .withMinuteOfHour(time.getMinuteOfHour())
                .withSecondOfMinute(time.getSecondOfMinute());
    }

    public static String formatRestoVisitDuration(Context context, String bookingStart,
                                                  String bookingEnd) {
        String message = context.getResources().getString(R.string.visit_duration);
        return String.format("%s: %s", message, visitDurationResto(bookingStart, bookingEnd));
    }

    public static String formatRestoDepartment(Context context, String department) {
        String message = context.getResources().getString(R.string.placement);
        return String.format("%s: %s", message, department);
    }

    public static int visitDurationResto(String bookingStart, String bookingEnd) {
        DateTime start = restoBookingTimeFromFormatted(bookingStart);
        DateTime end = restoBookingTimeFromFormatted(bookingEnd);
        return Minutes.minutesBetween(start, end).getMinutes();
    }

    public static String formatRestoPeopleCount(Context context, Long peopleCount) {
        String message = context.getResources().getString(R.string.number_of_persons);
        return String.format("%s: %s", message, peopleCount);
    }

    public static String formatRestoDeliveryPrice(Context context, String price) {
        String formattedPrice = new DecimalFormat("#.##").format(Double.valueOf(price));
        return String.format(Locale.US, "%s %s", formattedPrice,
                context.getString(R.string.uah).toUpperCase());
    }
}
