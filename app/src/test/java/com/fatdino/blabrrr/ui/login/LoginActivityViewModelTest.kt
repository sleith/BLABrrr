package com.fatdino.blabrrr.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import com.fatdino.blabrrr.R
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
class LoginActivityViewModelTest {

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

    lateinit var mViewModel: LoginActivityViewModel

    @Before
    fun setup() {
        mViewModel = LoginActivityViewModel()
        mViewModel.userService = apiUserService
        mViewModel.myPreferences = myPreferences
    }

    @Test
    fun start_existingUsername() {
        val username = "ray"
        Mockito.`when`(myPreferences.username).thenReturn(username)

        mViewModel.start(lifeCycleOwner)
        assertEquals(username, mViewModel.username.value)
    }

    @Test
    fun doLogin_emptyUsername() {
        mViewModel.username.value = ""
        mViewModel.doLogin()
        assertEquals(R.string.sus1_err_username_empty, mViewModel.errorMessageInt.value)
    }

    @Test
    fun doLogin_emptyPassword() {
        mViewModel.username.value = "aaa"
        mViewModel.password.value = ""
        mViewModel.doLogin()
        assertEquals(R.string.sus2_err_password_empty, mViewModel.errorMessageInt.value)
    }

    @Test
    fun doLogin_success() {
        mViewModel.username.value = "halo"
        mViewModel.password.value = "xyz123"

        val user = User(mViewModel.username.value ?: "", "raymond", "")

        Mockito.`when`(apiUserService.doLogin(any(), any())).thenReturn(
            Observable.just(
                UserResp(user = user)
            )
        )

        mViewModel.doLogin()

        Mockito.verify(myPreferences).setUser(user)
        Mockito.verify(myPreferences).username = mViewModel.username.value
        Mockito.verify(myPreferences).password = mViewModel.password.value
        assertEquals(user, mViewModel.callbackLogin.value)
    }

    @Test
    fun doLogin_error() {
        mViewModel.username.value = "halo"
        mViewModel.password.value = "xyz123"

        val errorMessage = "Test failure"

        Mockito.`when`(apiUserService.doLogin(any(), any())).thenReturn(
            Observable.just(
                UserResp(errorMessage)
            )
        )

        mViewModel.doLogin()

        assertEquals(errorMessage, mViewModel.errorMessage.value)
    }
}