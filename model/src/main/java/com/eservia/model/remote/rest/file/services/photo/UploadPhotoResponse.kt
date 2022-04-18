package com.eservia.model.remote.rest.file.services.photo

import com.eservia.model.remote.rest.file.services.FileServerResponse
import com.google.gson.annotations.SerializedName

class UploadPhotoResponse : FileServerResponse() {

    @SerializedName("data")
    var data: String? = null

    override fun isItemValid(): Boolean {
        return true
    }
}
