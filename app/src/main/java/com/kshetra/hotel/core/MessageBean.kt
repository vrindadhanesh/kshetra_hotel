package com.kshetra.hotel.core

import java.io.Serializable

/**
 * Created by Vrinda R Babu
 */
data class MessageBean(
    var message: String = "",
    var time: Int = 0,
    var type: Int = 0
) : Serializable {
    companion object {
        const val MESSAGE_TYPE_TOAST = 0
        const val MESSAGE_TYPE_SNACK_BAR = 1
        const val MESSAGE_TYPE_DIALOG = 2
    }
}