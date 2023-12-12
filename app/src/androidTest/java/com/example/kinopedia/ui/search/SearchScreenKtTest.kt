package com.example.kinopedia.ui.search

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.kinopedia.MainActivity
import com.example.kinopedia.launchFragmentInHiltContainer
import com.example.kinopedia.ui.search.view.SearchFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SearchScreenKtTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val testActivity = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragmentInHiltContainer<SearchFragment>()
    }

    @Test
    fun topFilmsIsShownTest() {
        onView(withText("filmForAdapterMock1")).check(matches(isDisplayed()))
        onView(withText("filmForAdapterMock2")).check(matches(isDisplayed()))
        onView(withText("filmForAdapterMock3")).perform(scrollTo()).check(matches(isDisplayed()))
    }

}