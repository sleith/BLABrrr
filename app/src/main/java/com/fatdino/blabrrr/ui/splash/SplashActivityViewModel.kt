package com.fatdino.blabrrr.ui.splash

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.fatdino.blabrrr.api.model.responds.UserResp
import com.fatdino.blabrrr.api.service.ApiUserService
import com.fatdino.blabrrr.storage.MySharedPreferences
import com.fatdino.blabrrr.ui.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SplashActivityViewModel : BaseViewModel() {

    @Inject
    lateinit var userService: ApiUserService

    @Inject
    lateinit var myPreferences: MySharedPreferences

    var mCallbackSplashTimedOut: MutableLiveData<Boolean> = MutableLiveData()

    override fun start(owner: LifecycleOwner) {
        val username = myPreferences.username
        val password = myPreferences.password

        if (!username.isNullOrEmpty() && !password.isNullOrEmpty()) {
            doLogin(username, password)
        } else {
            GlobalScope.launch {
                Thread.sleep(2000)
                withContext(Dispatchers.Main) {
                    mCallbackSplashTimedOut.value = false
                }
            }
        }
    }

    private fun doLogin(username: String, password: String) {
        subscription.add(
            userService.doLogin(
                username, password
            ).subscribeOn(Schedulers.io())
                .observeOn(
                    AndroidSchedulers.mainThread()
                ).doOnSubscribe {

                }.doOnTerminate {

                }.doOnError {
                    UserResp(it.localizedMessage)
                }.subscribe {
                    if (it.isSuccessful) {
                        myPreferences.setUser(it.user)
                        mCallbackSplashTimedOut.value = true
                    } else {
                        mCallbackSplashTimedOut.value = false
                    }
                }
        )
    }
}