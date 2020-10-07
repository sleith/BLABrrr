package com.fatdino.blabrrr.ui.login

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
import javax.inject.Inject

class LoginActivityViewModel : BaseViewModel() {

    @Inject
    lateinit var userService: ApiUserService

    @Inject
    lateinit var myPreferences: MySharedPreferences

    var username: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    var callbackLogin: MutableLiveData<User?> = MutableLiveData()

    override fun start(owner: LifecycleOwner) {

    }

    fun doLogin() {
        if (username.value.isNullOrEmpty()) {
            errorMessageInt.value = R.string.sus1_err_username_empty
            return
        }
        if (password.value.isNullOrEmpty()) {
            errorMessageInt.value = R.string.sus2_err_password_empty
            return
        }
        subscription.add(
            userService.doLogin(
                username.value ?: "",
                password.value ?: ""
            ).subscribeOn(Schedulers.io())
                .observeOn(
                    AndroidSchedulers.mainThread()
                ).doOnSubscribe {
                    loadingVisibility.value = true
                }.doOnTerminate {
                    loadingVisibility.value = false
                }.doOnError {
                    UserResp(it.localizedMessage)
                }.subscribe {
                    if (it.isSuccessful) {
                        myPreferences.setUser(it.user)
                        myPreferences.username = username.value
                        myPreferences.password = password.value

                        callbackLogin.value = it.user
                    } else {
                        errorMessage.value = it.description
                    }
                }
        )
    }
}