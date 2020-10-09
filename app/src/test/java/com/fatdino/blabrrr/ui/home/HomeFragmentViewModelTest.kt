package com.fatdino.blabrrr.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.api.model.User
import com.fatdino.blabrrr.api.model.responds.PostResp
import com.fatdino.blabrrr.api.model.responds.UserResp
import com.fatdino.blabrrr.api.service.ApiPostService
import com.fatdino.blabrrr.api.service.ApiUserService
import com.fatdino.blabrrr.rules.RxImmediateSchedulerRule
import com.fatdino.blabrrr.rules.RxSchedulersOverrideRule
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
class HomeFragmentViewModelTest {
    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    @Mock
    lateinit var apiUserService: ApiUserService

    @Mock
    lateinit var apiPostService: ApiPostService

    @Mock
    lateinit var myPreferences: MySharedPreferences

    @Mock
    lateinit var lifeCycleOwner: LifecycleOwner

    lateinit var mViewModel: HomeFragmentViewModel

    @Before
    fun setup() {
        mViewModel = HomeFragmentViewModel()
        mViewModel.userService = apiUserService
        mViewModel.postService = apiPostService
        mViewModel.myPreferences = myPreferences
    }

    @Test
    fun start_addedNewPost() {
        val post = Post("key", "username", "body", "", 0)

        Mockito.`when`(apiPostService.getLivePosts()).thenReturn(
            Observable.just(
                PostResp(post)
            )
        )

        mViewModel.start(lifeCycleOwner)

        assertEquals(1, mViewModel.posts.value?.count())
        assertEquals(post, mViewModel.posts.value?.get(0))
    }

    @Test
    fun start_addedUserData() {
        val post = Post("key", "username", "body", "", 0)
        val user = User("ray", "aaaa", "")

        Mockito.`when`(apiPostService.getLivePosts()).thenReturn(
            Observable.just(
                PostResp(post)
            )
        )

        Mockito.`when`(apiUserService.getUser(any())).thenReturn(
            Observable.just(
                UserResp(user)
            )
        )

        mViewModel.start(lifeCycleOwner)

        assertEquals(1, mViewModel.userMapList.value?.count())
    }
}