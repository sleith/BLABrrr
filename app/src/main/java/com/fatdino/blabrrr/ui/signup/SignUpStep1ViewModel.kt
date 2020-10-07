package com.fatdino.blabrrr.ui.signup

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.api.model.responds.CheckUsernameAvailabilityResp
import com.fatdino.blabrrr.api.service.ApiUserService
import com.fatdino.blabrrr.storage.MySharedPreferences
import com.fatdino.blabrrr.ui.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

class SignUpStep1ViewModel : BaseViewModel() {

    @Inject
    lateinit var userService: ApiUserService

    @Inject
    lateinit var myPreferences: MySharedPreferences

    var imageFile: MutableLiveData<File> = MutableLiveData()
    var username: MutableLiveData<String> = MutableLiveData("")
    var callbackIsUsernameAvailable: MutableLiveData<Boolean> = MutableLiveData()

    override fun start(owner: LifecycleOwner) {

    }

    fun checkUsernameAvailability() {
        if (username.value.isNullOrEmpty()) {
            errorMessageInt.value = R.string.sus1_err_username_empty
            return
        }
        subscription.add(
            userService.doCheckUsernameAvailable(username.value ?: "").subscribeOn(Schedulers.io())
                .observeOn(
                    AndroidSchedulers.mainThread()
                ).doOnSubscribe {
                    loadingVisibility.value = true
                }.doOnTerminate {
                    loadingVisibility.value = false
                }.onErrorReturn {
                    CheckUsernameAvailabilityResp(it.localizedMessage)
                }.subscribe {
                    if (it.isSuccessful) {
                        callbackIsUsernameAvailable.value = it.isAvailable
                    } else {
                        errorMessage.value = it.description
                    }
                }
        )

    }
}