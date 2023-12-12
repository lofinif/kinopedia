package com.example.kinopedia.ui.home.mapper

import com.example.kinopedia.BaseTest
import com.example.sharedtest.filmForAdapterMock
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FilmForAdapterToFilmForAdapterModelMapperTest: BaseTest() {
    private lateinit var mapper: FilmForAdapterToFilmForAdapterModelMapper
    private val entity = filmForAdapterMock

    override fun setUp() {
        super.setUp()
        mapper = FilmForAdapterToFilmForAdapterModelMapper()
    }

    @Test
    fun `id is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.filmId).isEqualTo(entity.filmId)
    }
    @Test
    fun `posterUrl is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.posterUrl).isEqualTo(entity.posterUrl)
    }
    @Test
    fun `displayCountry is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.displayCountry).isEqualTo(entity.countries?.get(0)?.country.toString())
    }
    @Test
    fun `displayGenre is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.displayGenre).isEqualTo(entity.genres?.get(0)?.genre.toString())
    }
    @Test
    fun `displayName is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.displayName).isEqualTo(entity.nameRu)
    }
    @Test
    fun `displayOriginalName is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.displayOriginalName).isEqualTo(entity.nameEn)
    }
    @Test
    fun `displayRatingImdb is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.displayRatingImdb).isEqualTo("\u2014")
    }
    @Test
    fun `displayRatingKinopoisk is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.displayRatingKinopoisk).isEqualTo(entity.rating.toString())
    }
    @Test
    fun `displayYear is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.displayYear).isEqualTo(entity.year.toString())
    }
}