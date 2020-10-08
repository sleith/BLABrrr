package com.fatdino.blabrrr.utils

import android.content.ContextWrapper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.api.model.User
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

@BindingAdapter("avatarUser")
fun setImageUser(view: ImageView, user: User?) {
    //TODO: set with image url
    Glide.with(view).load(user?.name).placeholder(R.drawable.ic_logo)
        .error(R.drawable.ic_logo).into(view)
}

@BindingAdapter("postImage")
fun setImagePost(view: ImageView, post: Post) {
    if (post.filePath.isNullOrEmpty()) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
        Glide.with(view).load(post.filePath).into(view)
    }
}

@BindingAdapter("mutableVisibilityBoolean")
fun setMutableVisibilityBoolean(view: View, isVisible: MutableLiveData<Boolean>) {
    val parentActivity = view.getParentActivity()
    parentActivity?.let { activity ->
        isVisible.observe(activity, {
            view.visibility = if (it) View.VISIBLE else View.GONE
        })
    }
}
