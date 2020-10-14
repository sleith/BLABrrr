package com.fatdino.blabrrr

import android.content.Context
import com.fatdino.blabrrr.api.model.responds.CheckUsernameAvailabilityResp
import com.fatdino.blabrrr.api.model.responds.PostListResp
import com.fatdino.blabrrr.api.model.responds.PostResp
import com.fatdino.blabrrr.api.model.responds.UserResp
import com.fatdino.blabrrr.api.service.ApiPostService
import com.fatdino.blabrrr.api.service.ApiUserService
import com.fatdino.blabrrr.injection.component.AppComponent
import com.fatdino.blabrrr.injection.component.DaggerAppComponent
import com.fatdino.blabrrr.injection.module.ServiceModule
import com.fatdino.blabrrr.injection.module.StorageModule
import com.fatdino.blabrrr.storage.MySharedPreferences
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import dagger.Module
import io.reactivex.rxjava3.core.Observable
import org.mockito.Mockito

class TestApplication : MyApplication() {

    override fun initAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .serviceModule(MockServiceModule())
            .storageModule(MockStorageModule(this))
            .build()
    }

    @Module
    private inner class MockServiceModule : ServiceModule() {
        override fun firebaseUserService(): ApiUserService {
            val service = Mockito.mock(ApiUserService::class.java)
            //default implementations
            Mockito.`when`(service.doLogin(any(), any())).thenReturn(
                Observable.just(
                    UserResp("Need to implement")
                )
            )
            Mockito.`when`(service.getUser(any())).thenReturn(
                Observable.just(
                    UserResp("Need to implement")
                )
            )
            Mockito.`when`(service.deleteUser(any())).thenReturn(
                Observable.just(
                    UserResp("Need to implement")
                )
            )
            Mockito.`when`(service.doRegister(any(), any(), any(), anyOrNull())).thenReturn(
                Observable.just(
                    UserResp("Need to implement")
                )
            )
            Mockito.`when`(service.doCheckUsernameAvailable(any())).thenReturn(
                Observable.just(
                    CheckUsernameAvailabilityResp("Need to implement")
                )
            )
            return service
        }

        override fun firebasePostService(): ApiPostService {
            val service = Mockito.mock(ApiPostService::class.java)
            //default implementations
            Mockito.`when`(service.getLivePosts()).thenReturn(
                Observable.just(
                    PostResp("Need to implement")
                )
            )
            Mockito.`when`(service.doPost(any(), any(), anyOrNull())).thenReturn(
                Observable.just(
                    PostResp("Need to implement")
                )
            )
            Mockito.`when`(service.deletePost(anyOrNull())).thenReturn(
                Observable.just(
                    PostResp("Need to implement")
                )
            )
            Mockito.`when`(service.getUserPosts(any())).thenReturn(
                Observable.just(
                    PostListResp("Need to implement")
                )
            )
            return service
        }
    }

    @Module
    private inner class MockStorageModule(context: Context) : StorageModule(context) {
        override fun providesPreferences(): MySharedPreferences {
            return MySharedPreferences(context = context)
        }
    }
}