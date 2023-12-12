package com.example.kinopedia.ui.more

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.kinopedia.MainActivity
import com.example.kinopedia.R
import com.example.kinopedia.launchFragmentInHiltContainer
import com.example.kinopedia.ui.more.view.MoreFragment
import com.example.sharedtest.filmForAdapterModelMock
import com.example.sharedtest.thisMonthFilmModelMock
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MoreScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val testActivity = ActivityScenarioRule(MainActivity::class.java)

    private val filmForAdapterMock = filmForAdapterModelMock
    private val thisMonthFilmMock = thisMonthFilmModelMock

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun awaitIsShownTest() {
        val args = Bundle().apply { putString("more_type", "Coming soon") }
        launchFragmentInHiltContainer<MoreFragment>(args)

        onView(withText(filmForAdapterMock.displayName)).check(matches(isDisplayed()))
        onView(withText(filmForAdapterMock.displayCountry)).check(matches(isDisplayed()))
        onView(withText(filmForAdapterMock.displayGenre)).check(matches(isDisplayed()))
        onView(withText(filmForAdapterMock.displayRatingImdb)).check(matches(isDisplayed()))
        onView(withText(filmForAdapterMock.displayOriginalName)).check(matches(isDisplayed()))
        onView(withText(filmForAdapterMock.displayYear)).check(matches(isDisplayed()))
        onView(withId(R.id.kinopoisk_logo_search_result)).check(matches(isDisplayed()))
        onView(withId(R.id.imdb_logo_search_result)).check(matches(isDisplayed()))
    }

    @Test
    fun popularIsShownTest() {
        val args = Bundle().apply { putString("more_type", "Trending") }
        launchFragmentInHiltContainer<MoreFragment>(args)

        onView(withText(filmForAdapterMock.displayName)).check(matches(isDisplayed()))
        onView(withText(filmForAdapterMock.displayCountry)).check(matches(isDisplayed()))
        onView(withText(filmForAdapterMock.displayGenre)).check(matches(isDisplayed()))
        onView(withText(filmForAdapterMock.displayRatingImdb)).check(matches(isDisplayed()))
        onView(withText(filmForAdapterMock.displayOriginalName)).check(matches(isDisplayed()))
        onView(withText(filmForAdapterMock.displayYear)).check(matches(isDisplayed()))
        onView(withId(R.id.kinopoisk_logo_search_result)).check(matches(isDisplayed()))
        onView(withId(R.id.imdb_logo_search_result)).check(matches(isDisplayed()))
    }

    @Test
    fun premierFilmsIsShownTest() {
        val args = Bundle().apply { putString("more_type", "Coming this month") }
        launchFragmentInHiltContainer<MoreFragment>(args)
        onView(withText(thisMonthFilmMock.displayName)).check(matches(isDisplayed()))
        onView(withText(thisMonthFilmMock.displayCountry)).check(matches(isDisplayed()))
        onView(withText(thisMonthFilmMock.displayGenre)).check(matches(isDisplayed()))
        onView(withId(R.id.rating_imdb_search_result)).check(matches(isDisplayed()))
        onView(withId(R.id.rating_kinopoisk_search_result)).check(matches(isDisplayed()))
        onView(withText(thisMonthFilmMock.displayOriginalName)).check(matches(isDisplayed()))
        onView(withText(thisMonthFilmMock.displayYear)).check(matches(isDisplayed()))
        onView(withId(R.id.kinopoisk_logo_search_result)).check(matches(isDisplayed()))
        onView(withId(R.id.imdb_logo_search_result)).check(matches(isDisplayed()))

    }
}