package com.fatdino.blabrrr.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import com.fatdino.blabrrr.api.model.User
import com.fatdino.blabrrr.api.model.responds.UserResp
import com.fatdino.blabrrr.api.service.ApiUserService
import com.fatdino.blabrrr.rules.MainCoroutineRule
import com.fatdino.blabrrr.rules.RxImmediateSchedulerRule
import com.fatdino.blabrrr.storage.MySharedPreferences
import com.nhaarman.mockitokotlin2.any
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class SplashActivityViewModelTest {

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Mock
    lateinit var apiUserService: ApiUserService

    @Mock
    lateinit var myPreferences: MySharedPreferences

    @Mock
    lateinit var lifeCycleOwner: LifecycleOwner

    lateinit var mViewModel: SplashActivityViewModel

    @Before
    fun setup() {
        mViewModel = SplashActivityViewModel()
        mViewModel.userService = apiUserService
        mViewModel.myPreferences = myPreferences
    }

    @Test
    fun start_autoLogin_success() {
        val user = User("ray", "raymond", "")

        Mockito.`when`(myPreferences.username).thenReturn("ray")
        Mockito.`when`(myPreferences.password).thenReturn("haha")

        Mockito.`when`(apiUserService.doLogin(any(), any())).thenReturn(
            Observable.just(
                UserResp(user = user)
            )
        )

        mViewModel.start(lifeCycleOwner)

        Mockito.verify(myPreferences).setUser(user)
        assertEquals(true, mViewModel.mCallbackSplashTimedOut.value)
    }

    @Test
    fun start_autoLogin_error() {
        val user = User("ray", "raymond", "")

        Mockito.`when`(myPreferences.username).thenReturn("ray")
        Mockito.`when`(myPreferences.password).thenReturn("haha")

        val errorMessage = "Wrong password"
        Mockito.`when`(apiUserService.doLogin(any(), any())).thenReturn(
            Observable.just(
                UserResp(errorMessage)
            )
        )

        mViewModel.start(lifeCycleOwner)

        assertEquals(false, mViewModel.mCallbackSplashTimedOut.value)
    }

    @Test
    fun start_no_autoLogin() {

        Mockito.`when`(myPreferences.username).thenReturn("")
        Mockito.`when`(myPreferences.password).thenReturn("")

        mViewModel.start(lifeCycleOwner)
        Thread.sleep(3000)
        assertEquals(false, mViewModel.mCallbackSplashTimedOut.value)
    }
}