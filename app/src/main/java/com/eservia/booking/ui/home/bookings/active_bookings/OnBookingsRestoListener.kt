package com.eservia.booking.ui.home.bookings.active_bookings

import com.eservia.model.entity.RestoDelivery

interface OnBookingsRestoListener {
    fun onOpenDeliveryInfo(delivery: RestoDelivery)
}