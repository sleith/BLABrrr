package com.fatdino.blabrrr.ui.post

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.api.model.responds.PostResp
import com.fatdino.blabrrr.api.service.ApiPostService
import com.fatdino.blabrrr.storage.MySharedPreferences
import com.fatdino.blabrrr.ui.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

class PostActivityViewModel : BaseViewModel() {

    @Inject
    lateinit var postService: ApiPostService

    @Inject
    lateinit var myPreferences: MySharedPreferences

    var imageFile: MutableLiveData<File> = MutableLiveData()
    var message: MutableLiveData<String> = MutableLiveData()

    var callbackPost: MutableLiveData<Post?> = MutableLiveData()

    override fun start(owner: LifecycleOwner) {

    }

    fun doPost() {
        if (message.value.isNullOrEmpty()) {
            errorMessageInt.value = R.string.post_error_empty_message
            return
        }
        val username = myPreferences.username

        subscription.add(
            postService.doPost(
                username ?: "",
                message.value ?: "",
                imageFile.value
            ).subscribeOn(Schedulers.io())
                .observeOn(
                    AndroidSchedulers.mainThread()
                ).doOnSubscribe {
                    loadingVisibility.value = true
                }.doOnTerminate {
                    loadingVisibility.value = false
                }.doOnError {
                    PostResp(it.localizedMessage)
                }.subscribe {
                    if (it.isSuccessful) {
                        callbackPost.value = it.post
                    } else {
                        errorMessage.value = it.description
                    }
                }
        )
    }
}