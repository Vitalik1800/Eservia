package com.eservia.booking.ui.home.search.sector;

public abstract class ListItem {

    public static final int TYPE_SECTOR = 1;
    public static final int TYPE_CHOOSE_SECTOR = 2;
    public static final int TYPE_NOT_FOUND = 3;

    abstract public int getType();
}
