package com.fatdino.blabrrr.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.fatdino.blabrrr.R

abstract class BaseActivity : AppCompatActivity() {
    var mHasBackButton = true
    lateinit var mViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(mHasBackButton)
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

    fun showSimpleDialog(title: String?, message: String) {
        val builder = AlertDialog.Builder(this)
        if (title != null) {
            builder.setTitle(title)
        }
        builder.setMessage(message)
        builder.setPositiveButton(getString(R.string.OK), null)
        builder.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    abstract fun getViewModel(): BaseViewModel

}