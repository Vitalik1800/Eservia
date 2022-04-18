package com.eservia.booking.model.event_bus;

import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessSector;

public class BusinessFavoriteAdded {

    public Business business;

    public BusinessSector sector;

    public BusinessFavoriteAdded(Business business, BusinessSector sector) {
        this.business = business;
        this.sector = sector;
    }
}
