package com.eservia.model.remote.rest.users.services.auth

import com.google.gson.annotations.SerializedName

data class ConfirmPhoneRequest(
        @SerializedName("phoneNumber")
        var phoneNumber: String,
        @SerializedName("code")
        var code: String
)
