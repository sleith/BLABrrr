package com.fatdino.blabrrr.ui.myposts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.api.model.responds.BaseResp
import com.fatdino.blabrrr.api.model.responds.PostListResp
import com.fatdino.blabrrr.api.service.ApiPostService
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
class MyPostActivityViewModelTest {

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

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner

    lateinit var mViewModel: MyPostActivityViewModel

    @Before
    fun setup() {
        mViewModel = MyPostActivityViewModel()
        mViewModel.postService = apiPostService
        mViewModel.myPreferences = myPreferences
    }

    @Test
    fun start_hasPosts() {
        val post = Post("key", "username", "body", "", 0)

        Mockito.`when`(apiPostService.getUserPosts(any())).thenReturn(
            Observable.just(PostListResp(arrayListOf(post)))
        )

        mViewModel.start(lifecycleOwner)
        assertEquals(1, mViewModel.posts.value?.count())
    }

    @Test
    fun start_noPosts() {
        Mockito.`when`(apiPostService.getUserPosts(any())).thenReturn(
            Observable.empty()
        )

        mViewModel.start(lifecycleOwner)
        assertEquals(0, mViewModel.posts.value?.count())
    }

    @Test
    fun start_errorGetPosts() {
        val errorMessage = "Test error"
        Mockito.`when`(apiPostService.getUserPosts(any())).thenReturn(
            Observable.just(PostListResp(errorMessage))
        )

        mViewModel.start(lifecycleOwner)
        assertEquals(errorMessage, mViewModel.errorMessage.value)
    }

    @Test
    fun doDeletePost_success() {
        val post = Post("key", "username", "body", "", 0)
        Mockito.`when`(apiPostService.getUserPosts(any())).thenReturn(
            Observable.just(PostListResp(arrayListOf(post)))
        )
        Mockito.`when`(apiPostService.deletePost(any())).thenReturn(
            Observable.just(BaseResp())
        )

        mViewModel.start(lifecycleOwner)
        assertEquals(1, mViewModel.posts.value?.count())

        mViewModel.doDeletePost(post)
        assertEquals(0, mViewModel.posts.value?.count())

    }

    @Test
    fun doDeletePost_failed() {
        val post = Post("key", "username", "body", "", 0)
        val errorMessage = "Fail to delete"

        Mockito.`when`(apiPostService.deletePost(any())).thenReturn(
            Observable.just(BaseResp(errorMessage))
        )

        mViewModel.doDeletePost(post)
        assertEquals(errorMessage, mViewModel.errorMessage.value)
    }

    @Test
    fun doLogout() {
        mViewModel.doLogout()
        Mockito.verify(myPreferences).password = ""
        Mockito.verify(myPreferences).setUser(null)
        assertEquals(true, mViewModel.callbackLogoutSuccess.value)
    }
}