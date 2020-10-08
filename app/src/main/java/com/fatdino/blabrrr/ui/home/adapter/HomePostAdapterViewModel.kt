package com.fatdino.blabrrr.ui.home.adapter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.api.model.User
import com.fatdino.blabrrr.ui.BaseViewModel
import com.fatdino.blabrrr.utils.extensions.millisToDate

class HomePostAdapterViewModel : BaseViewModel() {

    var post: MutableLiveData<Post> = MutableLiveData()
    var user: MutableLiveData<User> = MutableLiveData()

    fun bind(post: Post, user: User?) {
        this.post.value = post
        this.user.value = user
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