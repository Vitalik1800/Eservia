package com.eservia.model.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by Alexander on 29.12.2017.
 */
class ProfileUserData : Validator {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("customerId")
    var customerId: String? = null

    @SerializedName("email")
    var email: String? = null
    @SerializedName("emailConfirmed")
    var emailConfirmed: Boolean = false
    @SerializedName("phoneNumber")
    var phoneNumber: String? = null
    @SerializedName("photoPath")
    var photoId: String? = null
    @SerializedName("firstName")
    var firstName: String? = null
    @SerializedName("lastName")
    var lastName: String? = null
    @SerializedName("birthday")
    var birthday: Long = 0
    @SerializedName("cafeId")
    var establishmentId: Long = 0

    @SerializedName("sexId")
    var gender: Long = Gender.UNSPECIFIED
    @SerializedName("isBlocked")
    var isBlocked: Boolean = false
    @SerializedName("googleId")
    var googleId: String? = null
    @SerializedName("facebookId")
    var facebookId: String? = null

    override fun isItemValid(): Boolean {
        return firstName != null &&
                id != null
    }
}
