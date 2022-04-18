package com.eservia.model.remote.rest.users.services.auth

import com.google.gson.annotations.SerializedName


data class SocialSignInRequest(
        @SerializedName("provider")
        var provider: String,

        @SerializedName("token")
        var token: String
)