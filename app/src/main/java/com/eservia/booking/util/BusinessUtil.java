package com.eservia.booking.util;


import android.content.Context;

import androidx.annotation.Nullable;

import com.eservia.booking.R;
import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;
import com.eservia.model.entity.MarketingWorkSchedule;
import com.eservia.utils.StringUtil;
import com.google.android.gms.maps.model.LatLng;

import org.joda.time.DateTime;

import java.util.List;
import java.util.Locale;

public class BusinessUtil {

    public static final String COMMENT_DATE_FORMAT = "dd.MM.yyyy  HH:mm";

    public static final String FORMAT_HOUR_MINUTES = "HH:mm";

    public static final String FORMAT_DAY_MONTH_YEAR = "dd.MM.yyyy";

    public static String getFullAddress(String street, String number) {
        return street + ", " + number;
    }

    public static String getFullAddress(@Nullable String city, @Nullable String street,
                                        @Nullable String number) {
        StringBuilder sb = new StringBuilder();
        String comma = ", ";
        if (city != null) {
            sb.append(city);
            sb.append(comma);
        }
        if (street != null) {
            sb.append(street);
            sb.append(comma);
        }
        if (number != null) {
            sb.append(number);
        }
        return sb.toString();
    }

    public static String getFormattedAddress(Address address) {
        String city = address.getCity();
        String street = address.getStreet();
        String number = address.getNumber();
        return getFullAddress(city, street, number);
    }

