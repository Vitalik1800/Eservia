package com.eservia.model.remote.rest.users.services.profile

import com.google.gson.annotations.SerializedName

class EditProfileRequest {
    @SerializedName("photoPath")
    var photoPath: String? = null

    @SerializedName("firstName")
    var firstName: String? = null

    @SerializedName("lastName")
    var lastName: String? = null

    @SerializedName("sexId")
    var gender: Long = 0

    @SerializedName("birthday")
    var birthday: Long = 0
}
