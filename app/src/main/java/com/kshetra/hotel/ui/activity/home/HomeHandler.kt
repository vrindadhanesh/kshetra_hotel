package com.kshetra.hotel.ui.activity.home

import android.view.HapticFeedbackConstants
import android.view.View

/**
 * Created by Vrinda R Babu
 */
class HomeHandler(private var loginHandlerListener: HomeHandlerListener) {

    fun goToLogin(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        //mVibrator.vibrate(25);
        loginHandlerListener.goToLogin(view)
    }

   fun goToCustomerFeedForm(view: View) {
        loginHandlerListener.goToCustomerFeedForm(view)
    }


    fun performLogin(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        //mVibrator.vibrate(25);
        loginHandlerListener.performLogin(view)
    }
    /*
       fun goToForgotPassword(view: View) {
           loginHandlerListener.goToForgotPassword(view)
       }

       fun performCancel(view: View) {
           loginHandlerListener.goToCancel(view)
       }

       fun performRequestOtpForForgot(view: View) {
           loginHandlerListener.performRequestOtpForForgot(view)
       }
   */

    interface HomeHandlerListener {
        fun goToLogin(view: View)
        fun goToCustomerFeedForm(view: View)
        fun performLogin(view: View)
        /* fun goToForgotPassword(view: View)
        fun goToCancel(view: View)
        fun performRequestOtpForForgot(view: View)*/
    }

}