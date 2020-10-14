package com.fatdino.blabrrr.ui.login

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.databinding.ActivityLoginBinding
import com.fatdino.blabrrr.injection.component.AppComponent
import com.fatdino.blabrrr.ui.BaseActivity
import com.fatdino.blabrrr.ui.BaseViewModel
import com.fatdino.blabrrr.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    lateinit var viewModel: LoginActivityViewModel

    override fun setupViews() {
        viewModel = mViewModel as LoginActivityViewModel
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        ibBack.setOnClickListener {
            finish()
        }

        btnLogin.setOnClickListener {
            viewModel.doLogin()
        }
    }

    override fun setupObservers() {
        viewModel.callbackLogin.observe(this, Observer {
            it?.let {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
        })
    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider(this).get(LoginActivityViewModel::class.java)
    }

    override fun injectAppComponent(appComponent: AppComponent) {
        appComponent.inject(viewModel)
    }
}