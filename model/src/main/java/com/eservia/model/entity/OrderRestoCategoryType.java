package com.eservia.model.entity;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({OrderRestoCategoryType.FOLDER, OrderRestoCategoryType.TAG})
public @interface OrderRestoCategoryType {
    int FOLDER = 1;
    int TAG = 2;
}
