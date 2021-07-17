package com.kshetra.hotel.ui.activity.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.accepto.docall.network.models.login.LoginRequest
import com.accepto.docall.network.models.login.LoginResponse
import com.kshetra.hotel.core.ErrorBean
import com.kshetra.hotel.network.NetworkRepository
import com.kshetra.hotel.ui.core.BaseRepository

class HomeRepository : BaseRepository() {

    fun doLogin(loginRequest: LoginRequest): LiveData<LoginResponse> {
        return Transformations.map(NetworkRepository.performLogin(loginRequest)) {
            var loginResponse: LoginResponse? = null
            it?.let {
                if (it.status && it.responseBody != null) {
                    if (it.responseBody!!.error) {
                        errorData.value = ErrorBean(
                            it.responseBody!!.message,
                            it.responseBody!!.message,
                            it.responseBody!!.error_code
                        )
                    } else {
                        //Server response's status true
                        loginResponse = it.responseBody

                    }
                } else {
                    /*Either Response is null or api call is failed*/
                    errorData.value = ErrorBean("API Failed", "Login Failed")
                    loginResponse = null
                }
            }
            return@map loginResponse


        }
    }

}