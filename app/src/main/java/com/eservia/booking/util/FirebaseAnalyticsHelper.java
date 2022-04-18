package com.eservia.booking.util;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseAnalyticsHelper {

    public static void logEvent(Context context, String event, @Nullable Bundle bundle) {
        firebaseAnalytics(context).logEvent(event, bundle);
    }

    private static FirebaseAnalytics firebaseAnalytics(Context context) {
        return FirebaseAnalytics.getInstance(context);
    }
}
