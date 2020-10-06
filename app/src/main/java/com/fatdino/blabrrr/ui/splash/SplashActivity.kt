package com.fatdino.blabrrr.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.databinding.ActivitySplashBinding
import com.fatdino.blabrrr.ui.BaseActivity
import com.fatdino.blabrrr.ui.BaseViewModel
import com.fatdino.blabrrr.ui.landing.LandingActivity

class SplashActivity : BaseActivity() {

    lateinit var viewModel: SplashActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setupViews() {
        viewModel = mViewModel as SplashActivityViewModel
        val binding: ActivitySplashBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    override fun setupObservers() {
        viewModel.mCallbackSplashTimedout.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, LandingActivity::class.java))
            }
        })
    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider(this).get(SplashActivityViewModel::class.java)
    }


}