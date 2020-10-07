package com.fatdino.blabrrr.injection.module

import com.fatdino.blabrrr.api.service.ApiUserService
import com.fatdino.blabrrr.api.service.firebase.FirebaseUserService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    fun firebaseUserService(): ApiUserService {
        return FirebaseUserService()
    }

}