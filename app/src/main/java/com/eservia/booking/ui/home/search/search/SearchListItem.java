package com.eservia.booking.ui.home.search.search;

public abstract class SearchListItem {

    public static final int ITEM_BUSINESS = 1;
    public static final int ITEM_NOTHING_FOUND = 2;
    public static final int ITEM_LOAD_MORE_PROGRESS = 3;

    abstract public int getType();
}
