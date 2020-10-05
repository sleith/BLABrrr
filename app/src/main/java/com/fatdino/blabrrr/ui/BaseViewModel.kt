package com.fatdino.blabrrr.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val errorMessage: MutableLiveData<String?> = MutableLiveData(null)
    val errorMessageInt: MutableLiveData<Int?> = MutableLiveData(null)

    abstract fun start(owner: LifecycleOwner)

    override fun onCleared() {
        super.onCleared()

    }
}