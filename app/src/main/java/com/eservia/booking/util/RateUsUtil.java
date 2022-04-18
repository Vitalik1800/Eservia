package com.eservia.booking.util;

import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.FragmentActivity;

import com.eservia.booking.BuildConfig;
import com.eservia.booking.common.view.RateUsDialog;
import com.eservia.model.prefs.RateUs;

public class RateUsUtil {

    private final static int RESERVATIONS_UNTIL_PROMPT = 2;

    public static void appLaunched() {
        RateUs.setLaunchCount(RateUs.getLaunchCount() + 1);
        if (RateUs.getFirstLaunchDate() == 0) {
            RateUs.setFirstLaunchDate(System.currentTimeMillis());
        }
    }

    public static void reservationAdded() {
        RateUs.setReservationCount(RateUs.getReservationCount() + 1);
    }

    public static boolean shouldShowRateUsDialog() {
        if (RateUs.isDontShowAgain()) {
            return false;
        }
        return RateUs.getReservationCount() >= RESERVATIONS_UNTIL_PROMPT;
    }

    public static void showRateUsDialog(FragmentActivity activity) {
        RateUsDialog rateUsDialog = RateUsDialog.newInstance();
        rateUsDialog.show(activity.getSupportFragmentManager(), RateUsDialog.class.getSimpleName());
    }

    public static void openPlayMarketPage(FragmentActivity activity) {
        final String appPackageName = BuildConfig.APPLICATION_ID;
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
