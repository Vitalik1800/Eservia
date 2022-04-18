package com.eservia.booking.ui.home.search.search;

import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessSector;

public class BusinessAdapterItem extends SearchListItem {

    private Business business;

    private BusinessSector sector;

    public BusinessAdapterItem(Business business, BusinessSector sector) {
        this.business = business;
        this.sector = sector;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public BusinessSector getSector() {
        return sector;
    }

    public void setSector(BusinessSector sector) {
        this.sector = sector;
    }

    @Override
    public int getType() {
        return ITEM_BUSINESS;
    }
}
