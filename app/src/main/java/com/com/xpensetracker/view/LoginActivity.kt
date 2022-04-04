package com.com.xpensetracker.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import com.com.xpensetracker.R
import com.com.xpensetracker.databinding.ActivityLoginBinding
import com.com.xpensetracker.model.Category
import com.com.xpensetracker.utils.ExpenseCategoryType
import com.com.xpensetracker.utils.Status
import com.com.xpensetracker.utils.hideSoftKeyboard
import com.com.xpensetracker.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener {
    val loginViewModel: LoginViewModel by viewModels()
    lateinit var binding: ActivityLoginBinding
    var FIREBASE_LISTENER_CALLED = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initializeView()
    }

    private fun initializeView() {
        binding.fabActionDone.setOnClickListener(this)

        loginViewModel.userPreferences.isActive.asLiveData().observe(this) {
           Log.e("isActive=======","called")
            if (it&&!FIREBASE_LISTENER_CALLED) {
                FIREBASE_LISTENER_CALLED =true
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.rootView.visibility = View.VISIBLE
            }
        }

        loginViewModel.getAllCategories().observe(this, androidx.lifecycle.Observer {
            if (it.size == 0) {
                val categoryList = mutableListOf<Category>()
                categoryList.add(Category(image = R.drawable.ic_bill,title = getString(R.string.txt_bill),type = ExpenseCategoryType.BILL,color = R.color.colorBiskitRipple,rippleBackground = R.drawable.ripple_biskit))
                categoryList.add(Category(image = R.drawable.ic_utilities,title = getString(R.string.txt_utilities),type = ExpenseCategoryType.UTILITIES,color = R.color.colorBrownRipple,rippleBackground = R.drawable.ripple_brown))
                categoryList.add(Category(image = R.drawable.ic_gift,title = getString(R.string.txt_gift),type = ExpenseCategoryType.GIFT,color = R.color.colorFruciaRipple,rippleBackground = R.drawable.ripple_frucia))
                categoryList.add(Category(image = R.drawable.ic_food,title = getString(R.string.txt_food),type = ExpenseCategoryType.FOOD,color = R.color.colorOrangeRipple,rippleBackground = R.drawable.ripple_orange))
                categoryList.add(Category(image = R.drawable.ic_trips,title = getString(R.string.txt_trips),type = ExpenseCategoryType.TRIPS,color = R.color.colorPinkRipple,rippleBackground = R.drawable.ripple_pink))
                categoryList.add(Category(image = R.drawable.ic_maid_salary,title = getString(R.string.txt_maid_salary),type = ExpenseCategoryType.MAID_SALARY,color = R.color.colorGreenRipple,rippleBackground = R.drawable.ripple_green))
                categoryList.add(Category(image = R.drawable.ic_shopping,title = getString(R.string.txt_shopping),type = ExpenseCategoryType.SHOPPING,color = R.color.colorBlueRipple,rippleBackground = R.drawable.ripple_blue))
                categoryList.add(Category(image = R.drawable.ic_grocery,title = getString(R.string.txt_grocery),type = ExpenseCategoryType.GROCERY,color = R.color.colorPurpleRipple,rippleBackground = R.drawable.ripple_purple))
                categoryList.add(Category(image = R.drawable.ic_entertainment,title = getString(R.string.txt_entertainment),type = ExpenseCategoryType.ENTERTAINMENT,color = R.color.colorHiBiscusRipple,rippleBackground = R.drawable.ripple_hibiscus))
                categoryList.add(Category(image = R.drawable.ic_health,title = getString(R.string.txt_health),type = ExpenseCategoryType.HEALTH,color = R.color.colorYellowRipple,rippleBackground = R.drawable.ripple_yellow))

                loginViewModel.insertAllCategory(categoryList)

            }
        })

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.fabActionDone -> {
                showProgressbar()
                hideFloatingButton()
                hideSoftKeyboard(binding.rootView)
                login()
            }
        }
    }

    fun hideFloatingButton(){
        binding.fabActionDone.hide()
    }


    fun showFloatingButton(){
        binding.fabActionDone.show()
    }

    private fun showProgressbar() {
        binding.progressbar.visibility = View.VISIBLE
    }


    private fun hideProgressbar() {
        binding.progressbar.visibility = View.GONE
    }

    private fun login() {
        loginViewModel.login(binding.edtEditName.text.toString())
            .observe(this, androidx.lifecycle.Observer {
                if (it.status == Status.SUCCESS) {
                    Log.e("SUCCESS=======","called")
                    hideProgressbar()
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                } else if (it.status == Status.ERROR) {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    showFloatingButton()
                    hideProgressbar()
                } else if (it.status == Status.LOADING) {
                    showFloatingButton()
                    hideProgressbar()
                    Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                }
            })
    }
}