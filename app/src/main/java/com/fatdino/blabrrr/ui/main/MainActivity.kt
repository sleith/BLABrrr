package com.fatdino.blabrrr.ui.main

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.databinding.ActivityMainBinding
import com.fatdino.blabrrr.ui.BaseActivity
import com.fatdino.blabrrr.ui.BaseViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity() {

    lateinit var viewModel: MainActivityViewModel

    override fun setupViews() {
        viewModel = mViewModel as MainActivityViewModel
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    override fun setupObservers() {

    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }
}