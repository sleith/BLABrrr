package com.fatdino.blabrrr.ui.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.api.model.User
import com.fatdino.blabrrr.api.model.responds.UserResp
import com.fatdino.blabrrr.api.service.ApiUserService
import com.fatdino.blabrrr.rules.RxImmediateSchedulerRule
import com.fatdino.blabrrr.storage.MySharedPreferences
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
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
class SignUpStep2ViewModelTest {

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var apiUserService: ApiUserService

    @Mock
    lateinit var myPreferences: MySharedPreferences

    lateinit var mViewModel: SignUpStep2ViewModel

    @Before
    fun setup() {
        mViewModel = SignUpStep2ViewModel()
        mViewModel.userService = apiUserService
        mViewModel.myPreferences = myPreferences
    }

    @Test
    fun doRegister_emptyName() {
        mViewModel.name.value = ""
        mViewModel.doRegister()
        assertEquals(R.string.sus2_err_name_empty, mViewModel.errorMessageInt.value)
    }

    @Test
    fun doRegister_emptyPassword() {
        mViewModel.name.value = "Ray"
        mViewModel.password.value = ""
        mViewModel.doRegister()
        assertEquals(R.string.sus2_err_password_empty, mViewModel.errorMessageInt.value)
    }

    @Test
    fun doRegister_errorResult() {
        mViewModel.username.value = "halo"
        mViewModel.name.value = "My name"
        mViewModel.password.value = "xyz123"

        val errorMessage = "Sorry failed!"
        Mockito.`when`(apiUserService.doRegister(any(), any(), any(), anyOrNull())).thenReturn(
            Observable.just(
                UserResp(errorMessage)
            )
        )

        mViewModel.doRegister()
        assertEquals(errorMessage, mViewModel.errorMessage.value)
    }

    @Test
    fun doRegister_successfully() {
        mViewModel.username.value = "halo"
        mViewModel.name.value = "My name"
        mViewModel.password.value = "xyz123"

        val user = User(mViewModel.username.value ?: "", mViewModel.username.value ?: "", "")
        Mockito.`when`(apiUserService.doRegister(any(), any(), any(), anyOrNull())).thenReturn(
            Observable.just(
                UserResp(user = user)
            )
        )

        mViewModel.doRegister()

        Mockito.verify(myPreferences).setUser(user)
        Mockito.verify(myPreferences).username = mViewModel.username.value
        Mockito.verify(myPreferences).password = mViewModel.password.value
        assertEquals(user, mViewModel.callbackRegister.value)
    }
}