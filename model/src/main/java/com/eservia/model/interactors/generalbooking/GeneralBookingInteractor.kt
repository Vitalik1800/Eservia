package com.eservia.model.interactors.generalbooking

import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponseData
import io.reactivex.Observable

interface GeneralBookingInteractor {

    fun getGeneralActiveBookings(pageIndex: Int?, pageSize: Int?):
            Observable<List<GeneralBookingsResponseData>>

    fun getGeneralHistoryBookings(pageIndex: Int?, pageSize: Int?):
            Observable<List<GeneralBookingsResponseData>>
}
