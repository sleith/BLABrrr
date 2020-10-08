package com.fatdino.blabrrr.api.service.firebase

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.api.service.ApiPostService
import com.fatdino.blabrrr.api.service.ApiUserService
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirebasePostServiceTest {

    companion object {
        lateinit var firebasePostService: ApiPostService
        lateinit var firebaseUserService: ApiUserService

        val username = "xTestUser!"
        val fullName = "Tester 1"
        val password = "xasfeafdsaf"

        @BeforeClass
        @JvmStatic
        fun setUp() {
            firebaseUserService = FirebaseUserService()
            firebasePostService = FirebasePostService()

            //make sure user exist
            firebaseUserService.doRegister(username, fullName, password, null)
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            //delete user
            firebaseUserService.deleteUser(username)
        }
    }

    @Test
    fun doPost() {
        val postBody = "Hello, how are you doing!"
        val result = firebasePostService.doPost(username, postBody, null).blockingFirst()
        assertEquals(true, result.isSuccessful)
        assertEquals(username, result.post?.username)
        assertEquals(postBody, result.post?.body)

        //clean up
        result.post?.let {
            firebasePostService.deletePost(it).blockingFirst()
        }
    }

    @Test
    fun getLivePosts() {
        var count = 0
        firebasePostService.getLivePosts().subscribeOn(Schedulers.io())
            .observeOn(
                Schedulers.io()
            ).doOnSubscribe {
            }.doOnTerminate {
            }.doOnError {

            }.subscribe {
                count++
            }

        val postBody = "Hello, how are you doing!"
        val result = firebasePostService.doPost(username, postBody, null).blockingFirst()
        assertEquals(true, result.isSuccessful)

        result.post?.let {
            firebasePostService.deletePost(it).blockingFirst()
        }

        Thread.sleep(3000)

        //there should be at least 2 changes happened
        assertTrue(count >= 2)
    }

    @Test
    fun getUserPosts() {
        ////add data first
        val postedList: ArrayList<Post> = ArrayList()
        for (x in 0 until 3) {
            val postBody = "Hello, how are you doing!"
            val result = firebasePostService.doPost(username, postBody, null).blockingFirst()
            assertEquals(true, result.isSuccessful)

            result.post?.let {
                postedList.add(it)
            }
        }

        val result = firebasePostService.getUserPosts(username).blockingFirst()
        assertEquals(true, result.isSuccessful)
        assertEquals(postedList.count(), result.posts.count())

        for (currPost in result.posts) {
            var isExist = false
            //find to match the post
            for (p in postedList) {
                if (p.key == currPost.key) {
                    assertEquals(p.username, currPost.username)
                    assertEquals(p.body, currPost.body)
                    assertEquals(p.createdDate, currPost.createdDate)
                    assertEquals(p.filePath, currPost.filePath)
                    isExist = true
                    break
                }
            }
            assertEquals(true, isExist)
        }

        //clean up
        for (currPost in result.posts) {
            firebasePostService.deletePost(currPost).blockingFirst()
        }
    }

    @Test
    fun deletePost() {
        //insert first
        val postBody = "Hello, how are you doing!"
        var result = firebasePostService.doPost(username, postBody, null).blockingFirst()
        assertEquals(true, result.isSuccessful)

        //get all user's posts
        val resultPostList = firebasePostService.getUserPosts(username).blockingFirst()
        assertEquals(true, resultPostList.isSuccessful)
        assertTrue(resultPostList.posts.count() > 0)

        //delete all posts
        for (currPost in resultPostList.posts) {
            firebasePostService.deletePost(currPost).blockingFirst()
        }

        //get all user's posts again, now it should be 0
        val resultPostListAfterCleared = firebasePostService.getUserPosts(username).blockingFirst()
        assertEquals(true, resultPostListAfterCleared.isSuccessful)
        assertEquals(0, resultPostListAfterCleared.posts.count())
    }
}