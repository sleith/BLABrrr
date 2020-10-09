package com.fatdino.blabrrr.ui.home.adapter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.api.model.User
import com.fatdino.blabrrr.utils.extensions.millisToDate
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class HomePostAdapterViewModelTest {

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mViewModel: HomePostAdapterViewModel

    @Before
    fun setup() {
        mViewModel = HomePostAdapterViewModel()
    }

    @Test
    fun bind() {
        val user = Mockito.mock(User::class.java)
        val post = Mockito.mock(Post::class.java)
        mViewModel.bind(post, user)

        assertEquals(post, mViewModel.post.value)
        assertEquals(user, mViewModel.user.value)
    }

    @Test
    fun getAuthor() {
        val post = Post("key", "ray", "Nature", "", 0)
        mViewModel.bind(post, Mockito.mock(User::class.java))

        assertEquals(post.username, mViewModel.getAuthor())
    }

    @Test
    fun getBody() {
        val post = Post("key", "ray", "Nature", "", 0)
        mViewModel.bind(post, Mockito.mock(User::class.java))

        assertEquals(post.body, mViewModel.getBody())
    }

    @Test
    fun getCreatedDate() {
        val post = Post("key", "ray", "Nature", "", 1602211028024)
        mViewModel.bind(post, Mockito.mock(User::class.java))

        assertEquals(post.createdDate.millisToDate(), mViewModel.getCreatedDate())
    }
}