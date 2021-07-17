package com.kshetra.hotel.ui.core

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kshetra.hotel.core.MessageBean
import java.util.*

/**
 * Created by Vrinda R Babu
 */
open class BaseViewModel : ViewModel() {
    var progressStatus = MutableLiveData<Boolean>()
    var showMessage = MutableLiveData<MessageBean>()

    var isSettingsDialogOpen:Boolean = false
    var isGotoSettingsDialogOpen:Boolean = false

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun getAndUpdateToken() {
        /*   var accessToken = object : AccessToken(App.getInstance()) {

               override fun onPreExecute() {
                   super.onPreExecute()
               }
               override fun onPostExecute(resulTtoken: String?) {
                   super.onPostExecute(resulTtoken)
                   if (resulTtoken != null) {

                   }
                Log.e("Result", resulTtoken)
               }
           }
   */

      /*  FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("HSHSH", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                var token = task.result?.token

                // Log and toast
                val msg = "TOKEN=" + token
                Log.d("TOKEN", msg)
                token?.let {
                    FirebaseMessagingRepository()
                        .updateTokenToServer(token, LoginPrefHandler.getUserToken())
                }

                //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })*/

        //accessToken.execute()
    }

}