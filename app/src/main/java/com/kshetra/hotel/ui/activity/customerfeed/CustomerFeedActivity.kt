package com.kshetra.hotel.ui.activity.customerfeed

import android.Manifest
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.datepicker.MaterialDatePicker
import com.kshetra.hotel.R
import com.kshetra.hotel.core.App
import com.kshetra.hotel.databinding.ActivityCustomerFeedBinding
import kotlinx.android.synthetic.main.activity_customer_details.view.*


class CustomerFeedActivity : AppCompatActivity() {
    private val RECORD_REQUEST_CODE = 101
    val REQUEST_CODE = 200
    private lateinit var binding: ActivityCustomerFeedBinding
    private lateinit var viewModel: CustomerFeedViewmodel
    private lateinit var picker :MaterialDatePicker<Long>
    private lateinit var pickerDepart :MaterialDatePicker<Long>

    companion object {
        var isFromLogin: String = "isFromLogin"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_feed)
        viewModel = ViewModelProviders.of(this).get(CustomerFeedViewmodel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.handler = handler
        addNewCustomerForm()
        val builder = MaterialDatePicker.Builder.datePicker()
        picker = builder.build()
        pickerDepart = builder.build()
        binding.lytCustomerDetails.txt_arrival_time.setOnClickListener(View.OnClickListener {
            picker.min(System.currentTimeMillis() - 1000);
            picker.show(supportFragmentManager, picker.toString())
        })
            binding.lytCustomerDetails.txt_departing_time.setOnClickListener(View.OnClickListener {
                pickerDepart.min
            pickerDepart.show(supportFragmentManager, picker.toString())
        })
        binding.lytCustomerDetails.btn_add.setOnClickListener(View.OnClickListener {
            setAddIsClicked()
        })
        getSelectedDate()
        setupPermissions()
    }

    private fun setAddIsClicked() {
        capturePhoto()
    }

    private fun capturePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null){
            var newView: ImageView

            newView = ImageView(this)

            newView.setImageBitmap(data.extras?.get("data") as Bitmap)
            val params = LinearLayout.LayoutParams(200, 200)
            params.setMargins(10, 0, 0, 10)
            newView.layoutParams=params
            binding.lytCustomerDetails.img_Proof.addView(newView)
        }
    }
    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            RECORD_REQUEST_CODE
        )
    }

    private fun getSelectedDate() {
        picker.addOnPositiveButtonClickListener {
            binding.lytCustomerDetails.txt_arrival_time.setText(picker.getHeaderText());
        }
        pickerDepart.addOnPositiveButtonClickListener {
            binding.lytCustomerDetails.txt_departing_time.setText(pickerDepart.getHeaderText());
        }
    }

    var handler=CustomerFeedHandler(object : CustomerFeedHandler.CustomerFeedHandlerListener {
        override fun addNewForm(view: View) {
            Toast.makeText(App.getInstance(), "add", Toast.LENGTH_SHORT).show()
            addNewCustomerForm()
        }

        override fun selectArrivalDate(view: View) {

        }
    })

    private fun addNewCustomerForm() {
        val view = layoutInflater.inflate(R.layout.activity_customer_details, null)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(30, 30, 30, 30)
        binding.run {
            layoutParams.setMargins(30, 30, 30, 30)
            lytCustomerDetails.addView(view, layoutParams)
            //scrollToView(view)
            view.requestFocus()
        }
        /*binding.run {
            scrollCustomerDetails.scrollTo(0, scrollCustomerDetails.getBottom());
        }*/
    }
    private fun scrollToView(view: View) {
        binding.scrollCustomerDetails.scrollBy(view.scrollX, view.scrollY)
        ObjectAnimator.ofInt( binding.scrollCustomerDetails, "scrollY", view.top).setDuration(700).start()
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}