package com.fatdino.blabrrr.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.databinding.FragmentHomeBinding
import com.fatdino.blabrrr.injection.component.DaggerViewModelComponent
import com.fatdino.blabrrr.injection.module.ServiceModule
import com.fatdino.blabrrr.injection.module.StorageModule
import com.fatdino.blabrrr.ui.BaseFragment
import com.fatdino.blabrrr.ui.BaseViewModel
import com.fatdino.blabrrr.ui.home.adapter.HomePostAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    lateinit var viewModel: HomeFragmentViewModel
    lateinit var mAdapter: HomePostAdapter

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

    override fun setupViews() {
        mAdapter = HomePostAdapter(requireActivity(), viewModel.posts.value ?: ArrayList())
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun setupObservers() {
        viewModel.posts.observe(this, Observer {
            Log.e(javaClass.simpleName, "setupObservers posts changes!")
            mAdapter.notifyDataSetChanged()
        })
    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
    }

}