package com.eservia.booking.ui.booking.beauty.service;

import com.eservia.model.entity.BeautyService;

public class ServiceAdapterItem {

    private boolean isSelected;
    private BeautyService service;

    public ServiceAdapterItem(BeautyService service) {
        this.service = service;
        this.isSelected = false;
    }

    public ServiceAdapterItem(BeautyService service, boolean isSelected) {
        this.service = service;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public BeautyService getService() {
        return service;
    }

    public void setService(BeautyService service) {
        this.service = service;
    }
}
