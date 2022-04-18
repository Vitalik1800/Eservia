package com.eservia.model.entity;

import androidx.annotation.LongDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@LongDef({OrderRestoSaleMethod.SIZE_PRICE, OrderRestoSaleMethod.PORTION, OrderRestoSaleMethod.BY_WEIGHT})
public @interface OrderRestoSaleMethod {
    long SIZE_PRICE = 1;
    long PORTION = 2;
    long BY_WEIGHT = 3;
}
