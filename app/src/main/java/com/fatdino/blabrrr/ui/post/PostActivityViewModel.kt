package com.fatdino.blabrrr.ui.post

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.fatdino.blabrrr.ui.BaseViewModel
import java.io.File

class PostActivityViewModel : BaseViewModel() {

    var imageFile: MutableLiveData<File> = MutableLiveData()

    override fun start(owner: LifecycleOwner) {

    }
}