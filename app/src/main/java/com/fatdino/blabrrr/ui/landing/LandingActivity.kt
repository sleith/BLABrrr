package com.fatdino.blabrrr.ui.landing

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.databinding.ActivityLandingBinding
import com.fatdino.blabrrr.ui.BaseActivity
import com.fatdino.blabrrr.ui.BaseViewModel

class LandingActivity : BaseActivity() {

    lateinit var viewModel: LandingActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun setupViews() {
        viewModel = mViewModel as LandingActivityViewModel
        val binding: ActivityLandingBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_landing)
        binding.viewModel = viewModel
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