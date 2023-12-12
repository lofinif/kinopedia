package com.example.kinopedia.ui.home.mapper

import com.example.kinopedia.BaseTest
import com.example.sharedtest.thisMonthFilmMock
import com.google.common.truth.Truth
import org.junit.Test

class ThisMonthFilmToThisMonthFilmModelMapperTest : BaseTest() {
    private lateinit var mapper: ThisMonthFilmToThisMonthFilmModelMapper
    private val entity = thisMonthFilmMock

    override fun setUp() {
        super.setUp()
        mapper = ThisMonthFilmToThisMonthFilmModelMapper()
    }

    @Test
    fun `id is mapped correctly`() {
        val result = mapper.map(entity)
        Truth.assertThat(result.filmId).isEqualTo(entity.kinopoiskId)
    }

    @Test
    fun `displayName is mapped correctly`() {
        val result = mapper.map(entity)
        Truth.assertThat(result.displayName).isEqualTo(entity.nameRu)
    }

    @Test
    fun `posterUrl is mapped correctly`() {
        val result = mapper.map(entity)
        Truth.assertThat(result.posterUrl).isEqualTo(entity.posterUrl)
    }

    @Test
    fun `displayCountry is mapped correctly`() {
        val result = mapper.map(entity)
        Truth.assertThat(result.displayCountry).isEqualTo(entity.countries.get(0).country)
    }

    @Test
    fun `displayGenre is mapped correctly`() {
        val result = mapper.map(entity)
        Truth.assertThat(result.displayGenre).isEqualTo(entity.genres.get(0).genre)
    }

    @Test
    fun `displayOriginalName is mapped correctly`() {
        val result = mapper.map(entity)
        Truth.assertThat(result.displayOriginalName).isEqualTo(entity.nameEn)
    }

    @Test
    fun `displayYear is mapped correctly`() {
        val result = mapper.map(entity)
        Truth.assertThat(result.displayYear).isEqualTo(entity.year.toString())
    }

    @Test
    fun `dash is mapped correctly`() {
        val result = mapper.map(entity)
        Truth.assertThat(result.dash).isEqualTo("\u2014")
    }

}