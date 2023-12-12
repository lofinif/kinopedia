package com.example.kinopedia.ui.filter

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.kinopedia.MainActivity
import com.example.kinopedia.R
import com.example.kinopedia.launchFragmentInHiltContainer
import com.example.kinopedia.ui.filter.view.FilterFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FilterScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val testActivity = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragmentInHiltContainer<FilterFragment>()
    }

    @Test
    fun countriesIsShownTest() {
        onView(withId(R.id.selected_country)).perform(click())

        onView(withText("country1")).check(matches(isDisplayed()))
        onView(withText("country2")).check(matches(isDisplayed()))
        onView(withText("country3")).check(matches(isDisplayed()))
    }

    @Test
    fun genresIsShownTest() {
        onView(withId(R.id.more_genres)).perform(click())

        onView(withText("genre1")).check(matches(isDisplayed()))
        onView(withText("genre2")).check(matches(isDisplayed()))
        onView(withText("genre3")).check(matches(isDisplayed()))
    }
}