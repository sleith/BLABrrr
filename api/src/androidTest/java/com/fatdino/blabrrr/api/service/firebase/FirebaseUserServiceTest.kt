package com.fatdino.blabrrr.api.service.firebase

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fatdino.blabrrr.api.service.ApiUserService
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FirebaseUserServiceTest {

    companion object {
        lateinit var firebaseUserService: ApiUserService
        val username = "xTestUser!"
        val fullName = "Tester 1"
        val password = "xasfeafdsaf"

        @BeforeClass
        @JvmStatic
        fun setUp() {
            firebaseUserService = FirebaseUserService()
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            //make sure test user is deleted
            firebaseUserService.deleteUser(username).blockingFirst()
        }
    }


    @Test
    fun doCheckUsernameAvailable() {
        //make sure user is not exist first
        firebaseUserService.deleteUser(username).blockingFirst()

        var result =
            firebaseUserService.doCheckUsernameAvailable(username = username).blockingFirst()
        assertEquals(true, result.isAvailable)

        //try insert user
        firebaseUserService.doRegister(username, fullName, password, null).blockingFirst()

        //now check should be not available
        result =
            firebaseUserService.doCheckUsernameAvailable(username = username).blockingFirst()
        assertEquals(false, result.isAvailable)

    }

    @Test
    fun doRegister() {
        val result =
            firebaseUserService.doRegister(username, fullName, password, null).blockingFirst()
        assertEquals(true, result.isSuccessful)
        assertEquals(username, result.user?.username)
        assertEquals(fullName, result.user?.name)
    }

    @Test
    fun doLogin() {
        //make sure registered first
        firebaseUserService.doRegister(username, fullName, password, null).blockingFirst()

        val result = firebaseUserService.doLogin(username, password).blockingFirst()
        assertEquals(true, result.isSuccessful)
        assertEquals(username, result.user?.username)
        assertEquals(fullName, result.user?.name)
    }

    @Test
    fun getUser() {
        //make sure registered first
        firebaseUserService.doRegister(username, fullName, password, null).blockingFirst()

        val result = firebaseUserService.getUser(username).blockingFirst()
        assertEquals(true, result.isSuccessful)
        assertEquals(username, result.user?.username)
        assertEquals(fullName, result.user?.name)
    }

    @Test
    fun deleteUser() {
        //add user first
        firebaseUserService.doRegister(username, fullName, password, null).blockingFirst()

        //delete
        var result = firebaseUserService.deleteUser(username).blockingFirst()
        assertEquals(true, result.isSuccessful)

        //user should not exist anymore
        result = firebaseUserService.getUser(username).blockingFirst()
        assertEquals(false, result.isSuccessful)

    }
}

