package com.fatdino.blabrrr.ui.signup

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.api.model.User
import com.fatdino.blabrrr.api.model.responds.UserResp
import com.fatdino.blabrrr.api.service.ApiUserService
import com.fatdino.blabrrr.storage.MySharedPreferences
import com.fatdino.blabrrr.ui.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

class SignUpStep2ViewModel : BaseViewModel() {

    @Inject
    lateinit var userService: ApiUserService

    @Inject
    lateinit var myPreferences: MySharedPreferences

    var imageFile: MutableLiveData<File> = MutableLiveData()
    var username: MutableLiveData<String> = MutableLiveData()
    var name: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    var callbackRegister: MutableLiveData<User?> = MutableLiveData()

    override fun start(owner: LifecycleOwner) {

    }

    fun doRegister() {
        if (name.value.isNullOrEmpty()) {
            errorMessageInt.value = R.string.sus2_err_name_empty
            return
        }
        if (password.value.isNullOrEmpty()) {
            errorMessageInt.value = R.string.sus2_err_password_empty
            return
        }
        subscription.add(
            userService.doRegister(
                username.value ?: "",
                name.value ?: "",
                password.value ?: "",
                null
            ).subscribeOn(Schedulers.io())
                .observeOn(
                    AndroidSchedulers.mainThread()
                ).doOnSubscribe {
                    loadingVisibility.value = true
                }.doOnTerminate {
                    loadingVisibility.value = false
                }.onErrorReturn {
                    UserResp(it.localizedMessage)
                }.subscribe {
                    if (it.isSuccessful) {
                        myPreferences.setUser(it.user)
                        myPreferences.username = username.value
                        myPreferences.password = password.value

                        callbackRegister.value = it.user
                    } else {
                        errorMessage.value = it.description
                    }
                }
        )
    }
}