package com.eservia.booking.ui.delivery.resto.address;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({DeliveryAddressMode.SETTLEMENT, DeliveryAddressMode.STREET})
public @interface DeliveryAddressMode {
    int SETTLEMENT = 1;
    int STREET = 2;
}
