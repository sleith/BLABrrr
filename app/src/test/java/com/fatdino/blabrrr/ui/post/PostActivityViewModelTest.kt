package com.fatdino.blabrrr.ui.post

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.api.model.responds.PostResp
import com.fatdino.blabrrr.api.service.ApiPostService
import com.fatdino.blabrrr.rules.MainCoroutineRule
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
class PostActivityViewModelTest {

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
    lateinit var apiPostService: ApiPostService

    @Mock
    lateinit var myPreferences: MySharedPreferences

    lateinit var mViewModel: PostActivityViewModel

    @Before
    fun setup() {
        mViewModel = PostActivityViewModel()
        mViewModel.postService = apiPostService
        mViewModel.myPreferences = myPreferences
    }

    @Test
    fun doPost_emptyMessage() {
        mViewModel.message.value = ""
        mViewModel.doPost()
        assertEquals(R.string.post_error_empty_message, mViewModel.errorMessageInt.value)
    }

    @Test
    fun doPost_success() {
        mViewModel.message.value = "test message halloooo"

        val username = "ray"
        val post = Post("key", username, mViewModel.message.value ?: "", "", 0)

        Mockito.`when`(apiPostService.doPost(any(), any(), anyOrNull())).thenReturn(
            Observable.just(
                PostResp(post)
            )
        )

        mViewModel.doPost()

        assertEquals(post, mViewModel.callbackPost.value)
    }

    @Test
    fun doPost_failure() {
        mViewModel.message.value = "test message halloooo"

        val errorMessage = "Test failure"

        Mockito.`when`(apiPostService.doPost(any(), any(), anyOrNull())).thenReturn(
            Observable.just(
                PostResp(errorMessage)
            )
        )

        mViewModel.doPost()

        assertEquals(errorMessage, mViewModel.errorMessage.value)
    }
}