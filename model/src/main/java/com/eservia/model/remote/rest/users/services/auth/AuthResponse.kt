package com.eservia.model.remote.rest.users.services.auth

import com.eservia.model.entity.AuthData
import com.eservia.model.remote.rest.users.services.UsersServerResponse
import com.google.gson.annotations.SerializedName

class AuthResponse : UsersServerResponse() {

    @SerializedName("data")
    var data: AuthData? = null

    override fun isItemValid() = data?.isItemValid == true
}
