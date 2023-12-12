package com.example.kinopedia.ui.cinema.mapper

import com.example.kinopedia.BaseTest
import com.example.sharedtest.cinemaOSMMock
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CinemaOSMToCinemaOSMModelMapperTest: BaseTest() {

    private lateinit var mapper: CinemaOSMToCinemaOSMModelMapper
    private val entity = cinemaOSMMock

    override fun setUp() {
        super.setUp()
        mapper = CinemaOSMToCinemaOSMModelMapper()
    }

    @Test
    fun `id is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.id).isEqualTo(entity.id)
    }
    @Test
    fun `latitude is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.latitude).isEqualTo(entity.lat)
    }
    @Test
    fun `longitude is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.longitude).isEqualTo(entity.lon)
    }
    @Test
    fun `tags is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.tags).isEqualTo(entity.tags)
    }

}