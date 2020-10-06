package com.fatdino.blabrrr.ui.signup

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.fatdino.blabrrr.ui.BaseViewModel
import java.io.File

class SignUpStep1ViewModel : BaseViewModel() {

    var imageFileName: MutableLiveData<String> = MutableLiveData()
    var imageFile: MutableLiveData<File> = MutableLiveData()

    override fun start(owner: LifecycleOwner) {

    }
}