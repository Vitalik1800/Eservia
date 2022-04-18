package com.eservia.model.remote.rest.users.services.profile

import com.eservia.model.remote.rest.users.services.UsersServerResponse
import com.google.gson.annotations.SerializedName

/**
 * Created by Alexander on 28.12.2017.
 */
class EmailResponse : UsersServerResponse() {

    @SerializedName("data")
    lateinit var data: EmailUserData

    override fun isItemValid(): Boolean {
        return data.isItemValid
    }
}
