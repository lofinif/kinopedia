package com.example.kinopedia.ui.cinema

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.kinopedia.MainActivity
import com.example.kinopedia.R
import com.example.kinopedia.launchFragmentInHiltContainer
import com.example.kinopedia.ui.cinema.view.CinemaWelcomeFragment
import com.example.kinopedia.ui.cinema.view.NearestCinemaFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NearestCinemaScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val testActivity = ActivityScenarioRule(MainActivity::class.java)
    val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @Before
    fun setup() {
        hiltRule.inject()

        launchFragmentInHiltContainer<CinemaWelcomeFragment> {
            navController.setGraph(R.navigation.mobile_navigation)
            navController.setCurrentDestination(R.id.cinemaWelcomeFragment)
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @Test
    fun mapDetailsIsShown() {
        val args = Bundle().apply {
            putDouble("latitude", 123.456)
            putDouble("longitude", 789.012)
        }
        Thread.sleep(5000)
        onView(withId(R.id.map)).perform(click())
        launchFragmentInHiltContainer<NearestCinemaFragment>(args)
        Thread.sleep(500000)

    }

}