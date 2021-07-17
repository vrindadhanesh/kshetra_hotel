package com.kshetra.hotel.ui.activity.customerfeed

import android.view.HapticFeedbackConstants
import android.view.View

class CustomerFeedHandler(private var customerFeedHandlerListener: CustomerFeedHandlerListener) {

    fun addNewForm(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        //mVibrator.vibrate(25);
        customerFeedHandlerListener.addNewForm(view)
    }
    fun selectArrivalDate(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        //mVibrator.vibrate(25);
        customerFeedHandlerListener.selectArrivalDate(view)
    }

    interface CustomerFeedHandlerListener {
        fun addNewForm(view: View)
        fun selectArrivalDate(view: View)
    }
}