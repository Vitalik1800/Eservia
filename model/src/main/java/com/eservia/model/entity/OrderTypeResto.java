package com.eservia.model.entity;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({OrderTypeResto.DEFAULT, OrderTypeResto.DELIVERY, OrderTypeResto.BOOKING})
public @interface OrderTypeResto {
    int DEFAULT = 1;
    int DELIVERY = 2;
    int BOOKING = 4;
}
