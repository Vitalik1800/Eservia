package com.eservia.booking.ui.home.bookings.archive_bookings.general_archive_bookings

import com.eservia.booking.App
import com.eservia.booking.common.presenter.BasePresenter
import com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings.adapter.BookingDeliveryListener
import com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings.adapter.BookingPaginationListener
import com.eservia.booking.ui.home.bookings.archive_bookings.general_archive_bookings.adapter.BookingBeautyListener
import com.eservia.booking.ui.home.bookings.archive_bookings.general_archive_bookings.adapter.BookingHealthListener
import com.eservia.booking.ui.home.bookings.archive_bookings.general_archive_bookings.adapter.BookingRestoListener
import com.eservia.model.entity.RestoDelivery
import com.eservia.model.interactors.generalbooking.GeneralBookingInteractor
import com.eservia.model.local.ContentChangesObservable
import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponseData
import com.eservia.utils.Contract
import com.eservia.utils.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class GeneralArchiveBookingsPresenter : BasePresenter<GeneralArchiveBookingsView>(),
        BookingPaginationListener,
        BookingBeautyListener,
        BookingHealthListener,
        BookingRestoListener,
        BookingDeliveryListener {

    private val mBookings = mutableListOf<GeneralBookingsResponseData>()

    @Inject
    lateinit var mGeneralBookingInteractor: GeneralBookingInteractor

    private var mBookingsPaginationDisposable: Disposable? = null
    private var mIsAllBookingsLoaded = false

    init {
        App.getAppComponent().inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        subscribeOnBookingsUpdate()
        refreshBookings()
    }

    override fun onBookingBeautyClick(item: GeneralBookingsResponseData, position: Int) {
    }

    override fun onBookingHealthClick(item: GeneralBookingsResponseData, position: Int) {
    }

    override fun loadMoreBookings() {
        if (!paginationInProgress(mBookingsPaginationDisposable) && !mIsAllBookingsLoaded)
            makeBookingsPagination()
    }

    override fun onBookingRestoClick(item: GeneralBookingsResponseData, position: Int) {
    }

    override fun onDeliveryClick(item: GeneralBookingsResponseData, position: Int) {
        viewState.openDeliveryDetails(item.body as RestoDelivery)
    }

    fun refreshBookings() {
        cancelPagination(mBookingsPaginationDisposable)
        mBookings.clear()
        mIsAllBookingsLoaded = false
        makeBookingsPagination()
    }

    private fun makeBookingsPagination() {
        if (mBookings.isEmpty())
            viewState.showProgress()

        cancelPagination(mBookingsPaginationDisposable)

        val observable = mGeneralBookingInteractor
                .getGeneralHistoryBookings(mBookings.size / PART, PART)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        mBookingsPaginationDisposable = observable.subscribe(
                { this.onBookingsLoadingSuccess(it) },
                { this.onBookingsLoadingFailed(it) })
        addSubscription(mBookingsPaginationDisposable)
    }

    private fun subscribeOnBookingsUpdate() {
        addSubscription(ContentChangesObservable.bookings(false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { this.onSuccessHandledBookingsUpdate() },
                        { this.onErrorHandledBookingsUpdate(it) }))
    }

    private fun onBookingsLoadingSuccess(bookings: List<GeneralBookingsResponseData>) {
        mBookings.addAll(bookings)
        viewState.hideProgress()
        viewState.onBookingsLoadingSuccess(mBookings)
        mBookingsPaginationDisposable = null
        if (bookings.size != PART) {
            mIsAllBookingsLoaded = true
        }
    }

    private fun onBookingsLoadingFailed(throwable: Throwable) {
        viewState.hideProgress()
        viewState.onBookingsLoadingFailed(throwable)
        mBookingsPaginationDisposable = null
    }

    private fun onSuccessHandledBookingsUpdate() {
        refreshBookings()
    }

    private fun onErrorHandledBookingsUpdate(throwable: Throwable) {
        LogUtils.debug(Contract.LOG_TAG, throwable.message)
    }
}
