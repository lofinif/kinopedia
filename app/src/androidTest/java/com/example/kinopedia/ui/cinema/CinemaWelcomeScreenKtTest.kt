package com.example.kinopedia.ui.cinema

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.kinopedia.MainActivity
import com.example.kinopedia.launchFragmentInHiltContainer
import com.example.kinopedia.ui.cinema.view.CinemaWelcomeFragment
import com.example.sharedtest.cinemaOSMMock
import com.example.sharedtest.cityOSMMock
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CinemaWelcomeScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val testActivity = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragmentInHiltContainer<CinemaWelcomeFragment>()
    }

    @Test
    fun cityIsShown() {
        Thread.sleep(5000)
        onView(withText(cityOSMMock.address.displayCity)).check(matches(isDisplayed()))
        onView(withText(cinemaOSMMock.tags!!.city)).check(matches(isDisplayed()))
        onView(withText(cinemaOSMMock.tags!!.address)).check(matches(isDisplayed()))
    }
}