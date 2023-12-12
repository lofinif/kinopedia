package com.example.kinopedia.data.filter

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.kinopedia.BaseTest
import com.example.kinopedia.data.filter.dto.Filters
import com.example.kinopedia.domain.repository.FilterRepository
import com.example.kinopedia.network.services.ApiService
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class FilterRepositoryImplTest : BaseTest() {

    @MockK
    lateinit var api: ApiService

    @MockK
    private lateinit var pager: Pager<Int, KinopoiskFilmModel>

    private lateinit var repo: FilterRepository

    override fun setUp() {
        super.setUp()
        repo = FilterRepositoryImpl(api)
    }

    @Test
    fun `repo countries and genres returns correct flow`() {
        val flow = flowOf<Filters>()
        mockkObject(repo)
        every { repo.countriesGenres }.returns(flow)
        val result = repo.countriesGenres
        assertThat(result).isEqualTo(flow)
    }

    @Test
    fun `repo films with filters returns correct flow`() {
        val flow = flowOf<PagingData<KinopoiskFilmModel>>()
        every { pager.flow }.returns(flow)
        val result = pager.flow
        assertThat(result).isEqualTo(flow)
    }
}