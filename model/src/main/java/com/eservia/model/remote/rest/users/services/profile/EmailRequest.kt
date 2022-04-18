package com.eservia.model.remote.rest.users.services.profile

import com.google.gson.annotations.SerializedName

/**
 * Created by Alexander on 28.12.2017.
 */
class EmailRequest(@SerializedName("email") var email: String? = null)
