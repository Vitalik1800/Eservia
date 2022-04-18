package com.eservia.booking.ui.business_page.restaurant.menu.adapter_items;

import com.eservia.model.entity.OrderRestoNomenclature;

public class DishItem extends BaseMenuItem {

    private OrderRestoNomenclature nomenclature;

    public DishItem(OrderRestoNomenclature nomenclature) {
        this.nomenclature = nomenclature;
    }

    public OrderRestoNomenclature getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(OrderRestoNomenclature nomenclature) {
        this.nomenclature = nomenclature;
    }

    @Override
    public int getType() {
        return ITEM_DISH;
    }
}
