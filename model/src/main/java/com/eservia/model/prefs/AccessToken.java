package com.eservia.model.prefs;


import com.eservia.model.entity.AuthData;
import com.eservia.model.entity.AuthorizationData;
import com.eservia.model.prefs.common.PreferencesManager;

public class AccessToken {

    private static final String KEY_ACCESS_TOKEN = "AccessToken";
    private static final String KEY_REFRESH_TOKEN = "RefreshToken";
    private static final String KEY_EXPIRATION_TIME = "ExpirationTime";
    private static final String KEY_SESSION_ID = "SessionId";

    private static final long MIN = 60 * 1000;

    public static boolean isValidRefreshToken() {
        String refreshToken = getRefreshToken();
        return refreshToken != null && !refreshToken.isEmpty();
    }

    public static boolean tokenIsExpired() {
        return getExpirationTime() < System.currentTimeMillis() + (MIN * 5);
    }

    public static void setToken(AuthData data) {

        PreferencesManager.userPrefs().putString(KEY_ACCESS_TOKEN, data.getAccessToken());
        PreferencesManager.userPrefs().putString(KEY_REFRESH_TOKEN, data.getRefreshToken());
        PreferencesManager.userPrefs().putLong(KEY_EXPIRATION_TIME, data.getExpirationTime());
        PreferencesManager.userPrefs().putString(KEY_SESSION_ID, data.getSessionId());
    }

    public static void setToken(AuthorizationData data) {
        PreferencesManager.userPrefs().putString(KEY_ACCESS_TOKEN, data.getToken());
        PreferencesManager.userPrefs().putLong(KEY_EXPIRATION_TIME, data.getExpiration());
    }

    public static void setToken(String token, long expirationData) {
        PreferencesManager.userPrefs().putString(KEY_ACCESS_TOKEN, token);
        PreferencesManager.userPrefs().putLong(KEY_EXPIRATION_TIME, expirationData);
    }

    public static String getAccessToken() {
        return PreferencesManager.userPrefs().getString(KEY_ACCESS_TOKEN);
    }

    public static String getRefreshToken() {
        return PreferencesManager.userPrefs().getString(KEY_REFRESH_TOKEN);
    }

    public static long getExpirationTime() {
        return PreferencesManager.userPrefs().getLong(KEY_EXPIRATION_TIME);
    }

    public static String getSessionId() {
        return PreferencesManager.userPrefs().getString(KEY_SESSION_ID);
    }

    public static void resetToken() {
        PreferencesManager.userPrefs().putString(KEY_ACCESS_TOKEN, null);
        PreferencesManager.userPrefs().putString(KEY_REFRESH_TOKEN, null);
        PreferencesManager.userPrefs().putLong(KEY_EXPIRATION_TIME, 0);
        PreferencesManager.userPrefs().putString(KEY_SESSION_ID, null);
    }
}
