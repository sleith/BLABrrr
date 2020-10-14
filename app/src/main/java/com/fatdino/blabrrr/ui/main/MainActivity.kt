package com.fatdino.blabrrr.ui.main

import android.content.Intent
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.databinding.ActivityMainBinding
import com.fatdino.blabrrr.injection.component.AppComponent
import com.fatdino.blabrrr.ui.BaseActivity
import com.fatdino.blabrrr.ui.BaseViewModel
import com.fatdino.blabrrr.ui.post.PostActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

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

        ibPost.setOnClickListener {
            startActivity(Intent(this, PostActivity::class.java))
        }
    }

    override fun onBackPressed() {
        viewModel.backPressed()
    }

    override fun setupObservers() {
        viewModel.callbackMoveAppToBg.observe(this, {
            if (it) {
                moveTaskToBack(true)
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.main_back_press_confirmation),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun injectAppComponent(appComponent: AppComponent) {
        appComponent.inject(viewModel)
    }
}