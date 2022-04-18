package com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings

import com.eservia.booking.common.view.LoadingView
import com.eservia.model.entity.RestoDelivery
import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponseData
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface GeneralActiveBookingsView : LoadingView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun onBookingsLoadingSuccess(bookings: List<GeneralBookingsResponseData>)

    @StateStrategyType(value = SkipStrategy::class)
    fun onBookingsLoadingFailed(throwable: Throwable)

    @StateStrategyType(value = SkipStrategy::class)
    fun showCancelBookingDialog()

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun hideCancelBookingDialog()

    @StateStrategyType(value = SkipStrategy::class)
    fun onBookingCancelSuccess()

    @StateStrategyType(value = SkipStrategy::class)
    fun onBookingCancelFailed(throwable: Throwable)

    @StateStrategyType(value = SkipStrategy::class)
    fun openDeliveryDetails(delivery: RestoDelivery)
}
