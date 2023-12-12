package com.example.kinopedia.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.kinopedia.MainActivity
import com.example.kinopedia.R
import com.example.kinopedia.launchFragmentInHiltContainer
import com.example.kinopedia.ui.home.view.HomeFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
internal class HomeScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val testActivity = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragmentInHiltContainer<HomeFragment>()
    }

    @Test
    fun comingFilmsIsShownTest() {
        onView(withText("filmForAdapterMock1")).check(matches(isDisplayed()))
        onView(withText("filmForAdapterMock2")).check(matches(isDisplayed()))
        onView(withText("filmForAdapterMock3")).perform(scrollTo()).check(matches(isDisplayed()))
    }

    @Test
    fun trendingFilmsIsShownTest() {
        onView(withText("filmForAdapterMock4")).perform(scrollTo()).check(matches(isDisplayed()))
        onView(withText("filmForAdapterMock5")).perform(scrollTo()).check(matches(isDisplayed()))
        onView(withText("filmForAdapterMock6")).perform(scrollTo()).check(matches(isDisplayed()))
    }

    @Test
    fun premierFilmsIsShownTest() {
        onView(withText("thisMonthFilmsMock1")).check(matches(isDisplayed()))
        onView(withId(R.id.viewPager)).perform(swipeLeft())
        onView(withText("thisMonthFilmsMock2")).check(matches(isDisplayed()))
        onView(withId(R.id.viewPager)).perform(swipeLeft())
        onView(withText("thisMonthFilmsMock3")).check(matches(isDisplayed()))

    }
}