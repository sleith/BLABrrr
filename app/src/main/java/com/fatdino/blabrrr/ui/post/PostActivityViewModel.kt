package com.fatdino.blabrrr.ui.post

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.fatdino.blabrrr.api.service.ApiUserService
import com.fatdino.blabrrr.storage.MySharedPreferences
import com.fatdino.blabrrr.ui.BaseViewModel
import java.io.File
import javax.inject.Inject

class PostActivityViewModel : BaseViewModel() {

    @Inject
    lateinit var userService: ApiUserService

    @Inject
    lateinit var myPreferences: MySharedPreferences

    var imageFile: MutableLiveData<File> = MutableLiveData()

    override fun start(owner: LifecycleOwner) {

    }
}