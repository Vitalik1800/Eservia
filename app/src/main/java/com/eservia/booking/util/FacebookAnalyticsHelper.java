package com.eservia.booking.util;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.facebook.appevents.AppEventsLogger;

public class FacebookAnalyticsHelper {

    public static void logEvent(Context context, String event, @Nullable Bundle bundle) {
        appEventsLogger(context).logEvent(event, bundle);
    }

    private static AppEventsLogger appEventsLogger(Context context) {
        return AppEventsLogger.newLogger(context);
    }
}
