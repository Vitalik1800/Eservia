package com.eservia.model.remote.rest.users.services.auth

import com.eservia.model.remote.rest.users.services.UsersServerResponse
import com.google.gson.annotations.SerializedName

/**
 * Created by Alexander on 27.12.2017.
 */
class DeviceTokenResponse : UsersServerResponse() {
    @SerializedName("data")
    var data: Boolean = false
    override fun isItemValid() = true
}