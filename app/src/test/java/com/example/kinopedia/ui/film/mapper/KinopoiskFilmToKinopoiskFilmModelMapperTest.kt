package com.example.kinopedia.ui.film.mapper

import com.example.kinopedia.BaseTest
import com.example.sharedtest.kinopoiskFilmMock
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class KinopoiskFilmToKinopoiskFilmModelMapperTest : BaseTest() {
    private lateinit var mapper: KinopoiskFilmToKinopoiskFilmModelMapper
    private val entity = kinopoiskFilmMock

    override fun setUp() {
        super.setUp()
        mapper = KinopoiskFilmToKinopoiskFilmModelMapper()
    }

    @Test
    fun `id is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.filmId).isEqualTo(entity.kinopoiskId)
    }

    @Test
    fun `displayGenre is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.displayGenre).isEqualTo(entity.genres[0].genre.toString())
    }

    @Test
    fun `posterUrl is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.posterUrl).isEqualTo(entity.posterUrl)
    }

    @Test
    fun `displayFilmLength is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.displayFilmLength).isEqualTo(entity.filmLength.toString())
    }

    @Test
    fun `displayDescription is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.displayDescription).isEqualTo(entity.description)
    }

    @Test
    fun `displayCountries is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.displayCountries).isEqualTo(entity.countries[0].country.toString())
    }

    @Test
    fun `displayYear is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.displayYear).isEqualTo(entity.year.toString())
    }

    @Test
    fun `displayDetails is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.displayDetails)
            .isEqualTo("${result.displayYear}, ${result.displayGenre}, ${result.displayFilmLength}, ${result.displayCountries}")
    }

    @Test
    fun `displayName is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.displayName).isEqualTo(entity.nameRu)
    }

    @Test
    fun `displayRatingImdb is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.displayRatingImdb).isEqualTo(entity.ratingImdb.toString())
    }

    @Test
    fun `displayRatingKinopoisk is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.displayRatingKinopoisk).isEqualTo(entity.ratingKinopoisk.toString())
    }

}