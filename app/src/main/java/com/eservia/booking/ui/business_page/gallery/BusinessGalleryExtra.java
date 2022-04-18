package com.eservia.booking.ui.business_page.gallery;

import com.eservia.model.entity.Business;

public class BusinessGalleryExtra {

    public Business business;

    public int startPosition;

    public BusinessGalleryExtra(Business business) {
        this.business = business;
        this.startPosition = 0;
    }

    public BusinessGalleryExtra(Business business, int startPosition) {
        this.business = business;
        this.startPosition = startPosition;
    }
}
