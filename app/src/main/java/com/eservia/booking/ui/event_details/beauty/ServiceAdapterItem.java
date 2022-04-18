package com.eservia.booking.ui.event_details.beauty;

import com.eservia.model.entity.BeautyService;
import com.eservia.model.entity.BeautyServiceGroup;
import com.eservia.model.entity.Marketing;

public class ServiceAdapterItem {

    private BeautyService service;

    private BeautyServiceGroup serviceGroup;

    private Marketing marketing;

    private boolean isSelected;

    public ServiceAdapterItem(BeautyService service, BeautyServiceGroup serviceGroup,
                              Marketing marketing, boolean isSelected) {
        this.service = service;
        this.serviceGroup = serviceGroup;
        this.marketing = marketing;
        this.isSelected = isSelected;
    }

    public BeautyServiceGroup getServiceGroup() {
        return serviceGroup;
    }

    public void setServiceGroup(BeautyServiceGroup serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

    public Marketing getMarketing() {
        return marketing;
    }

    public void setMarketing(Marketing marketing) {
        this.marketing = marketing;
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
