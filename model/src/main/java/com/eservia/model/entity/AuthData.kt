package com.eservia.model.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by Alexander on 27.12.2017.
 */

class AuthData : Validator {

    @SerializedName("userId")
    var userId: String? = null
    @SerializedName("token")
    var accessToken: String? = null
    @SerializedName("refreshToken")
    var refreshToken: String? = null
    @SerializedName("sessionId")
    var sessionId: String? = null
    /**
     * Token expiration time in seconds
     */
    @SerializedName("expirationTime")
    var expirationTime: Long = 0

    override fun isItemValid() =
            userId != null &&
                    accessToken != null &&
                    refreshToken != null &&
                    sessionId != null

    override fun toString(): String {
        return "AuthData(" +
                "userId='$userId', " +
                "accessToken='$accessToken', " +
                "refreshToken='$refreshToken', " +
                "expirationTime=$expirationTime)"
    }
}
