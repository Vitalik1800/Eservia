package com.eservia.model.prefs;

import com.eservia.model.prefs.common.PreferencesManager;

import java.util.UUID;

public class Settings {

    private static final String KEY_HAS_FAVORITES = "has_favorites";
    private static final String KEY_GENERATED_USER_ID = "generated_user_id";

    public static void setHasFavorites(boolean hasFavorites) {
        PreferencesManager.appPrefs().putBoolean(KEY_HAS_FAVORITES, hasFavorites);
    }

    public static boolean getHasFavorites() {
        return PreferencesManager.appPrefs().getBoolean(KEY_HAS_FAVORITES);
    }

    public static void setGuid(String guid) {
        PreferencesManager.appPrefs().putString(KEY_GENERATED_USER_ID, guid);
    }

    public static String getGuid() {
        String guid = PreferencesManager.appPrefs().getString(KEY_GENERATED_USER_ID);
        if (guid == null || guid.isEmpty()) {
            guid = UUID.randomUUID().toString();
            setGuid(guid);
        }
        return guid;
    }
}
