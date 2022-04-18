package com.eservia.simplecalendar;

import org.joda.time.DateTime;

public interface SimpleCalendarListener {

    void onDaySelected(DateTime day);

    void onDaySelectionCancelled(DateTime day);
}
