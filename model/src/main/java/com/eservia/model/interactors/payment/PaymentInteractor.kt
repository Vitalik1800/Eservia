package com.eservia.model.interactors.payment

import android.content.Context
import io.reactivex.Observable

interface PaymentInteractor {

    fun pay(context: Context, details: PaymentDetails): Observable<PaymentResult>
}
