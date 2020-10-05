package com.fatdino.blabrrr.ui.splash

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.databinding.ActivitySplashBinding
import com.fatdino.blabrrr.ui.BaseActivity
import com.fatdino.blabrrr.ui.BaseViewModel

class SplashActivity : BaseActivity() {

    lateinit var viewModel: SplashActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = mViewModel as SplashActivityViewModel
        val binding: ActivitySplashBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.viewModel = viewModel

        viewModel.start(this)
    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider(this).get(SplashActivityViewModel::class.java)
    }

}