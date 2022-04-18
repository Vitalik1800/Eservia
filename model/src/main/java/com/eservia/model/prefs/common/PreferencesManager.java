package com.eservia.model.prefs.common;


import android.content.Context;

public class PreferencesManager {

    private static final String KEY_APP_PREFS = "app_prefs";

    private static PrivatePrefs userPrefs;
    private static PrivatePrefs appPrefs;

    private PreferencesManager() {
    }

    public static synchronized void initializeInstance(Context context) {
        userPrefs = new UserPrefs(context, context.getPackageName());
        appPrefs = new AppPrefs(context, KEY_APP_PREFS);
    }

    public static synchronized PrivatePrefs userPrefs() {
        if (userPrefs == null) {
            throw new IllegalStateException(
                    PreferencesManager.class.getSimpleName() + " is not initialized");
        }
        return userPrefs;
    }

    public static synchronized PrivatePrefs appPrefs() {
        if (appPrefs == null) {
            throw new IllegalStateException(
                    PreferencesManager.class.getSimpleName() + " is not initialized");
        }
        return appPrefs;
    }
}
