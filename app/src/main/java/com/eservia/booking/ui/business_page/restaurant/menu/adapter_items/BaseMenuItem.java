package com.eservia.booking.ui.business_page.restaurant.menu.adapter_items;

public abstract class BaseMenuItem {

    public static final int ITEM_CATEGORY = 1;
    public static final int ITEM_DISH = 2;

    abstract public int getType();
}
