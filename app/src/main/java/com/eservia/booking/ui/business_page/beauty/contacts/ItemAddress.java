package com.eservia.booking.ui.business_page.beauty.contacts;

import com.eservia.model.entity.Address;

public class ItemAddress extends AddressAdapterItem {

    private Address address;

    public ItemAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public int getType() {
        return ITEM_ADDRESS;
    }
}
