package com.fatdino.blabrrr.ui.home

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.api.model.User
import com.fatdino.blabrrr.api.service.ApiPostService
import com.fatdino.blabrrr.storage.MySharedPreferences
import com.fatdino.blabrrr.ui.BaseViewModel
import com.google.gson.Gson
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomeFragmentViewModel : BaseViewModel() {

    @Inject
    lateinit var postService: ApiPostService

    @Inject
    lateinit var myPreferences: MySharedPreferences

    var user: MutableLiveData<User?> = MutableLiveData()
    var posts: MutableLiveData<ArrayList<Post>> = MutableLiveData(ArrayList())

    override fun start(owner: LifecycleOwner) {
        user.value = myPreferences.getUser()

        subscription.add(postService.getLivePosts()
            .subscribeOn(Schedulers.io())
            .observeOn(
                Schedulers.io()
            ).doOnSubscribe {
            }.doOnTerminate {
            }.doOnError {

            }.subscribe {
                if (it.isSuccessful) {
                    val gson = Gson()
                    Log.e(javaClass.simpleName, "@@ post update : " + gson.toJson(it))

                    it.post?.let { post ->
                        when {
                            it.isNew -> {
                                addNewPost(post)
                            }
                            it.isDeleted -> {
                                removePost(post.key)
                            }
                            else -> {
                                replacePost(post)
                            }
                        }
                    }

                } else {
                    errorMessage.value = it.description
                }
            })
    }

    private fun addNewPost(post: Post) {
        posts.value?.let {
            it.add(0, post)
            posts.postValue(it)
        }
    }

    private fun removePost(key: String) {
        posts.value?.let {
            for (currPost in it) {
                if (currPost.key == key) {
                    it.remove(currPost)
                    posts.postValue(it)
                    break
                }
            }
        }
    }

    private fun replacePost(post: Post) {
        posts.value?.let {
            var index = 0
            for (currPost in it) {
                if (currPost.key == post.key) {
                    it.remove(currPost)
                    it.add(index, post)
                    posts.postValue(it)
                    break
                }
                index++
            }
        }
    }
}