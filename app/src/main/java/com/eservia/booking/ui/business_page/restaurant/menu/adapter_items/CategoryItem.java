package com.eservia.booking.ui.business_page.restaurant.menu.adapter_items;

import com.eservia.model.entity.OrderRestoCategory;

public class CategoryItem extends BaseMenuItem {

    private OrderRestoCategory category;

    public CategoryItem(OrderRestoCategory category) {
        this.category = category;
    }

    public OrderRestoCategory getCategory() {
        return category;
    }

    public void setCategory(OrderRestoCategory category) {
        this.category = category;
    }

    @Override
    public int getType() {
        return ITEM_CATEGORY;
    }
}
