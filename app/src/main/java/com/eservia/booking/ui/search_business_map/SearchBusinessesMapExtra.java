package com.eservia.booking.ui.search_business_map;

import com.eservia.model.entity.Business;
import com.eservia.model.entity.BusinessSector;

import java.util.ArrayList;
import java.util.List;

public class SearchBusinessesMapExtra {

    public final List<Business> businesses = new ArrayList<>();

    public BusinessSector sector;

    public SearchBusinessesMapExtra(List<Business> businesses,
                                    BusinessSector sector) {
        this.businesses.addAll(businesses);
        this.sector = sector;
    }
}
