package com.kshetra.hotel.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.accepto.docall.network.models.core.APIResponseBean
import com.accepto.docall.network.models.core.BaseResponse
import com.accepto.docall.network.models.login.LoginRequest
import com.accepto.docall.network.models.login.LoginResponse
import com.kshetra.hotel.network.ApiClient.getAPI
import com.kshetra.hotel.ui.core.BaseRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NetworkRepository: BaseRepository() {

    fun getApi(): API = ApiClient.getAPI()
    /*fun getTwilioApi(): TwilioAPI =
        ApiClient.getClientWithCustomBaseUrl(ApiClient.BASE_URL_TWELIO).create(TwilioAPI::class.java)

    fun getCurrentCountryCodeApi(): CountrycodeAPI =
        ApiClient.getClientWithCustomBaseUrl(ApiClient.BASE_URL_COUNTRY_CODE).create(CountrycodeAPI::class.java)*/

    const val OS_VERSION = "1"
    const val APP_VERSION = "234"


    //Login api call
    fun performLogin(loginRequest: LoginRequest): LiveData<APIResponseBean<LoginResponse>> {
        var responseData = MutableLiveData<APIResponseBean<LoginResponse>>()
        val call = getAPI().performLogin(loginRequest)//OS_VERSION, APP_VERSION,
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response != null) {
                    if (response.code() == 403) {
                        // errorData.value = ErrorBean(response.message(), response.message())

                        val loginResponse = LoginResponse()
                        setErrorBean(
                            loginResponse,
                            response.code(),
                            true,
                            "Authentication Failed!!"
                        )

                        responseData.postValue(
                            APIResponseBean(
                                true, loginResponse

                            )
                        )
                    } else {
                        responseData.postValue(
                            APIResponseBean(
                                true,
                                response.body()
                            )
                        )
                    }
                } else
                    responseData.postValue(
                        APIResponseBean(
                            false,
                            null
                        )
                    )
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                responseData.postValue(
                    APIResponseBean(
                        false,
                        null
                    )
                )
            }
        })
        return responseData
    }
    fun setErrorBean(
        responseClass: BaseResponse,
        code: Int,
        error: Boolean,
        message: String
    ) {
        responseClass.error_code = code
        responseClass.error = error
        responseClass.message = message
    }


}