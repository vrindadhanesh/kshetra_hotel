package com.kshetra.hotel.ui.activity.splash

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.telecom.TelecomManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kshetra.hotel.R
import com.kshetra.hotel.utils.BaseUtil
import com.kshetra.hotel.databinding.ActivitySplashBinding
import com.kshetra.hotel.ui.activity.home.HomeActivity
import com.kshetra.hotel.ui.core.BaseActivity
/*import com.accepto.docall.R
import com.accepto.docall.core.MessageBean
import com.accepto.docall.databinding.ActivitySplashBinding
import com.accepto.docall.network.models.countrylist.CurrentCountryCode
import com.accepto.docall.sharedpreference.LoginPrefHandler
import com.accepto.docall.ui.activity.callLog.CallLogActivity
import com.accepto.docall.ui.activity.login.LoginActivity
import com.accepto.docall.ui.core.BaseActivity
import com.accepto.docall.utils.base.BaseUtil
import com.accepto.docall.utils.base.CountryListUtil*/


class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private var handler: Handler? = null
    private val SPLASHDELAY: Long = 3000 //3 seconds
    private lateinit var viewModel: SplashViewModel

    companion object {

        private const val REQUEST_CODE_SET_DEFAULT_DIALER = 123
    }

    internal val mRunnable: Runnable = Runnable {
        //viewModel.checkLoginStatus()
        navigateToLogin()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        if(BaseUtil.isConnectedToNetwork(this))
        {
            gotoNextScreen()
            //viewModel.isLoggedIn.postValue(true)
        }
        viewModel.isLoggedIn.observe(this, Observer<Boolean?> { isLoggedIn ->

            isLoggedIn?.let {
                if (it) {
                   /* viewModel.getAndUpdateToken()
                    navigateToCallLog()*/
                    navigateToLogin()
                } else {
                    navigateToLogin()
                }
            }

        })
       /* if (BaseUtil.isConnectedToNetwork(this)) {
            CountryListUtil.getCurrentCountryExtension()
                .observe(this, object : Observer<CurrentCountryCode?> {
                    override fun onChanged(it: CurrentCountryCode?) {
                        it?.let {
                            LoginPrefHandler.saveCurrentCountryCode(
                                it.countryCode!!.toLowerCase(
                                    Locale.ENGLISH
                                )
                            )
                        }
                    }
                })
        } else {
            viewModel.showMessage.value = MessageBean(
                resources.getString(R.string.network_not_available),
                Toast.LENGTH_LONG,
                MessageBean.MESSAGE_TYPE_TOAST
            )
        }
        viewModel.isLoggedIn.observe(this, Observer<Boolean?> { isLoggedIn ->

            isLoggedIn?.let {
                if (it) {
                    viewModel.getAndUpdateToken()
                    navigateToCallLog()
                } else {
                    navigateToLogin()
                }
            }

        })*/
    }


    private fun gotoNextScreen() {
        handler = Handler()
        handler!!.postDelayed(mRunnable, SPLASHDELAY)
    }

    public override fun onDestroy() {

        if (handler != null) {
            handler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
        finish()
    }

   /* private fun navigateToCallLog() {
        startActivity(Intent(this@SplashActivity, CallLogActivity::class.java))
        finish()
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_SET_DEFAULT_DIALER ->
            {}// checkSetDefaultDialerResult(resultCode)
        }
    }

    override fun onResume() {
        super.onResume()

        if (isAboveMarshmallow()) {
            if (!viewModel.isFromDefaultHandlerDialogResult) {
                if (isDefaultPhoneHandler()) {
                    onDefaultPhoneHandler()
                } else {
                    promptDefaultHandler()
                }
            }

        } else {
            onDefaultPhoneHandler()
        }


    }

    private fun onDefaultPhoneHandler() {
        // go to login
        gotoNextScreen()
    }

    private fun isAboveMarshmallow(): Boolean {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.M
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isDefaultPhoneHandler(): Boolean {
        val telecomManager = getSystemService(TELECOM_SERVICE) as TelecomManager
        return packageName == telecomManager.defaultDialerPackage
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun promptDefaultHandler() {

        viewModel.isFromDefaultHandlerDialogResult = true

        val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER)
            .putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, packageName)
        startActivityForResult(intent, REQUEST_CODE_SET_DEFAULT_DIALER)
    }

    private fun checkSetDefaultDialerResult(resultCode: Int) {
        when (resultCode) {
            RESULT_OK -> {
                onDefaultPhoneHandler()
            }
            RESULT_CANCELED -> {

                Toast.makeText(
                    this,
                    "User declined request to become default dialer",
                    Toast.LENGTH_SHORT
                ).show()

                // Prompt user to make app as default handler via settings
                showSettingsDialog2("In order to work this app, you have to make this app as default phone handler.")
            }
            else -> throw IllegalArgumentException("Unexpected result code $resultCode")
        }

    }

    fun showSettingsDialog2(message: String) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Need Permissions")
        builder.setMessage(message)
        builder.setCancelable(false)

        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    dialog.cancel()
                    viewModel.isFromDefaultHandlerDialogResult = false
                    openSettings()
                }

            }
        }

        builder.setPositiveButton("GOTO SETTINGS", dialogClickListener)
        dialog = builder.create()
        dialog.show()
    }


}