package com.eservia.booking.ui.menu;

import com.eservia.model.entity.Business;

public class RestoMenuExtra {

    public Business business;

    public Long categoryId;

    public RestoMenuExtra(Business business, Long categoryId) {
        this.business = business;
        this.categoryId = categoryId;
    }
}
