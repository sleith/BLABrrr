package com.fatdino.blabrrr.ui.landing

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.databinding.ActivityLandingBinding
import com.fatdino.blabrrr.ui.BaseActivity
import com.fatdino.blabrrr.ui.BaseViewModel
import com.fatdino.blabrrr.ui.login.LoginActivity
import com.fatdino.blabrrr.ui.signup.SignUpStep1Activity
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : BaseActivity() {

    lateinit var viewModel: LandingActivityViewModel

    override fun setupViews() {
        viewModel = mViewModel as LandingActivityViewModel
        val binding: ActivityLandingBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_landing)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpStep1Activity::class.java))
        }

        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun setupObservers() {

    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider(this).get(LandingActivityViewModel::class.java)
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}