package com.kshetra.hotel.network

import com.accepto.docall.network.models.login.LoginRequest
import com.accepto.docall.network.models.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface API {
    @POST(ApiUrl.LOGIN)
    fun performLogin(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    /*@Header("OSVERSION") osVersion: String,
        @Header("APPVERSION") appVersion: String,*/
}