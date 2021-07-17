package com.accepto.docall.network.models.login

import com.google.gson.annotations.SerializedName

/**
 * Created by Vrinda R Babu
 */
class LoginRequest {

    @SerializedName("identity")
    var identity: String = ""
    @SerializedName("password")
    var password: String = ""
}