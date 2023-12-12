package com.example.kinopedia.ui.film.mapper

import com.example.kinopedia.BaseTest
import com.example.sharedtest.externalSourceMock
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExternalSourceToExternalSourceModelMapperTest: BaseTest(){
    private lateinit var mapper: ExternalSourceToExternalSourceModelMapper
    private val entity = externalSourceMock

    override fun setUp() {
        super.setUp()
        mapper = ExternalSourceToExternalSourceModelMapper()
    }

    @Test
    fun `url is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.url).isEqualTo(entity.url)
    }
    @Test
    fun `displayLogoUrl is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.displayLogoUrl).isEqualTo(entity.logoUrl)
    }
    @Test
    fun `displayPlatform is mapped correctly`(){
        val result = mapper.map(entity)
        assertThat(result.displayPlatform).isEqualTo(entity.platform)
    }
}