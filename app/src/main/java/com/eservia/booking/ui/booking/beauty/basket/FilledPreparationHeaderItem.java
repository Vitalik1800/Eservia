package com.eservia.booking.ui.booking.beauty.basket;

import org.joda.time.DateTime;

public class FilledPreparationHeaderItem extends FilledPreparationListItem {

    private DateTime day;

    public FilledPreparationHeaderItem(DateTime day) {
        this.day = day;
    }

    public DateTime getDay() {
        return day;
    }

    public void setDay(DateTime day) {
        this.day = day;
    }

    @Override
    public int getItemType() {
        return TYPE_HEADER;
    }
}
