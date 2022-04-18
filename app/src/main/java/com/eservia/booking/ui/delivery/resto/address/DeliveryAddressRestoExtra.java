package com.eservia.booking.ui.delivery.resto.address;

public class DeliveryAddressRestoExtra {

    @DeliveryAddressMode
    int type;

    DeliveryAddressRestoExtra(@DeliveryAddressMode int type) {
        this.type = type;
    }
}
