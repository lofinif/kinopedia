package com.example.kinopedia.ui.favourite

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.kinopedia.MainActivity
import com.example.kinopedia.launchFragmentInHiltContainer
import com.example.kinopedia.ui.favourite.view.FavouriteFragment
import com.example.sharedtest.favouriteEntityMock
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FavouriteScreenKtTest {


    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val testActivity = ActivityScenarioRule(MainActivity::class.java)

    private val mock = favouriteEntityMock

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragmentInHiltContainer<FavouriteFragment>()
    }

    @Test
    fun filmItemDetailsIsShownTest() {
        onView(withText(mock.nameRu)).check(matches(isDisplayed()))
        onView(withText(mock.nameOriginal)).check(matches(isDisplayed()))
        onView(withText(mock.year)).check(matches(isDisplayed()))
        onView(withText(mock.genre)).check(matches(isDisplayed()))
        onView(withText(mock.country)).check(matches(isDisplayed()))
        onView(withText(mock.ratingImdb)).check(matches(isDisplayed()))
        onView(withText(mock.ratingKinopoisk)).check(matches(isDisplayed()))
    }


}