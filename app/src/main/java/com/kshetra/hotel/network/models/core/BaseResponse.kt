package com.accepto.docall.network.models.core

import com.google.gson.annotations.SerializedName

/**
 * Created by Vrinda R Babu
 */
open class BaseResponse {

    @SerializedName("error")
    var error: Boolean = false;
    @SerializedName("error_code")
    var error_code: Int = 0
    @SerializedName("message")
    var message: String = ""
}