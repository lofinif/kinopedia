package com.example.kinopedia.ui.genre

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
import com.example.kinopedia.ui.genre.view.GenreFragment
import com.example.sharedtest.kinopoiskFilmModelMock
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class GenreScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val testActivity = ActivityScenarioRule(MainActivity::class.java)

    private val mock = kinopoiskFilmModelMock

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragmentInHiltContainer<GenreFragment>(Bundle().apply {
            putInt(
                "genreId" +
                        "+-", 1
            )
            putString("genre", "detective")
        }
        )
    }

    @Test
    fun filmItemDetailsIsShownTest() {
        //   launchFragmentInHiltContainer<GenreFragment>(Bundle().apply { putInt("genre", 1) })

        Thread.sleep(123123123)

        onView(withText(mock.displayName)).check(matches(isDisplayed()))
        onView(withText(mock.displayNameOriginal)).check(matches(isDisplayed()))
        onView(withText(mock.displayYear)).check(matches(isDisplayed()))
        onView(withText(mock.displayGenre)).check(matches(isDisplayed()))
        onView(withText(mock.displayCountries)).check(matches(isDisplayed()))
        onView(withText(mock.displayRatingImdb)).check(matches(isDisplayed()))
        onView(withText(mock.displayRatingKinopoisk)).check(matches(isDisplayed()))
        onView(withId(R.id.kinopoisk_logo_search_result)).check(matches(isDisplayed()))
        onView(withId(R.id.imdb_logo_search_result)).check(matches(isDisplayed()))

    }

}