package com.kshetra.hotel.ui.core

import androidx.lifecycle.MutableLiveData
import com.kshetra.hotel.core.ErrorBean

/**
 * Created by Vrinda R Babu
 */
open class BaseRepository {

    companion object {
        @JvmStatic
        var errorData: MutableLiveData<ErrorBean> = MutableLiveData()
    }
}