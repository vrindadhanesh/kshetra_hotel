package com.accepto.docall.network.models.login

import com.accepto.docall.network.models.core.BaseResponse
import com.google.gson.annotations.SerializedName

/**
 * Created by Vrinda R Babu
 */


class LoginResponse : BaseResponse() {

    @SerializedName("data")
    var data: LoginData = LoginData()
}


class LoginData {
    @SerializedName("token")
    var token: String = ""
    @SerializedName("do_not_disturb")
    var do_not_disturb: String = ""
    @SerializedName("sip_status")
    var sip_status: String = ""

}


/*class LoginResponse
{
    @SerializedName("id")
    var userID: String = ""
    @SerializedName("firstName")
    var email: String = ""
    @SerializedName("lastName")
    var firstName: String = ""
    @SerializedName("image")
    var lastName: String = ""
}*/



