package com.eservia.booking.ui.business_page.restaurant.menu;

import com.eservia.model.entity.OrderRestoCategory;
import com.eservia.model.entity.OrderRestoNomenclature;

public interface RestoMenuFragmentListener {

    void onCategoryClicked(OrderRestoCategory category);

    void onDishClicked(OrderRestoNomenclature dish);
}
