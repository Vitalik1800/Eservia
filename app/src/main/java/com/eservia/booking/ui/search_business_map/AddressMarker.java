package com.eservia.booking.ui.search_business_map;

import com.eservia.model.entity.Address;
import com.eservia.model.entity.Business;

public class AddressMarker {

    private Business business;

    private Address address;

    public AddressMarker(Business business, Address address) {
        this.business = business;
        this.address = address;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
