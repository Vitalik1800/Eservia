package com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings.adapter

import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponseData

interface BookingBeautyListener {

    fun onBookingBeautyClick(item: GeneralBookingsResponseData, position: Int)

    fun onBookingBeautyDeleteClick(item: GeneralBookingsResponseData, position: Int)
}

interface BookingHealthListener {

    fun onBookingHealthClick(item: GeneralBookingsResponseData, position: Int)

    fun onBookingHealthDeleteClick(item: GeneralBookingsResponseData, position: Int)
}

interface BookingRestoListener {

    fun onBookingRestoClick(item: GeneralBookingsResponseData, position: Int)

    fun onBookingRestoDeleteClick(item: GeneralBookingsResponseData, position: Int)
}

interface BookingDeliveryListener {

    fun onDeliveryClick(item: GeneralBookingsResponseData, position: Int)
}

interface BookingPaginationListener {

    fun loadMoreBookings()
}
