package com.fatdino.blabrrr.ui.landing

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.ui.login.LoginActivity
import com.fatdino.blabrrr.ui.signup.SignUpStep1Activity
import com.fatdino.blabrrr.utils.withBgColor
import com.fatdino.blabrrr.utils.withDrawable
import com.fatdino.blabrrr.utils.withTextColor
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LandingActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<LandingActivity> =
        ActivityScenarioRule(LandingActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun ui_check() {
        onView(withId(R.id.container)).check(matches(withBgColor(R.color.black)))
        onView(withId(R.id.ivLogo)).check(matches(withDrawable(R.drawable.ic_logo)))
        onView(withId(R.id.ivLogoText)).check(matches(withDrawable(R.drawable.ic_logo_text)))
        onView(withId(R.id.txtIntro)).check(matches(withText(R.string.landing_info)))
        onView(withId(R.id.txtIntro)).check(matches(withTextColor(R.color.white)))

        onView(withId(R.id.llButtonsContainer)).check(matches(withDrawable(R.drawable.ic_bg_onboarding)))
        onView(withId(R.id.btnSignUp)).check(matches(withText(R.string.sign_up)))
        onView(withId(R.id.btnLogin)).check(matches(withText(R.string.login)))

        onView(withId(R.id.txtAppName)).check(matches(withText(R.string.app_name)))
        onView(withId(R.id.txtAppName)).check(matches(withTextColor(R.color.white)))

        onView(withId(R.id.txtCreator)).check(matches(withText(R.string.creator)))
        onView(withId(R.id.txtCreator)).check(matches(withTextColor(R.color.white)))
    }

    @Test
    fun navigation_signUp() {
        onView(withId(R.id.btnSignUp)).perform(click())
        intended(hasComponent(SignUpStep1Activity::class.java.name))
    }

    @Test
    fun navigation_login() {
        onView(withId(R.id.btnLogin)).perform(click())
        intended(hasComponent(LoginActivity::class.java.name))
    }
}