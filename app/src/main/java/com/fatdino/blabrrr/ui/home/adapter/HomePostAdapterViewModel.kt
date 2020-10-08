package com.fatdino.blabrrr.ui.home.adapter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.ui.BaseViewModel
import com.fatdino.blabrrr.utils.extensions.millisToDate

class HomePostAdapterViewModel : BaseViewModel() {

    var post: MutableLiveData<Post> = MutableLiveData()

    fun bind(post: Post) {
        this.post.value = post
    }

    override fun start(owner: LifecycleOwner) {

    }

    fun getAuthor(): String {
        return post.value?.username ?: ""
    }

    fun getBody(): String {
        return post.value?.body ?: ""
    }

    fun getCreatedDate(): String {
        return post.value?.createdDate?.millisToDate() ?: ""
    }
}