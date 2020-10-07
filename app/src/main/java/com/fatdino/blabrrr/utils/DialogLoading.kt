package com.fatdino.blabrrr.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.fatdino.blabrrr.R

class DialogLoading(private val context: Context) {

    var mAlertDialog: AlertDialog? = null
    var mTxtLoading: TextView? = null

    fun show(message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(true)

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
        mTxtLoading = view.findViewById(R.id.txtLoading)
        mTxtLoading?.text = message
        builder.setView(view)

        mAlertDialog = builder.show()
    }

    fun dismiss() {
        mAlertDialog?.dismiss()
    }
}