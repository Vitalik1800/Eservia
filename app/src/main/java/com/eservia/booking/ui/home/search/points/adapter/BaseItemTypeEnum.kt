package com.eservia.booking.ui.home.search.points.adapter

import androidx.annotation.IntDef

object BaseItemTypeEnum {
    const val NOTHING_FOUND = 0
    const val POINTS = 1
    const val POINTS_DETAILS = 2
    const val DISCOUNT = 3

    @IntDef(NOTHING_FOUND, POINTS, POINTS_DETAILS, DISCOUNT)
    @Retention(AnnotationRetention.SOURCE)
    annotation class BaseItemTypeEnum
}
