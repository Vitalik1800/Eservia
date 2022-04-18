package com.eservia.model.remote.rest.users.services.auth

import com.google.gson.annotations.SerializedName

/**
 * Created by Alexander on 27.12.2017.
 */
data class SignUpProviderRequest(
        @SerializedName("username")
        var username: String,
        @SerializedName("provider")
        var provider: String,
        @SerializedName("token")
        var token: String
)

