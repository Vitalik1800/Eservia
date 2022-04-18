package com.eservia.model.prefs;


import com.eservia.model.prefs.common.PreferencesManager;

public class WasOpened {

    private static final String KEY_WAS_OPENED = "was_opened";

    public static void setWasOpened(boolean wasOpened) {
        PreferencesManager.appPrefs().putBoolean(KEY_WAS_OPENED, wasOpened);
    }

    public static boolean wasOpened() {
        return PreferencesManager.appPrefs().getBoolean(KEY_WAS_OPENED);
    }
}
