package com.eservia.booking.util;

import android.content.Context;

import com.eservia.booking.BuildConfig;

public class AnalyticsHelper {

    private static final String EVENT_ADD_SALON_SEND_POPUP = "addsalon_SEND_popup";

    private static final String EVENT_ADD_SALON_LATER_POPUP = "addsalon_later_popup";

    private static final String EVENT_ADD_SALON_SEND = "addsalon_SEND";

    private static final String EVENT_ADD_SALON_BUTTON = "addsalon_button";

    private static final String EVENT_ADD_SALON_THANKS = "addsalon_thanks";

    public static void logAddSalonSendPopup(Context context) {
        if (shouldSend()) return;
        FirebaseAnalyticsHelper.logEvent(context, EVENT_ADD_SALON_SEND_POPUP, null);
        FacebookAnalyticsHelper.logEvent(context, EVENT_ADD_SALON_SEND_POPUP, null);
    }

    public static void logAddSalonLaterPopup(Context context) {
        if (shouldSend()) return;
        FirebaseAnalyticsHelper.logEvent(context, EVENT_ADD_SALON_LATER_POPUP, null);
        FacebookAnalyticsHelper.logEvent(context, EVENT_ADD_SALON_LATER_POPUP, null);
    }

    public static void logAddSalonSend(Context context) {
        if (shouldSend()) return;
        FirebaseAnalyticsHelper.logEvent(context, EVENT_ADD_SALON_SEND, null);
        FacebookAnalyticsHelper.logEvent(context, EVENT_ADD_SALON_SEND, null);
    }

    public static void logAddSalonButton(Context context) {
        if (shouldSend()) return;
        FirebaseAnalyticsHelper.logEvent(context, EVENT_ADD_SALON_BUTTON, null);
        FacebookAnalyticsHelper.logEvent(context, EVENT_ADD_SALON_BUTTON, null);
    }

    public static void logAddSalonThanks(Context context) {
        if (shouldSend()) return;
        FirebaseAnalyticsHelper.logEvent(context, EVENT_ADD_SALON_THANKS, null);
        FacebookAnalyticsHelper.logEvent(context, EVENT_ADD_SALON_THANKS, null);
    }

    private static boolean shouldSend() {
        return BuildConfig.DEBUG;
    }
}
