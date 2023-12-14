package com.example.kinopedia.ui.film

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.kinopedia.MainActivity
import com.example.kinopedia.R
import com.example.kinopedia.launchFragmentInHiltContainer
import com.example.kinopedia.ui.film.view.FilmPageFragment
import com.example.sharedtest.kinopoiskFilmMock
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FilmPageScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val testActivity = ActivityScenarioRule(MainActivity::class.java)

    private val mock = kinopoiskFilmMock

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragmentInHiltContainer<FilmPageFragment>()
    }

    @Test
    fun posterBackgroundIsShownTest() {
        onView(withId(R.id.poster_background)).check(matches(isDisplayed()))
    }

    @Test
    fun posterIsShownTest() {
        onView(withId(R.id.poster_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun nameMovieIsShownTest() {
        onView(withText(mock.nameRu)).check(matches(isDisplayed()))
    }

    @Test
    fun nameMovieOriginalIsShownTest() {
        onView(withText(mock.nameOriginal)).check(matches(isDisplayed()))
    }

    @Test
    fun detailsMovieIsShownTest() {
        onView(withText("${mock.year}, ${mock.genres[0].genre}, ${mock.filmLength}, ${mock.countries[0].country}")).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun descriptionMovieIsShownTest() {
        onView(withText(mock.description)).perform(scrollTo()).check(matches(isDisplayed()))
    }

    @Test
    fun ratingImdbIsShownTest() {
        onView(withText(mock.ratingImdb.toString())).check(matches(isDisplayed()))
    }

    @Test
    fun ratingKinopoiskIsShownTest() {
        onView(withText(mock.ratingKinopoisk.toString())).check(matches(isDisplayed()))
    }

    @Test
    fun ratingImdbLogoIsShownTest() {
        onView(withId(R.id.imdb_logo)).check(matches(isDisplayed()))
    }

    @Test
    fun ratingKinopoiskLogoIsShownTest() {
        onView(withId(R.id.kinopoisk_logo)).check(matches(isDisplayed()))
    }

    @Test
    fun actorsIsShownTest() {
        onView(withText("nameRuActor1")).perform(scrollTo()).check(matches(isDisplayed()))
        onView(withText("nameRuActor2")).perform(scrollTo()).check(matches(isDisplayed()))
        onView(withText("nameRuActor3")).perform(scrollTo()).check(matches(isDisplayed()))
    }

    @Test
    fun staffIsShownTest() {
        onView(withText("nameRuStaff4")).perform(scrollTo()).check(matches(isDisplayed()))
        onView(withText("nameRuStaff5")).perform(scrollTo()).check(matches(isDisplayed()))
        onView(withText("nameRuStaff6")).perform(scrollTo()).check(matches(isDisplayed()))
    }

    @Test
    fun similarIsShownTest() {
        onView(withText("nameRu")).perform(scrollTo()).check(matches(isDisplayed()))
    }

    @Test
    fun similarPosterIsShownTest() {
        onView(withId(R.id.poster_similar)).perform(scrollTo()).check(matches(isDisplayed()))
    }

    @Test
    fun externalIsShownTest() {
        onView(withText("platform")).perform(scrollTo()).check(matches(isDisplayed()))
    }

    @Test
    fun externalLogoIsShownTest() {
        onView(withId(R.id.logo)).perform(scrollTo()).check(matches(isDisplayed()))
    }
}