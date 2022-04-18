package com.eservia.booking.ui.home.search.discounts.adapter

import com.eservia.booking.ui.home.search.points.adapter.BaseItem
import com.eservia.booking.ui.home.search.points.adapter.BaseItemTypeEnum

class DiscountsAdapterItem : BaseItem() {

    override val type: Int
        get() = BaseItemTypeEnum.DISCOUNT
}
