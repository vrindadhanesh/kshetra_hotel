package com.kshetra.hotel.ui.activity.splash

import androidx.lifecycle.MutableLiveData
import com.kshetra.hotel.ui.core.BaseViewModel


/*
 *   Created by Vrinda R Babu
*/
class SplashViewModel : BaseViewModel() {

    var isFromDefaultHandlerDialogResult: Boolean = false
    var isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()


    fun checkLoginStatus() {
       /* val loginData = LoginPrefHandler.getUserToken()
        isLoggedIn.value = loginData != ""*/
    }

}