package com.kshetra.hotel.utils

import android.content.Context
import android.net.ConnectivityManager

class BaseUtil {
    companion object{
        @JvmStatic
        fun isConnectedToNetwork(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            var activeNetwork = connectivityManager?.activeNetworkInfo
            if (activeNetwork != null) {
                return true
            }
            return false
        }
    }
}