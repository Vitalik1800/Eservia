package com.eservia.model.remote.rest.users.services.auth

import com.google.gson.annotations.SerializedName

/**
 * Created by Alexander on 27.12.2017.
 */
data class RestorePasswordRequest(
        @SerializedName("phoneNumber")
        var phoneNumber: String,

        @SerializedName("password")
        var password: String,

        @SerializedName("code")
        var code: String
)