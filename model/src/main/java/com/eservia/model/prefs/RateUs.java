package com.eservia.model.prefs;


import com.eservia.model.prefs.common.PreferencesManager;

public class RateUs {

    private static final String KEY_DONT_SHOW_AGAIN = "dont_show_again";
    private static final String KEY_APP_LAUNCH_COUNT = "app_launch_count";
    private static final String KEY_RESERVATION_COUNT = "reservation_count";
    private static final String KEY_FIRST_LAUNCH_DATE = "first_launch_date";

    public static void setDontShowAgain(boolean dontShowAgain) {
        PreferencesManager.appPrefs().putBoolean(KEY_DONT_SHOW_AGAIN, dontShowAgain);
    }

    public static boolean isDontShowAgain() {
        return PreferencesManager.appPrefs().getBoolean(KEY_DONT_SHOW_AGAIN);
    }

    public static void setLaunchCount(long launchCount) {
        PreferencesManager.appPrefs().putLong(KEY_APP_LAUNCH_COUNT, launchCount);
    }

    public static long getLaunchCount() {
        return PreferencesManager.appPrefs().getLong(KEY_APP_LAUNCH_COUNT);
    }

    public static void setReservationCount(long reservationCount) {
        PreferencesManager.appPrefs().putLong(KEY_RESERVATION_COUNT, reservationCount);
    }

    public static long getReservationCount() {
        return PreferencesManager.appPrefs().getLong(KEY_RESERVATION_COUNT);
    }

    public static void setFirstLaunchDate(long firstLaunchDate) {
        PreferencesManager.appPrefs().putLong(KEY_FIRST_LAUNCH_DATE, firstLaunchDate);
    }

    public static long getFirstLaunchDate() {
        return PreferencesManager.appPrefs().getLong(KEY_FIRST_LAUNCH_DATE);
    }
}
