package com.kshetra.hotel.ui.core

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.kshetra.hotel.R
import com.kshetra.hotel.core.ErrorBean
import com.kshetra.hotel.core.MessageBean
import com.kshetra.hotel.ui.activity.home.HomeActivity
//import com.accepto.docall.R
//import com.accepto.docall.core.ErrorBean
//import com.accepto.docall.core.MessageBean
//import com.accepto.docall.network.models.savecall.SaveCallHistoryResponse
//import com.accepto.docall.ui.activity.login.LoginActivity
//import com.accepto.docall.utils.TimeUtil
//import com.accepto.docall.utils.base.BaseUtil
//import com.google.android.material.snackbar.Snackbar
//import com.karumi.dexter.Dexter
//import com.karumi.dexter.MultiplePermissionsReport
//import com.karumi.dexter.PermissionToken
//import com.karumi.dexter.listener.PermissionRequest
//import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.util.*


abstract class BaseActivity : AppCompatActivity() {


    companion object {
        const val REQUEST_PERMISSIONS: Int = 101
    }

    private var viewModel: BaseViewModel? = null
    private var permissionsListener: OnPermissionGrantedListener? = null
    private val REQUEST_READ_PHONE_STATE = 2
    private val REQUEST_PHONE_CALL = 1
   // var refreshReceiver: RefreshReceiver? = null
    var refreshListener: RefreshListener? = null

    lateinit var progress: View

    lateinit var dialog: AlertDialog

    private var startTime: Calendar? = null
    private var duration: Long = 0

    private val snackBarDismissOnClickListener: View.OnClickListener =
        View.OnClickListener { view ->
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            //mVibrator.vibrate(25);
            view.visibility = View.GONE
        }


    fun setPermissionListener(permissionsListener: OnPermissionGrantedListener) {
        this.permissionsListener = permissionsListener
    }


    fun setProgressVisibilityStatus(progressVisibility: LiveData<Boolean>, view: View) {
        this.progress = view
        progressVisibility.observe(this, Observer<Boolean?> {
            it?.let {
                if (it) {
                    progress.visibility = View.VISIBLE
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    )
                } else {
                    progress.visibility = View.GONE
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }
            }
        })
    }

    fun initMessageBox(view: View, message: LiveData<MessageBean>) {
        message.observe(this, Observer<MessageBean?> {
            it?.let {
                if (it.type == MessageBean.MESSAGE_TYPE_SNACK_BAR) {
                    Snackbar.make(view, it.message, it.time)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show()
                } else if (it.type == MessageBean.MESSAGE_TYPE_DIALOG) {
                    //popUpDialog()
                } else {
                    Toast.makeText(applicationContext, it.message, it.time).show()
                }
            }
        })
    }

    fun setViewModel(viewModel: BaseViewModel) {
       // refreshReceiver = RefreshReceiver()
        this.viewModel = viewModel
        /*val filter = IntentFilter()
        filter.addAction(BaseUtil.ACTION_RECEIVER_WITHOUT_SIM)
        registerReceiver(refreshReceiver, filter)*/
    }

    fun initErrorBean(view: View) {
        BaseRepository.errorData.observe(this, Observer<ErrorBean?> {
            it?.let {
                if (it.error_code.equals(403)) {
                    setErrorDialog()
                    /* Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT)
                         .show()*/
                } else {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
                BaseRepository.errorData.value = null
            }
        })
    }

    private fun setErrorDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Session Expired")

        builder.setMessage("Please re-login to renew your session.")

        builder.setPositiveButton("OK") { dialog, which ->
            cleraAllPreferenceValues()
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }
        val dialog: AlertDialog = builder.create()

        dialog.show()
    }

    private fun cleraAllPreferenceValues() {
        val preferences = getSharedPreferences("com.accepto.docall", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.commit()
        finish()
    }


   /* fun checkPermissionCall(permissionFrom: Int) {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECORD_AUDIO

            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    permissionsListener?.onPermissionGrantedDoLogin(
                        report.areAllPermissionsGranted(),
                        permissionFrom
                    )

                    viewModel?.isSettingsDialogOpen = false

                    val isShown = viewModel?.isGotoSettingsDialogOpen ?: false

                    // check for permanent denial of any permission
                    if (!isShown && report.isAnyPermissionPermanentlyDenied) {
                        showSettingsDialog("This app needs permission to use this feature. You can grant them in app settings.")
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener { error ->
                Toast.makeText(
                    applicationContext,
                    "Error occurred! $error",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel?.isSettingsDialogOpen = false
            }
            .onSameThread()
            .check()

        if (viewModel != null && !viewModel!!.isSettingsDialogOpen) {
            viewModel!!.isSettingsDialogOpen = true
        }

    }*/

    fun showSettingsDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Need Permissions")
        builder.setMessage(message)
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    dialog.cancel()
                    openSettings()
                    viewModel?.isGotoSettingsDialogOpen = false
                }
//                DialogInterface.BUTTON_NEUTRAL -> {
//                    dialog.cancel()
//                }
            }
        }

        builder.setPositiveButton("GOTO SETTINGS", dialogClickListener)
        //builder.setNegativeButton("NO",dialogClickListener)
        //builder.setNeutralButton("Cancel", dialogClickListener)
        dialog = builder.create()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        viewModel?.isGotoSettingsDialogOpen = true


    }

    // navigating user to app settings
    fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    interface OnPermissionGrantedListener {
        fun onPermissionGrantedDoLogin(isGranted: Boolean, permissionFrom: Int)
    }

   /* inner class RefreshReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            //Need to register in manifest also.
            //initDatePicker()
            startTime = Calendar.getInstance()
            duration = TimeUtil.timeBetween(startTime, startTime)
            viewModel!!.performSaveCallHistory(startTime!!, duration, intent)
                .observe(this@BaseActivity,
                    Observer<SaveCallHistoryResponse> { it ->
                        it?.let {
                            refreshListener?.doRefreshActivity()
                        }
                    })
            //Toast.makeText(App.getInstance(), "Received", Toast.LENGTH_SHORT).show();
        }
    }*/

    override fun onDestroy() {
        super.onDestroy()
      /*  if (refreshReceiver != null) {
            unregisterReceiver(refreshReceiver)
        }*/
    }

    protected fun checkForPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            !(ActivityCompat.checkSelfPermission(
                this, Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(
                this, Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(
                this, Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
                    )
        } else {
            return true
        }
    }

    protected fun getAllPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val permissions = arrayOf(
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.RECORD_AUDIO
                )

                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var showRationale = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requestCode == REQUEST_PERMISSIONS) {
                val hasAllPermissions = grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED

                for (i in permissions.indices) {
                    val permission = permissions[i]
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        // user rejected the permission
                        showRationale = shouldShowRequestPermissionRationale(permission)
                        if (showRationale)
                            break
                    }
                }
                permissionsListener?.onPermissionGrantedDoLogin(
                    hasAllPermissions,
                    3
                )

                viewModel?.isSettingsDialogOpen = false

                val isShown = viewModel?.isGotoSettingsDialogOpen ?: false

                // check for permanent denial of any permission
                if (!isShown && showRationale) {
                    showSettingsDialog(getString(R.string.message_app_needs_permission))
                }
            }
        }

    }


}

interface RefreshListener {
    fun doRefreshActivity()
}
