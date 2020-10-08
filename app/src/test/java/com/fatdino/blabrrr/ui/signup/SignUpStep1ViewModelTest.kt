package com.fatdino.blabrrr.ui.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.api.model.responds.CheckUsernameAvailabilityResp
import com.fatdino.blabrrr.api.service.ApiUserService
import com.fatdino.blabrrr.rules.RxImmediateSchedulerRule
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
class SignUpStep1ViewModelTest {

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

    lateinit var mViewModel: SignUpStep1ViewModel

    @Before
    fun setup() {
        mViewModel = SignUpStep1ViewModel()
        mViewModel.userService = apiUserService
    }

    @Test
    fun checkUsernameAvailability_emptyUsername() {
        mViewModel.username.value = ""
        mViewModel.checkUsernameAvailability()
        assertEquals(R.string.sus1_err_username_empty, mViewModel.errorMessageInt.value)
    }

    @Test
    fun checkUsernameAvailability_errorResult() {
        mViewModel.username.value = "halo"

        val errorMessage = "Sorry failed!"
        Mockito.`when`(apiUserService.doCheckUsernameAvailable(any())).thenReturn(
            Observable.just(
                CheckUsernameAvailabilityResp(errorMessage)
            )
        )

        mViewModel.checkUsernameAvailability()
        assertEquals(errorMessage, mViewModel.errorMessage.value)
    }

    @Test
    fun checkUsernameAvailability_available() {
        mViewModel.username.value = "halo"

        Mockito.`when`(apiUserService.doCheckUsernameAvailable(any())).thenReturn(
            Observable.just(
                CheckUsernameAvailabilityResp(true)
            )
        )

        mViewModel.checkUsernameAvailability()
        assertEquals(true, mViewModel.callbackIsUsernameAvailable.value)
    }

    @Test
    fun checkUsernameAvailability_notAvailable() {
        mViewModel.username.value = "halo"

        Mockito.`when`(apiUserService.doCheckUsernameAvailable(any())).thenReturn(
            Observable.just(
                CheckUsernameAvailabilityResp(false)
            )
        )

        mViewModel.checkUsernameAvailability()
        assertEquals(false, mViewModel.callbackIsUsernameAvailable.value)
    }
}