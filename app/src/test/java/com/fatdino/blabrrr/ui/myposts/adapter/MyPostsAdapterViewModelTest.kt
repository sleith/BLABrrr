package com.fatdino.blabrrr.ui.myposts.adapter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fatdino.blabrrr.api.model.Post
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
class MyPostsAdapterViewModelTest {

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mViewModel: MyPostsAdapterViewModel

    @Before
    fun setup() {
        mViewModel = MyPostsAdapterViewModel()
    }

    @Test
    fun bind() {
        val post = Mockito.mock(Post::class.java)
        mViewModel.bind(post)

        assertEquals(post, mViewModel.post.value)
    }

    @Test
    fun getBody() {
        val post = Post("key", "ray", "Nature", "", 0)
        mViewModel.bind(post)

        assertEquals(post.body, mViewModel.getBody())
    }

    @Test
    fun getCreatedDate() {
        val post = Post("key", "ray", "Nature", "", 1602211028024)
        mViewModel.bind(post)

        assertEquals(post.createdDate.millisToDate(), mViewModel.getCreatedDate())
    }
}
