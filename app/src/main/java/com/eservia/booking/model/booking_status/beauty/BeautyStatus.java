package com.eservia.booking.model.booking_status.beauty;

import com.eservia.booking.model.booking_status.Status;
import com.eservia.model.entity.BeautyServiceGroup;
import com.eservia.model.entity.Business;

import java.util.ArrayList;
import java.util.List;

public class BeautyStatus extends Status {

    private BeautyServiceGroup mServiceGroup;

    private final List<Preparation> mPreparations = new ArrayList<>();

    public BeautyStatus(Business business) {
        super(business);
    }

    public BeautyServiceGroup getServiceGroup() {
        return mServiceGroup;
    }

    public void setServiceGroup(BeautyServiceGroup serviceGroup) {
        this.mServiceGroup = serviceGroup;
    }

    public List<Preparation> getPreparations() {
        return mPreparations;
    }
}
