package com.eservia.booking.ui.home.search.points_details.adapter

import com.eservia.booking.ui.home.search.points.adapter.BaseItem
import com.eservia.booking.ui.home.search.points.adapter.BaseItemTypeEnum

class PointsDetailsAdapterItem : BaseItem() {

    override val type: Int
        get() = BaseItemTypeEnum.POINTS_DETAILS
}
