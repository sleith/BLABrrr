package com.fatdino.blabrrr.ui.signup

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.databinding.ActivitySignupStep2Binding
import com.fatdino.blabrrr.ui.BaseActivity
import com.fatdino.blabrrr.ui.BaseViewModel
import kotlinx.android.synthetic.main.activity_signup_step1.*

class SignUpStep2Activity : BaseActivity() {
    lateinit var viewModel: SignUpStep2ViewModel

    override fun setupViews() {
        viewModel = mViewModel as SignUpStep2ViewModel
        val binding: ActivitySignupStep2Binding =
            DataBindingUtil.setContentView(this, R.layout.activity_signup_step2)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        ibBack.setOnClickListener {
            finish()
        }
    }

    override fun setupObservers() {

    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider(this).get(SignUpStep2ViewModel::class.java)
    }
}