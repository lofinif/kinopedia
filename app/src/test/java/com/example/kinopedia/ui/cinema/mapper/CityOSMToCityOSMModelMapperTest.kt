package com.example.kinopedia.ui.cinema.mapper

import com.example.kinopedia.BaseTest
import com.example.sharedtest.cityOSMMock
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CityOSMToCityOSMModelMapperTest : BaseTest() {

    private lateinit var mapper: CityOSMToCityOSMModelMapper
    private val entity = cityOSMMock

    override fun setUp() {
        super.setUp()
        mapper = CityOSMToCityOSMModelMapper()
    }

    @Test
    fun `id is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.lon).isEqualTo(entity.lon)
    }

    @Test
    fun `lat is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.lat).isEqualTo(entity.lat)
    }

    @Test
    fun `address is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.address).isEqualTo(entity.address)
    }

    @Test
    fun `displayName is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.displayName).isEqualTo(entity.display_name)
    }

    @Test
    fun `placeId is mapped correctly`() {
        val result = mapper.map(entity)
        assertThat(result.placeId).isEqualTo(entity.place_id)
    }

}