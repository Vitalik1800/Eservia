package com.eservia.model.interactors.payment

data class PaymentDetails(
        var amount: String? = null,
        var currency: String? = null,
        var description: String? = null,
        var language: String? = null
)
