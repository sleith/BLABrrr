package com.fatdino.blabrrr.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.fatdino.blabrrr.MyApplication
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.injection.component.AppComponent
import com.fatdino.blabrrr.utils.DialogLoading
import io.github.inflationx.viewpump.ViewPumpContextWrapper

abstract class BaseActivity : AppCompatActivity() {
    protected var mHasBackButton = true
    lateinit var mViewModel: BaseViewModel

    lateinit var mLoading: DialogLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(mHasBackButton)

        mLoading = DialogLoading(this)
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

        mViewModel.loadingVisibility.observe(this, Observer {
            if (it) {
                showWait()
            } else {
                hideWait()
            }
        })

        setupViews()
        setupObservers()
        injectAppComponent(appComponent())
        mViewModel.start(this)
    }

    fun showSimpleDialog(title: String?, message: String) {
        val builder = AlertDialog.Builder(this, R.style.MyDialog)
        if (title != null) {
            builder.setTitle(title)
        }
        builder.setMessage(message)
        builder.setPositiveButton(getString(R.string.OK), null)
        builder.create().show()
    }

    fun showWait() {
        showWait(getString(R.string.loading___))
    }

    fun showWait(message: String) {
        mLoading.show(message)
    }

    fun hideWait() {
        mLoading.dismiss()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase != null) {
            super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
        } else {
            super.attachBaseContext(newBase)
        }
    }

    fun appComponent(): AppComponent {
        return (application as MyApplication).appComponent
    }

    abstract fun setupViews()

    abstract fun setupObservers()

    abstract fun getViewModel(): BaseViewModel

    abstract fun injectAppComponent(appComponent: AppComponent)

}