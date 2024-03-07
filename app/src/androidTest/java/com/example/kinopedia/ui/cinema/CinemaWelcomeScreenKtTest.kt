package com.example.kinopedia.ui.cinema


import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.example.kinopedia.MainActivity
import com.example.kinopedia.launchFragmentInHiltContainer
import com.example.kinopedia.ui.cinema.view.CinemaWelcomeFragment
import com.example.kinopedia.utils.EspressoIdlingResource
import com.example.sharedtest.cinemaOSMMock
import com.example.sharedtest.cityOSMMock
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CinemaWelcomeScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val testActivity = ActivityScenarioRule(MainActivity::class.java)



    @get:Rule(order = 2)
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION)

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragmentInHiltContainer<CinemaWelcomeFragment>()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun cityIsShown() {
        /* IMPORTANT: This test may fail on an emulator due to the use of locationManager, which relies on the network_provider.
         It is recommended to run the test on a real device for successful execution. */

        onView(withText(cityOSMMock.address.displayCity)).check(matches(isDisplayed()))
        onView(withText(cinemaOSMMock.tags!!.city)).check(matches(isDisplayed()))
        onView(withText(cinemaOSMMock.tags!!.address)).check(matches(isDisplayed()))
    }
}