package com.fatdino.blabrrr.ui.splash

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.fatdino.blabrrr.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivityViewModel : BaseViewModel() {

    var mCallbackSplashTimedout: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun start(owner: LifecycleOwner) {
        GlobalScope.launch {
            Thread.sleep(2000)

            withContext(Dispatchers.Main) {
                mCallbackSplashTimedout.value = true
            }
        }
    }
}