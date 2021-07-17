package com.kshetra.hotel.ui.activity.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.accepto.docall.network.models.login.LoginRequest
import com.accepto.docall.network.models.login.LoginResponse
import com.kshetra.hotel.ui.core.BaseViewModel
import com.kshetra.hotel.utils.EmptyCheck


/**
 * Created by Vrinda R Babu
 */
class HomeViewModel : BaseViewModel() {

    var isLoginClicked = ObservableBoolean(false)

    //private var loginRepository: LoginRepository = LoginRepository()
    var email = ObservableField("")
    var password = ObservableField("")
    //private var forgotPasswordRequest =
        //ForgotPasswordRequest()
    var emailForForgot = ObservableField("")

    var emailError = ObservableField("")
    var passwordError = ObservableField("")
    var emailForForgotError = ObservableField("")

    private var homeRepository: HomeRepository = HomeRepository()
    private val loginRequest = LoginRequest()
    var loginResponse: LoginResponse? = null
        set(value) {
            field = value
            value?.let {
                //saveLoginResponse(loginRequest,it)
            }
        }


    fun doLogin(): LiveData<LoginResponse> {
        progressStatus.value = true
        loginRequest.identity = EmptyCheck.getNonNull(email.get())
        loginRequest.password = EmptyCheck.getNonNull(password.get())
        //return loginRepository.doLogin(loginRequest)
        return Transformations.map(homeRepository.doLogin(loginRequest))
        {
            var loginResponse: LoginResponse? = null
            it?.let {
                loginResponse = it
                /*   showMessage.value = MessageBean(
                       "Login successfully to " + loginRequest.identity,
                       Toast.LENGTH_LONG,
                       MESSAGE_TYPE_TOAST
                   )*/
                progressStatus.postValue(false)
            }
            if (it == null) {
                progressStatus.postValue(false)
            }
            return@map loginResponse
        }

    }


    /*   var forgotPasswordResponse: ForgotPasswordResponse? = null
       private val loginRequest = LoginRequest()
       var loginResponse: LoginResponse? = null
           set(value) {
               field = value
               value?.let {
                   //saveLoginResponse(loginRequest,it)
               }
           }
   */

    /*fun doLogin(): LiveData<LoginResponse> {
        val loginRequest = LoginRequest()
        loginRequest.email = EmptyCheck.getNonNull(email.get())
        loginRequest.password = EmptyCheck.getNonNull(password.get())
       return Transformations.map(performLogin(loginRequest)) {
            var loginResponse: LoginResponse? = null
            it?.let {
                if (it.status && it.responseBody != null) {
                    loginResponse = it.responseBody
                }
            }
            return@map loginResponse
        }
    }*/

    /*init {
        loginRepository.errorData.observeForever {
            it?.let {
                showMessage.value =
                    MessageBean(it.message, Toast.LENGTH_LONG, MessageBean.MESSAGE_TYPE_TOAST)
                loginRepository.errorData.value = null
                showMessage.value = null
            }
        }
    }*/

   /* fun doLogin(): LiveData<LoginResponse> {
        progressStatus.value = true
        loginRequest.identity = EmptyCheck.getNonNull(email.get())
        loginRequest.password = EmptyCheck.getNonNull(password.get())
        //return loginRepository.doLogin(loginRequest)
        return Transformations.map(loginRepository.doLogin(loginRequest))
        {
            var loginResponse: LoginResponse? = null
            it?.let {
                loginResponse = it
                *//*   showMessage.value = MessageBean(
                       "Login successfully to " + loginRequest.identity,
                       Toast.LENGTH_LONG,
                       MESSAGE_TYPE_TOAST
                   )*//*
                progressStatus.postValue(false)
            }
            if (it == null) {
                progressStatus.postValue(false)
            }
            return@map loginResponse
        }

    }
*/
   /* fun requestForgotPasswordOtp(): LiveData<ForgotPasswordResponse> {

        progressStatus.value = true
        forgotPasswordRequest.email = EmptyCheck.getNonNull(emailForForgot.get())
        //return loginRepository.doLogin(loginRequest)
        return Transformations.map(
            loginRepository.doOtpRequestForForgotPassword(
                forgotPasswordRequest
            )
        ) {
            var forgotPasswordResponse: ForgotPasswordResponse? = null
            it?.let {
                forgotPasswordResponse = it

                showMessage.value =
                    MessageBean(it.message, Toast.LENGTH_LONG, MESSAGE_TYPE_TOAST)

                progressStatus.postValue(false)
            }
            if (it == null) {
                progressStatus.postValue(false)
            }
            return@map forgotPasswordResponse
        }
    }*/

    /*fun checkEmptyEntries(): Boolean {
        var isValid = true
        val strEmail = email.get()
        val strPassword = password.get()

        if (TextUtils.isEmpty(strEmail)) {
            emailError.set(App.getInstance().getString(R.string.txt_enter_email))
            isValid = false
        } else if (!isEmailValid(strEmail!!)) {
            emailError.set(App.getInstance().getString(R.string.txt_not_valid_email))
            isValid = false
        } else {
            emailError.set("")
        }
        if (TextUtils.isEmpty(strPassword)) {
            passwordError.set(App.getInstance().getString(R.string.txt_enter_password))
            isValid = false

        } else if (strPassword!!.length < 3 || strPassword.length > 18) {
            passwordError.set(App.getInstance().getString(R.string.txt_enter_valid_password))
            isValid = false
        } else {
            passwordError.set("")
        }

        emailError.notifyChange()
        passwordError.notifyChange()
        return isValid
    }

    fun checkValidEmailId(): Boolean {
        var isValid = true
        if (emailForForgot.get().isNullOrBlank()) {
            emailForForgotError.set(App.getInstance().getString(R.string.txt_enter_email))
            isValid = false
        } else if (!isEmailValid(emailForForgot.get().toString())) {
            emailForForgotError.set(App.getInstance().getString(R.string.txt_not_valid_email))
            isValid = false
        } else {
            emailForForgotError.set("")
        }

        return isValid
    }*/


}