package com.eservia.booking.ui.home.bookings.archive_bookings.general_archive_bookings.adapter

import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponseData

interface BookingBeautyListener {

    fun onBookingBeautyClick(item: GeneralBookingsResponseData, position: Int)
}

interface BookingHealthListener {

    fun onBookingHealthClick(item: GeneralBookingsResponseData, position: Int)
}

interface BookingRestoListener {

    fun onBookingRestoClick(item: GeneralBookingsResponseData, position: Int)
}
