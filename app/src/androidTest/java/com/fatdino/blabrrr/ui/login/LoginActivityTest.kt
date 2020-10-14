package com.fatdino.blabrrr.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.api.model.User
import com.fatdino.blabrrr.api.model.responds.UserResp
import com.fatdino.blabrrr.ui.main.MainActivity
import com.fatdino.blabrrr.utils.withBgColor
import com.fatdino.blabrrr.utils.withDrawable
import com.fatdino.blabrrr.utils.withTextColor
import com.fatdino.blabrrr.utils.withTextHint
import com.nhaarman.mockitokotlin2.any
import io.reactivex.rxjava3.core.Observable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<LoginActivity> =
        ActivityScenarioRule(LoginActivity::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mViewModel: LoginActivityViewModel

    @Before
    fun setUp() {
        activityRule.scenario.onActivity { activity ->
            if (activity.mViewModel is LoginActivityViewModel) {
                mViewModel = activity.viewModel
            }
        }

        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun ui_check() {
        onView(withId(R.id.container)).check(matches(withBgColor(R.color.black)))

        onView(withId(R.id.txtIntro)).check(matches(withText(R.string.login_welcome)))
        onView(withId(R.id.txtIntro)).check(matches(withTextColor(R.color.white)))

        onView(withId(R.id.tilUsername)).check(matches(withTextHint(R.string.username)))
        onView(withId(R.id.tilPassword)).check(matches(withTextHint(R.string.password)))

        onView(withId(R.id.btnLogin)).check(matches(withText(R.string.login)))

        onView(withId(R.id.ivFooter)).check(matches(withDrawable(R.drawable.ic_bg_loginregister)))

    }

    @Test
    fun login_success() {
        val user = User("any", "any", "")

        Mockito.`when`(mViewModel.userService.doLogin(any(), any())).thenReturn(
            Observable.just(
                UserResp(user)
            )
        )

        onView(withId(R.id.etUsername)).perform(replaceText("any"))
        onView(withId(R.id.etPassword)).perform(replaceText("any"))

        onView(withId(R.id.btnLogin)).perform(click())

        intended(hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun login_failed() {
        val errorMessage = "error man!"
        Mockito.`when`(mViewModel.userService.doLogin(any(), any())).thenReturn(
            Observable.just(
                UserResp(errorMessage)
            )
        )

        onView(withId(R.id.etUsername)).perform(replaceText("any"))
        onView(withId(R.id.etPassword)).perform(replaceText("any"))

        onView(withId(R.id.btnLogin)).perform(click())

        onView(withText(errorMessage))
            .inRoot(isDialog())
            .check(matches(isDisplayed()));

    }
}