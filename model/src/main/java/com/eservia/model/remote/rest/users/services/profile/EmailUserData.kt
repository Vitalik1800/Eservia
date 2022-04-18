package com.eservia.model.remote.rest.users.services.profile

import com.eservia.model.entity.Validator
import com.google.gson.annotations.SerializedName

/**
 * Created by Alexander on 28.12.2017.
 */
class EmailUserData : Validator {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("email")
    var email: String? = null
    @SerializedName("emailConfirmed")
    var emailConfirmed: Boolean = false
    @SerializedName("phoneNumber")
    var phoneNumber: String? = null
    @SerializedName("firstName")
    var firstName: String? = null
    @SerializedName("lastName")
    var lastName: String? = null

    override fun isItemValid(): Boolean {
        return firstName != null &&
                id != null
    }
}
