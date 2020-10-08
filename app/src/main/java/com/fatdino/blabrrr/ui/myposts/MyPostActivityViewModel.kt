package com.fatdino.blabrrr.ui.myposts

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.api.model.responds.PostListResp
import com.fatdino.blabrrr.api.service.ApiPostService
import com.fatdino.blabrrr.storage.MySharedPreferences
import com.fatdino.blabrrr.ui.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MyPostActivityViewModel : BaseViewModel() {

    @Inject
    lateinit var postService: ApiPostService

    @Inject
    lateinit var myPreferences: MySharedPreferences

    var posts: MutableLiveData<ArrayList<Post>> = MutableLiveData(ArrayList())
    var noPostVisibilityBoolean: MutableLiveData<Boolean> = MutableLiveData(false)

    var callbackLogoutSuccess: MutableLiveData<Boolean> = MutableLiveData()

    override fun start(owner: LifecycleOwner) {
        fetchPosts()
    }

    private fun fetchPosts() {
        subscription.add(postService.getUserPosts(myPreferences.username ?: "")
            .subscribeOn(Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()
            ).doOnSubscribe {
                loadingVisibility.value = true
            }.doOnTerminate {
                loadingVisibility.value = false
            }.doOnError {
                PostListResp(it.localizedMessage)
            }.subscribe {
                if (it.isSuccessful) {
                    posts.value?.let { postList ->
                        postList.addAll(it.posts)
                        posts.value = postList
                    }

                    noPostVisibilityBoolean.value = posts.value.isNullOrEmpty()
                } else {
                    errorMessage.value = it.description
                }
            })
    }

    fun doDeletePost(post: Post) {
        subscription.add(postService.deletePost(post)
            .subscribeOn(Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()
            ).doOnSubscribe {
                loadingVisibility.value = true
            }.doOnTerminate {
                loadingVisibility.value = false
            }.doOnError {
                PostListResp(it.localizedMessage)
            }.subscribe {
                if (it.isSuccessful) {
                    posts.value?.let { postList ->
                        postList.remove(post)
                        posts.value = postList
                    }
                } else {
                    errorMessage.value = it.description
                }
            })
    }

    fun doLogout() {
        myPreferences.password = ""
        myPreferences.setUser(null)
        callbackLogoutSuccess.value = true
    }
}