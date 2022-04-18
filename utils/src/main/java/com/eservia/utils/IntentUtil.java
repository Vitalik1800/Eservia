package com.eservia.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

public class IntentUtil {

    private final static String APP_ID_INSTAGRAM = "com.instagram.android";

    public static Intent instagramProfileIntent(PackageManager pm, String url) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (pm.getPackageInfo(APP_ID_INSTAGRAM, 0) != null) {
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length() - 1);
                }
                final String username = url.substring(url.lastIndexOf("/") + 1);
                intent.setData(Uri.parse("https://instagram.com/_u/" + username));
                intent.setPackage(APP_ID_INSTAGRAM);
                return intent;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        intent.setData(Uri.parse(url));
        return intent;
    }

    public static Intent facebookProfileIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent urlIntent(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        intent.setData(Uri.parse(url));
        return intent;
    }

    public static Intent googleMapRouteIntent(double lat, double lng) {
        Uri.Builder directionsBuilder = new Uri.Builder()
                .scheme("https")
                .authority("www.google.com")
                .appendPath("maps")
                .appendPath("dir")
                .appendPath("")
                .appendQueryParameter("api", "1")
                .appendQueryParameter("destination", lat + "," + lng);
        return new Intent(Intent.ACTION_VIEW, directionsBuilder.build());
    }

    public static void openLinkWebsite(Context context, String url) {
        if (StringUtil.isEmpty(url)) {
            return;
        }
        try {
            context.startActivity(IntentUtil.urlIntent(url));
        } catch (Exception e) {
            LogUtils.debug(Contract.LOG_TAG, "Failed to start website intent: " + e.getMessage());
        }
    }

    public static void openLinkFacebook(Context context, String url) {
        if (StringUtil.isEmpty(url)) {
            return;
        }
        try {
            context.startActivity(IntentUtil.facebookProfileIntent(context.getPackageManager(), url));
        } catch (Exception e) {
            LogUtils.debug(Contract.LOG_TAG, "Failed to start facebook intent: " + e.getMessage());
        }
    }

    public static void openLinkInstagram(Context context, String url) {
        if (StringUtil.isEmpty(url)) {
            return;
        }
        try {
            context.startActivity(IntentUtil.instagramProfileIntent(context.getPackageManager(), url));
        } catch (Exception e) {
            LogUtils.debug(Contract.LOG_TAG, "Failed to start instagram intent: " + e.getMessage());
        }
    }

}
