package com.eservia.booking.model.booking_status;

import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;

import java.util.UUID;

public abstract class Status {

    private final String id;

    private final Business business;

    private Address mAddress;

    public Status(Business business) {
        this.business = business;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Business getBusiness() {
        return business;
    }

    public Address getAddress() {
        return mAddress;
    }

    public void setAddress(Address address) {
        mAddress = address;
    }
}
