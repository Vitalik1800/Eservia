package com.eservia.booking.util;

import com.eservia.model.prefs.BusinessSuggestion;
import com.eservia.model.prefs.RateUs;

public class BusinessSuggestionUtil {

    private final static int SHOWING_INTERVAL = 3;
    private final static int MAX_SHOWING_COUNT = 4;

    public static boolean shouldShowBusinessSuggestionModal() {

        if (BusinessSuggestion.isDontShowAgain()) {
            return false;
        }

        if (BusinessSuggestion.getShowingCount() >= MAX_SHOWING_COUNT) {
            return false;
        }

        if (RateUs.getLaunchCount() == 1) {
            return true;
        }

        return RateUs.getLaunchCount() % SHOWING_INTERVAL == 0;
    }

    public static void onBusinessSuggestionModalShowed() {
        BusinessSuggestion.setShowingCount(BusinessSuggestion.getShowingCount() + 1);
    }
}
