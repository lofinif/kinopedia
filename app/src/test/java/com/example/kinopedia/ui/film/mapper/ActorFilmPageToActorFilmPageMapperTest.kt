package com.example.kinopedia.ui.film.mapper

import com.example.kinopedia.BaseTest
import com.example.sharedtest.actorFilmPageMock
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ActorFilmPageToActorFilmPageMapperTest : BaseTest() {
    private lateinit var mapper: ActorFilmPageToActorFilmPageMapper
    private val entity = actorFilmPageMock

    override fun setUp() {
        super.setUp()
        mapper = ActorFilmPageToActorFilmPageMapper()
    }

    @Test
    fun `displayName is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.displayName).isEqualTo(entity.nameRu)
    }

    @Test
    fun `displayDescription is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.displayDescription).isEqualTo(entity.description)
    }

    @Test
    fun `posterUrl is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.posterUrl).isEqualTo(entity.posterUrl)
    }
}