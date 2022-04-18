package com.eservia.model.remote.rest.users.services.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class SignInRequest(
        @SerializedName("username")
        @Expose
        var username: String,

        @SerializedName("password")
        @Expose
        var password: String
)
