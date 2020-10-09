package com.fatdino.blabrrr.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.fatdino.blabrrr.configs.Configs
import com.fatdino.blabrrr.ui.BaseViewModel

class MainActivityViewModel : BaseViewModel() {

    private var lastTimeClickedBackButton = 0L
    var callbackMoveAppToBg: MutableLiveData<Boolean> = MutableLiveData()

    override fun start(owner: LifecycleOwner) {

    }

    fun backPressed() {
        if (System.currentTimeMillis() - lastTimeClickedBackButton > Configs.DIFF_TIME_BACK_PRESS_TO_BG) {
            lastTimeClickedBackButton = System.currentTimeMillis()
            callbackMoveAppToBg.value = false
        } else {
            lastTimeClickedBackButton = 0
            callbackMoveAppToBg.value = true
        }
    }
}