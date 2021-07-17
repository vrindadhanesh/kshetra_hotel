package com.kshetra.hotel.ui.activity.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.accepto.docall.network.models.login.LoginResponse
import com.denzcoskun.imageslider.models.SlideModel
import com.kshetra.hotel.R
import com.kshetra.hotel.databinding.ActivityHomeBinding
import com.kshetra.hotel.ui.activity.customerfeed.CustomerFeedActivity
import com.kshetra.hotel.ui.activity.userlist.UserListActivity
import com.kshetra.hotel.utils.BaseUtil

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.handler = homeHandler
        val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(SlideModel("https://bit.ly/2YoJ77H", "The animal population decreased by 58 percent in 42 years."))
        imageList.add(SlideModel("https://bit.ly/2BteuF2", "Elephants and tigers may become extinct."))
        imageList.add(SlideModel("https://bit.ly/3fLJf72", "And people do that."))

        binding.imageSlider.setImageList(imageList)
    }
    var homeHandler=HomeHandler(object :HomeHandler.HomeHandlerListener{
        override fun goToLogin(view: View) {
            goToLoginScreen()
        }

        override fun goToCustomerFeedForm(view: View) {
            goToCustomerDetailsScreen()
        }

        override fun performLogin(view: View) {
            doLogin()
        }

    })

    private fun doLogin() {
        if (BaseUtil.isConnectedToNetwork(this)) {
            //if (viewModel.checkEmptyEntries()) {
                viewModel.doLogin().observe(this,
                    Observer<LoginResponse?> { it ->
                        it?.let {
                            viewModel.loginResponse = it
                            /*LoginPrefHandler.saveIsLoggedIn(true)
                            LoginPrefHandler.saveUserToken(it.data.token)
                            CallLogPrefHandler.saveUserDndValue((it.data.do_not_disturb))
                            CallLogPrefHandler.saveUserSipDndValue(it.data.sip_status)*/
                            viewModel.getAndUpdateToken()
                            val intent = Intent(applicationContext, UserListActivity::class.java)
                            //intent.putExtra(UserListActivity.isFromLogin, true)
                            startActivity(intent)
                            finish()
                        }
                    })
            }
        /*} else {
            viewModel.showMessage.value = MessageBean(
                resources.getString(R.string.network_not_available),
                Toast.LENGTH_LONG,
                MESSAGE_TYPE_TOAST
            )
        }*/
    }

    private fun goToCustomerDetailsScreen() {
        val intent = Intent(applicationContext, CustomerFeedActivity::class.java)
        intent.putExtra(CustomerFeedActivity.isFromLogin, true)
        startActivity(intent)
    }

    private fun goToLoginScreen() {
        viewModel.isLoginClicked.set(true)
    }
}