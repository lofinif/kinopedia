package com.example.kinopedia.ui.favourite.mapper

import com.example.kinopedia.BaseTest
import com.example.sharedtest.favouriteEntityMock
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FavouriteEntityToFavouriteEntityModelMapperTest: BaseTest() {
    private lateinit var mapper: FavouriteEntityToFavouriteEntityModelMapper
    private val entity = favouriteEntityMock

    override fun setUp() {
        super.setUp()
        mapper = FavouriteEntityToFavouriteEntityModelMapper()
    }

    @Test
    fun `id is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.filmId).isEqualTo(entity.filmId)
    }
    @Test
    fun `displayName is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.displayName).isEqualTo(entity.nameRu)
    }
    @Test
    fun `posterUrl is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.posterUrl).isEqualTo(entity.posterUrl)
    }
    @Test
    fun `displayGenre is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.displayGenre).isEqualTo(entity.genre)
    }
    @Test
    fun `displayCountry is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.displayCountry).isEqualTo(entity.country)
    }
    @Test
    fun `displayImdbRating is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.displayImdbRating).isEqualTo(entity.ratingImdb)
    }
    @Test
    fun `displayKinopoiskRating is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.displayKinopoiskRating).isEqualTo(entity.ratingKinopoisk)
    }
    @Test
    fun `displayYear is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.displayYear).isEqualTo(entity.year)
    }

}