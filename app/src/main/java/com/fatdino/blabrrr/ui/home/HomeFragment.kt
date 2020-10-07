package com.fatdino.blabrrr.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.databinding.FragmentHomeBinding
import com.fatdino.blabrrr.injection.component.DaggerViewModelComponent
import com.fatdino.blabrrr.injection.module.ServiceModule
import com.fatdino.blabrrr.injection.module.StorageModule
import com.fatdino.blabrrr.ui.BaseFragment
import com.fatdino.blabrrr.ui.BaseViewModel

class HomeFragment : BaseFragment() {

    lateinit var viewModel: HomeFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = mViewModel as HomeFragmentViewModel
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val injector =
            DaggerViewModelComponent.builder().serviceModule(ServiceModule()).storageModule(
                StorageModule(requireContext())
            ).build()
        injector.inject(viewModel)

        return binding.root
    }

    override fun setupObservers() {

    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
    }

}