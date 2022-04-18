package com.eservia.booking.ui.payment

import android.content.Context
import com.eservia.booking.App
import com.eservia.booking.common.presenter.BasePresenter
import com.eservia.model.interactors.payment.PaymentDetails
import com.eservia.model.interactors.payment.PaymentInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class PaymentPresenter : BasePresenter<PaymentView>() {

    @Inject
    lateinit var mPaymentInteractor: PaymentInteractor

    @Inject
    lateinit var mContext: Context

    init {
        App.getAppComponent().inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        pay()
    }

    private fun pay() {
        val observable = mPaymentInteractor
                .pay(mContext, paymentDetails())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addSubscription(observable.subscribe(
                { this.onPaymentResult() },
                { this.onPaymentError() }
        ))
    }

    private fun onPaymentResult() {
        println()
    }

    private fun onPaymentError() {
        println()
    }

    private fun paymentDetails(): PaymentDetails {
        return PaymentDetails("1.0", "uah", "Оплата послуг", "ru")
    }
}
