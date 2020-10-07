package com.fatdino.blabrrr.ui.landing

import androidx.lifecycle.LifecycleOwner
import com.fatdino.blabrrr.api.service.ApiUserService
import com.fatdino.blabrrr.storage.MySharedPreferences
import com.fatdino.blabrrr.ui.BaseViewModel
import javax.inject.Inject

class LandingActivityViewModel : BaseViewModel() {

    @Inject
    lateinit var userService: ApiUserService

    @Inject
    lateinit var myPreferences: MySharedPreferences

    override fun start(owner: LifecycleOwner) {

    }
}