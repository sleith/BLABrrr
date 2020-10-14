package com.fatdino.blabrrr.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.fatdino.blabrrr.MyApplication
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.injection.component.AppComponent

abstract class BaseFragment : Fragment() {
    protected var mHasBackButton = true
    lateinit var mViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = getViewModel()

        mViewModel.errorMessage.observe(this, Observer {
            it?.let { str ->
                showSimpleDialog(null, str)
            }
        })

        mViewModel.errorMessageInt.observe(this, Observer {
            it?.let { resId ->
                showSimpleDialog(null, getString(resId))
            }
        })


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()
        injectAppComponent((activity?.application as MyApplication).appComponent)
        mViewModel.start(this)
    }

    fun showSimpleDialog(title: String?, message: String) {
        val builder = AlertDialog.Builder(requireContext())
        if (title != null) {
            builder.setTitle(title)
        }
        builder.setMessage(message)
        builder.setPositiveButton(getString(R.string.OK), null)
        builder.create().show()
    }

    abstract fun setupViews()

    abstract fun setupObservers()

    abstract fun getViewModel(): BaseViewModel

    abstract fun injectAppComponent(appComponent: AppComponent)

}