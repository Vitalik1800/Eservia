package com.eservia.booking.ui.home.search.sector;

import com.eservia.model.entity.BusinessSector;

public class SectorItem extends ListItem {

    private BusinessSector sector;

    private boolean isMale;

    public SectorItem(BusinessSector sector, boolean isMale) {
        this.sector = sector;
        this.isMale = isMale;
    }

    public BusinessSector getSector() {
        return sector;
    }

    public void setSector(BusinessSector sector) {
        this.sector = sector;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    @Override
    public int getType() {
        return TYPE_SECTOR;
    }
}
