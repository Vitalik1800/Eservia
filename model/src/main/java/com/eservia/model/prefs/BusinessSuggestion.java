package com.eservia.model.prefs;


import com.eservia.model.prefs.common.PreferencesManager;

public class BusinessSuggestion {

    private static final String KEY_DONT_SHOW_AGAIN_BUSINESS_SUGGESTION
            = "dont_show_again_business_suggestion";

    private static final String KEY_BUSINESS_SUGGESTION_SHOWING_COUNT
            = "business_suggestion_showing_count";

    public static void setDontShowAgain(boolean dontShowAgain) {
        PreferencesManager.appPrefs().putBoolean(KEY_DONT_SHOW_AGAIN_BUSINESS_SUGGESTION, dontShowAgain);
    }

    public static boolean isDontShowAgain() {
        return PreferencesManager.appPrefs().getBoolean(KEY_DONT_SHOW_AGAIN_BUSINESS_SUGGESTION);
    }

    public static void setShowingCount(long count) {
        PreferencesManager.appPrefs().putLong(KEY_BUSINESS_SUGGESTION_SHOWING_COUNT, count);
    }

    public static long getShowingCount() {
        return PreferencesManager.appPrefs().getLong(KEY_BUSINESS_SUGGESTION_SHOWING_COUNT);
    }
}
