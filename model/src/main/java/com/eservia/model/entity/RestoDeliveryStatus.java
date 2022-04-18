package com.eservia.model.entity;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({RestoDeliveryStatus.CREATED, RestoDeliveryStatus.NOT_AGREED, RestoDeliveryStatus.WAITING,
        RestoDeliveryStatus.ACCEPTED, RestoDeliveryStatus.IN_PROGRESS, RestoDeliveryStatus.PENDING,
        RestoDeliveryStatus.DONE, RestoDeliveryStatus.CANCELLED, RestoDeliveryStatus.OVERDUE})
public @interface RestoDeliveryStatus {
    int CREATED = 0;
    int NOT_AGREED = 1;
    int WAITING = 2;
    int ACCEPTED = 3;
    int IN_PROGRESS = 4;
    int DONE = 5;
    int CANCELLED = 6;
    int OVERDUE = 7;
    int PENDING = 8;
}
