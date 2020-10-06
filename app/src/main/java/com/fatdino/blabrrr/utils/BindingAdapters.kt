package com.fatdino.blabrrr.utils

import android.content.ContextWrapper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.fatdino.blabrrr.R
import java.io.File

fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, url: String) {
    Glide.with(view).load(url).into(view)
}

@BindingAdapter("imageFile")
fun setImageFile(view: ImageView, file: File?) {
    Glide.with(view).load(file).placeholder(R.drawable.ic_emptyprofile)
        .error(R.drawable.ic_emptyprofile).into(view)
}

@BindingAdapter("imageFileHideIfNull")
fun setImageFileHideIfNull(view: ImageView, file: File?) {
    if (file != null) {
        Glide.with(view).load(file).placeholder(R.drawable.ic_emptyprofile)
            .error(R.drawable.ic_emptyprofile).into(view)
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}