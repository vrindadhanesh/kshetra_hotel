package com.kshetra.hotel.core

import android.app.Application
import com.kshetra.hotel.core.Config
import java.util.*


/**
 * Created by Vrinda R Babu
 */
class App : Application() {

    /* init {
         instance =this
     }

     companion object {
         private var instance: App? = null
         var context: Context? = null

         fun getApplicationContext() : Context {
             return instance!!.applicationContext
         }
     }


     override fun onCreate() {
         super.onCreate()
         FirebaseApp.initializeApp(this)
         context= App.getApplicationContext()
     }*/

    companion object {
        private var instance: App? = null

        @JvmStatic
        fun getInstance(): App {

            if (instance == null)
                instance = App()
            return instance as App
        }

        @JvmStatic
        fun getCurrentLocale(): Locale {
            return Locale(Config.getInstance().getLocale())
        }

        @JvmStatic
        fun getLocale(lang: String): Locale {
            return Locale(lang)
        }

        @JvmStatic
        fun setLocale(lang: String) {
            val myLocale = Locale(lang)
            val res = App.getInstance().resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = myLocale
            res.updateConfiguration(conf, dm)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //BugMailer.init(this, BugMailerConfig("vrinda.b@thinkpalm.com"))

        //Stetho.initializeWithDefaults(this);


        /*val client = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()*/
    }


}