package com.fatdino.blabrrr.ui.signup

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.databinding.ActivitySignupStep2Binding
import com.fatdino.blabrrr.injection.component.AppComponent
import com.fatdino.blabrrr.ui.BaseActivity
import com.fatdino.blabrrr.ui.BaseViewModel
import com.fatdino.blabrrr.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_signup_step1.ibBack
import kotlinx.android.synthetic.main.activity_signup_step2.*
import java.io.File

class SignUpStep2Activity : BaseActivity() {
    lateinit var viewModel: SignUpStep2ViewModel

    companion object {
        const val INTENT_USERNAME = "username"
        const val INTENT_AVATAR_FILEPATH = "avatar"

        fun generateIntent(context: Context, username: String, avatar: File?): Intent {
            return Intent(context, SignUpStep2Activity::class.java).apply {
                putExtra(INTENT_USERNAME, username)
                avatar?.let { file ->
                    putExtra(INTENT_AVATAR_FILEPATH, file.absolutePath)
                }
            }
        }
    }

    override fun setupViews() {
        viewModel = mViewModel as SignUpStep2ViewModel
        val binding: ActivitySignupStep2Binding =
            DataBindingUtil.setContentView(this, R.layout.activity_signup_step2)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        btnSgnUp.setOnClickListener {
            viewModel.doRegister()
        }

        ibBack.setOnClickListener {
            finish()
        }

        viewModel.username.value = intent.getStringExtra(INTENT_USERNAME)
        intent.getStringExtra(INTENT_AVATAR_FILEPATH)?.let {
            viewModel.imageFile.value = File(it)
        }

    }

    override fun setupObservers() {
        viewModel.callbackRegister.observe(this, Observer {
            it?.let {
                startActivity(Intent(this@SignUpStep2Activity, MainActivity::class.java))
            }
        })
    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider(this).get(SignUpStep2ViewModel::class.java)
    }

    override fun injectAppComponent(appComponent: AppComponent) {
        appComponent.inject(viewModel)
    }
}