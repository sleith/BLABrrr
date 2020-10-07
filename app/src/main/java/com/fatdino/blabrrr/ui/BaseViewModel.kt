package com.fatdino.blabrrr.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorMessage: MutableLiveData<String?> = MutableLiveData(null)
    val errorMessageInt: MutableLiveData<Int?> = MutableLiveData(null)
    val subscription: CompositeDisposable = CompositeDisposable()

    abstract fun start(owner: LifecycleOwner)

    override fun onCleared() {
        super.onCleared()

        loadingVisibility.value = false
        subscription.clear()
    }
}