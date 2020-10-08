package com.fatdino.blabrrr.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.databinding.FragmentNotificationsBinding
import com.fatdino.blabrrr.ui.BaseFragment
import com.fatdino.blabrrr.ui.BaseViewModel

class NotificationsFragment : BaseFragment() {

    lateinit var viewModel: NotificationsFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = mViewModel as NotificationsFragmentViewModel
        val binding: FragmentNotificationsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_notifications, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun setupViews() {

    }

    override fun setupObservers() {

    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider(this).get(NotificationsFragmentViewModel::class.java)
    }

}