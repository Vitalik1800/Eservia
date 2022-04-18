package com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings

import com.eservia.booking.App
import com.eservia.booking.common.presenter.BasePresenter
import com.eservia.booking.ui.home.bookings.active_bookings.general_active_bookings.adapter.*
import com.eservia.model.entity.BeautyBooking
import com.eservia.model.entity.BookingKindEnum
import com.eservia.model.entity.RestoBooking
import com.eservia.model.entity.RestoDelivery
import com.eservia.model.interactors.generalbooking.GeneralBookingInteractor
import com.eservia.model.interactors.resto.RestoBookingsInteractor
import com.eservia.model.local.ContentChangesObservable
import com.eservia.model.remote.rest.RestManager
import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingsResponseData
import com.eservia.utils.Contract
import com.eservia.utils.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class GeneralActiveBookingsPresenter : BasePresenter<GeneralActiveBookingsView>(),
        BookingPaginationListener,
        BookingBeautyListener,
        BookingHealthListener,
        BookingRestoListener,
        BookingDeliveryListener,
        CancelBookingDialogSheet.OnCancelBookingListener {

    private val mBookings = mutableListOf<GeneralBookingsResponseData>()

    @Inject
    lateinit var mGeneralBookingInteractor: GeneralBookingInteractor

    @Inject
    lateinit var mRestoBookingsInteractor: RestoBookingsInteractor

    @Inject
    lateinit var mRestManager: RestManager

    private var mBookingsPaginationDisposable: Disposable? = null
    private var mCancelBookingDisposable: Disposable? = null
    private var mIsAllBookingsLoaded = false
    private var mBookingToCancel: GeneralBookingsResponseData? = null

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

    override fun onBookingBeautyDeleteClick(item: GeneralBookingsResponseData, position: Int) {
        mBookingToCancel = item
        viewState.showCancelBookingDialog()
    }

    override fun onBookingHealthClick(item: GeneralBookingsResponseData, position: Int) {
    }

    override fun onBookingHealthDeleteClick(item: GeneralBookingsResponseData, position: Int) {
        mBookingToCancel = item
        viewState.showCancelBookingDialog()
    }

    override fun onDialogKeepBookingClick() {
        viewState.hideCancelBookingDialog()
    }

    override fun onDialogCancelBookingClick() {
        viewState.hideCancelBookingDialog()
        if (mBookingToCancel != null) {
            cancelBooking(mBookingToCancel!!)
        }
    }

    override fun loadMoreBookings() {
        if (!paginationInProgress(mBookingsPaginationDisposable) && !mIsAllBookingsLoaded)
            makeBookingsPagination()
    }

    override fun onBookingRestoClick(item: GeneralBookingsResponseData, position: Int) {
    }

    override fun onBookingRestoDeleteClick(item: GeneralBookingsResponseData, position: Int) {
        mBookingToCancel = item
        viewState.showCancelBookingDialog()
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
                .getGeneralActiveBookings(mBookings.size / PART, PART)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        mBookingsPaginationDisposable = observable.subscribe(
                { this.onBookingsLoadingSuccess(it) },
                { this.onBookingsLoadingFailed(it) })
        addSubscription(mBookingsPaginationDisposable)
    }

    private fun cancelBooking(bookingData: GeneralBookingsResponseData) {
        when (bookingData.kind) {
            BookingKindEnum.BEAUTY -> cancelBeautyBooking(bookingData.body as BeautyBooking)
            BookingKindEnum.HEALTH -> cancelHealthBooking(bookingData.body as BeautyBooking)
            BookingKindEnum.BOOKING -> cancelRestoBooking(bookingData.body as RestoBooking)
        }
    }

    private fun cancelBeautyBooking(booking: BeautyBooking) {
        cancelPagination(mCancelBookingDisposable)
        val observable = mRestManager
                .cancelBookingBeauty(booking.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        mCancelBookingDisposable = observable.subscribe(
                { this.onBookingCancelSuccess() },
                { this.onBookingCancelFailed(it) })
        addSubscription(mCancelBookingDisposable)
    }

    private fun cancelHealthBooking(booking: BeautyBooking) {
        cancelPagination(mCancelBookingDisposable)
        val observable = mRestManager
                .cancelBookingHealth(booking.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        mCancelBookingDisposable = observable.subscribe(
                { this.onBookingCancelSuccess() },
                { this.onBookingCancelFailed(it) })
        addSubscription(mCancelBookingDisposable)
    }

    private fun cancelRestoBooking(booking: RestoBooking) {
        cancelPagination(mCancelBookingDisposable)
        val observable = mRestoBookingsInteractor
                .cancelRestoBooking(booking.id, booking.addressId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        mCancelBookingDisposable = observable.subscribe(
                { this.onBookingCancelSuccess() },
                { this.onBookingCancelFailed(it) })
        addSubscription(mCancelBookingDisposable)
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

    private fun onBookingCancelSuccess() {
        if (mBookingToCancel != null) {
            mBookings.remove(mBookingToCancel!!)
        }
        viewState.onBookingCancelSuccess()
        if (!paginationInProgress(mBookingsPaginationDisposable)) {
            viewState.onBookingsLoadingSuccess(mBookings)
        }
        mBookingToCancel = null
        mCancelBookingDisposable = null
    }

    private fun onBookingCancelFailed(throwable: Throwable) {
        viewState.onBookingCancelFailed(throwable)
        mBookingToCancel = null
        mCancelBookingDisposable = null
    }

    private fun onSuccessHandledBookingsUpdate() {
        refreshBookings()
    }

    private fun onErrorHandledBookingsUpdate(throwable: Throwable) {
        LogUtils.debug(Contract.LOG_TAG, throwable.message)
    }
}
