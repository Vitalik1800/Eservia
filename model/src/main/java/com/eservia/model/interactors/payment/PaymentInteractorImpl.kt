package com.eservia.model.interactors.payment

import android.content.Context
import com.eservia.model.remote.rest.RestManager
import com.example.paylibliqpay.ErrorCode
import com.example.paylibliqpay.LiqPay
import com.example.paylibliqpay.LiqPayCallBack
import io.reactivex.Observable
import java.util.*

class PaymentInteractorImpl(val restManager: RestManager) : PaymentInteractor {

    override fun pay(context: Context, details: PaymentDetails): Observable<PaymentResult> {
        return Observable.create { subscriber ->
            checkout(context, details, object : LiqPayCallBack {

                override fun onResponseSuccess(p0: String?) {
                    subscriber.onNext(PaymentResult())
                    subscriber.onComplete()
                }

                override fun onResponceError(p0: ErrorCode?) {
                    subscriber.onNext(PaymentResult())
                    subscriber.onComplete()
                }
            })
        }
    }

//    private fun checkout(context: Context, details: PaymentDetails, callBack: LiqPayCallBack): Observable<Boolean> {
//        return Observable.fromCallable<Boolean> {
//            LiqPay.checkout(context,
//                    checkoutParams(details),
//                    PaymentConfiguration.LIQ_PAY_PRIVATE_KEY,
//                    callBack)
//            true
//        }
//    }

    private fun checkout(context: Context, details: PaymentDetails, callBack: LiqPayCallBack) {
        LiqPay.checkout(context, checkoutParams(details), PaymentConfiguration.LIQ_PAY_PRIVATE_KEY, callBack)
    }

    private fun checkoutParams(details: PaymentDetails): HashMap<String, String> {
        val map = HashMap<String, String>()
        map["version"] = PaymentConfiguration.LIQ_PAY_VERSION
        map["public_key"] = PaymentConfiguration.LIQ_PAY_PUBLIC_KEY
        map["action"] = "pay"
        map["amount"] = details.amount ?: ""
        map["currency"] = details.currency ?: ""
        map["description"] = details.description ?: ""
        map["order_id"] = UUID.randomUUID().toString()
        map["language"] = details.language ?: ""
        map["server_url"] = ""
        map["recurringbytoken"] = "1"
        //TODO: remove sandbox key
        map["sandbox"] = "1"
        return map
    }
}
