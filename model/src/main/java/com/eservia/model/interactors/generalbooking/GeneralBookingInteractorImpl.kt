package com.eservia.model.interactors.generalbooking

import com.eservia.model.entity.*
import com.eservia.model.interactors.booking.BookingInteractor
import com.eservia.model.interactors.resto.RestoBookingsInteractor
import com.eservia.model.remote.rest.RestManager
import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponseData
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class GeneralBookingInteractorImpl(val restManager: RestManager,
                                   private val resoBookingsInteractor: RestoBookingsInteractor,
                                   val bookingInteractor: BookingInteractor) : GeneralBookingInteractor {

    override fun getGeneralActiveBookings(pageIndex: Int?, pageSize: Int?):
            Observable<List<GeneralBookingsResponseData>> {
        return restManager.getGeneralActiveBookings(pageIndex, pageSize)
                .flatMap { allBookings -> onGeneralBookingsLoaded(allBookings) }
    }

    override fun getGeneralHistoryBookings(pageIndex: Int?, pageSize: Int?):
            Observable<List<GeneralBookingsResponseData>> {
        return restManager.getGeneralHistoryBookings(pageIndex, pageSize)
                .flatMap { allBookings -> onGeneralBookingsLoaded(allBookings) }
    }

    private fun onGeneralBookingsLoaded(allBookings: List<GeneralBookingsResponseData>):
            Observable<List<GeneralBookingsResponseData>> {
        val beautyBookings = arrayListOf<BeautyBooking>()
        val healthBookings = arrayListOf<BeautyBooking>()
        val restoBookings = arrayListOf<RestoBooking>()
        val restoDeliveries = arrayListOf<RestoDelivery>()

        for ((index, booking) in allBookings.withIndex()) {
            booking.body.positionInResponse = index
            when (booking.kind) {
                BookingKindEnum.BEAUTY -> beautyBookings.add(booking.body as BeautyBooking)
                BookingKindEnum.HEALTH -> healthBookings.add(booking.body as BeautyBooking)
                BookingKindEnum.BOOKING -> restoBookings.add(booking.body as RestoBooking)
                BookingKindEnum.DELIVERY -> restoDeliveries.add(booking.body as RestoDelivery)
            }
        }

        val beautyObservable = fillBeautyBookings(beautyBookings).subscribeOn(Schedulers.io())
        val healthObservable = fillHealthBookings(healthBookings).subscribeOn(Schedulers.io())
        val restoObservable = fillRestoBookings(restoBookings).subscribeOn(Schedulers.io())
        val deliveryObservable = fillRestoDeliveries(restoDeliveries).subscribeOn(Schedulers.io())

        return Observable.zip(beautyObservable, healthObservable, restoObservable, deliveryObservable
        ) { filledBeauty, filledHealth, filledResto, filledDeliveries ->
            assignGeneralBookings(allBookings, filledBeauty)
            assignGeneralBookings(allBookings, filledHealth)
            assignGeneralBookings(allBookings, filledResto)
            assignGeneralBookings(allBookings, filledDeliveries)
            allBookings
        }
    }

    private fun fillBeautyBookings(bookings: List<BeautyBooking>): Observable<List<BeautyBooking>> {
        return bookingInteractor.fillBookings(BusinessSector.BEAUTY_CLUB, bookings)
    }

    private fun fillHealthBookings(bookings: List<BeautyBooking>): Observable<List<BeautyBooking>> {
        return bookingInteractor.fillBookings(BusinessSector.HEALTH, bookings)
    }

    private fun fillRestoBookings(bookings: List<RestoBooking>): Observable<List<RestoBooking>> {
        return resoBookingsInteractor.fillRestoBookings(bookings)
    }

    private fun fillRestoDeliveries(bookings: List<RestoDelivery>): Observable<List<RestoDelivery>> {
        return resoBookingsInteractor.fillRestoDeliveries(bookings)
    }

    private fun assignGeneralBookings(dataList: List<GeneralBookingsResponseData>, generalBookings: List<GeneralBooking>) {
        for (booking in generalBookings)
            dataList[booking.positionInResponse].body = booking
    }
}