    public static String getStaffFullName(String firstName, String lastName) {
        StringBuilder builder = new StringBuilder();
        if (!StringUtil.isEmpty(firstName)) {
            builder.append(firstName);
        }
        if (!StringUtil.isEmpty(lastName)) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(lastName);
        }
        return builder.toString();
    }

    public static boolean shouldShowBookingButton(Business business) {
        return business != null
                && business.getAddresses() != null
                && business.getAddresses().size() == 1;
    }

    public static boolean mayShowBookingButton(String sector) {
        return SectorUtil.isBeauty(sector) || SectorUtil.isHealth(sector);
    }

    public static String formatEventStartEndTime(@Nullable DateTime start, @Nullable DateTime end) {
        StringBuilder sb = new StringBuilder();
        String separator = " - ";
        if (start != null) {
            sb.append(start.toString(BusinessUtil.FORMAT_DAY_MONTH_YEAR));

            if (end != null) {
                sb.append(separator);
                sb.append(end.toString(BusinessUtil.FORMAT_DAY_MONTH_YEAR));
            }
        }
        return sb.toString();
    }

    public static String formatHoursPeriod(Context context,
                                           @Nullable DateTime start, @Nullable DateTime end) {

        String from = context.getResources().getString(R.string.from);
        String to = context.getResources().getString(R.string.to);
        String separator = " ";

        String strStart = start != null ? separator + from + separator +
                start.toString(FORMAT_HOUR_MINUTES) : null;
        String strEnd = end != null ? separator + to + separator +
                end.toString(FORMAT_HOUR_MINUTES) : null;

        StringBuilder sb = new StringBuilder();

        if (strStart != null) {
            sb.append(strStart);
        }

        if (strEnd != null) {
            sb.append(strEnd);
        }

        return sb.toString();
    }

    public static String formatWeekDaysHoursPeriod(Context context, int day,
                                                   @Nullable DateTime start, @Nullable DateTime end) {
        switch (day) {
            case MarketingWorkSchedule.TYPE_MONDAY: {

                String every = context.getResources().getString(R.string.every_monday);
                return every + formatHoursPeriod(context, start, end);
            }
            case MarketingWorkSchedule.TYPE_TUESDAY: {

                String every = context.getResources().getString(R.string.every_tuesday);
                return every + formatHoursPeriod(context, start, end);
            }
            case MarketingWorkSchedule.TYPE_WEDNESDAY: {

                String every = context.getResources().getString(R.string.every_wednesday);
                return every + formatHoursPeriod(context, start, end);
            }
            case MarketingWorkSchedule.TYPE_THURSDAY: {

                String every = context.getResources().getString(R.string.every_thursday);
                return every + formatHoursPeriod(context, start, end);
            }
            case MarketingWorkSchedule.TYPE_FRIDAY: {

                String every = context.getResources().getString(R.string.every_friday);
                return every + formatHoursPeriod(context, start, end);
            }
            case MarketingWorkSchedule.TYPE_SATURDAY: {

                String every = context.getResources().getString(R.string.every_saturday);
                return every + formatHoursPeriod(context, start, end);
            }
            case MarketingWorkSchedule.TYPE_SUNDAY: {

                String every = context.getResources().getString(R.string.every_sunday);
                return every + formatHoursPeriod(context, start, end);
            }
            default: {
                return "";
            }
        }
    }

    public static String businessAddressesTitle(Context context, Business business) {
        if (business.getAddresses() == null) {
            return context.getResources().getString(R.string.has_not_any_departments);
        } else {
            List<Address> addresses = business.getAddresses();
            if (addresses.size() == 1) {
                String street = addresses.get(0).getStreet();
                String number = addresses.get(0).getNumber();
                return BusinessUtil.getFullAddress(street, number);
            } else if (!addresses.isEmpty()) {
                String departments = context.getResources().getString(R.string.departments_);
                return String.format(departments, addresses.size());
            } else {
                return context.getResources().getString(R.string.has_not_any_departments);
            }
        }
    }

    public static String formatBusinessDistanceTitle(Context context, Business business) {

        List<Address> addresses = business.getAddresses();
        if (addresses == null || addresses.isEmpty()) {
            return "";
        }
        Address nearest = addresses.get(0);

        for (Address address : addresses) {
            Double nearestDistance = nearest.getDistance();
            Double distance = address.getDistance();

            if (nearestDistance == null || distance == null) {
                continue;
            }
            if (distance < nearestDistance) {
                nearest = address;
            }
        }
        return formatAddressDistance(context, nearest);
    }

    public static String formatAddressDistance(Context context, Address address) {
        Double distance = address.getDistance();
        if (distance == null) {
            return "";
        }
        if (distance > 1000) {
            return context.getResources().getString(R.string.km_from_you, distance / 1000);
        } else {
            return context.getResources().getString(R.string.m_from_you, Math.round(distance));
        }
    }

    public static String formatAddressDistanceShort(Context context, Address address) {
        Double distance = address.getDistance();
        if (distance == null) {
            return "";
        }
        if (distance > 1000) {
            return context.getResources().getString(R.string.km_distance, distance / 1000);
        } else {
            return context.getResources().getString(R.string.m_distance, Math.round(distance));
        }
    }

    public static LatLng latLngFromAddress(Address address) {
        double lat = Double.parseDouble(address.getLat());
        double lng = Double.parseDouble(address.getLng());
        return new LatLng(lat, lng);
    }

    public static Float starsRating(Float rating) {
        return rating / 2;
    }

    public static String formatRating(Float rating) {
        return String.format(Locale.ENGLISH, "%.01f/%d", rating, 10);
    }

    public static Float average(Float[] ratings) {
        if (ratings.length < 1) return 0.0f;
        float sum = 0.0f;
        for (Float rating : ratings) {
            sum += rating;
        }
        return sum / ratings.length;
    }

    public static void favoriteAdded(Business business) {
        business.getIs().setFavorited(true);
        business.getCan().setFavorite(false);
    }

    public static void favoriteRemoved(Business business) {
        business.getIs().setFavorited(false);
        business.getCan().setFavorite(true);
    }

    public static void favoriteAdded(List<Business> businesses) {
        for (Business business : businesses) {
            business.getIs().setFavorited(true);
            business.getCan().setFavorite(false);
        }
    }

    public static void favoriteRemoved(List<Business> businesses) {
        for (Business business : businesses) {
            business.getIs().setFavorited(false);
            business.getCan().setFavorite(true);
        }
    }

    public static void commentAdded(Business business) {
        business.getIs().setCommented(true);
        business.getCan().setComment(false);
    }

}
