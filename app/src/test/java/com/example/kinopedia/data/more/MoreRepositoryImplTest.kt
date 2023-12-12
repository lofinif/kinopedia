package com.example.kinopedia.data.more

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.kinopedia.BaseTest
import com.example.kinopedia.domain.repository.MoreRepository
import com.example.kinopedia.network.services.ApiService
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class MoreRepositoryImplTest : BaseTest() {

    @MockK
    lateinit var api: ApiService

    @MockK
    private lateinit var pagerComing: Pager<Int, FilmForAdapterModel>

    @MockK
    private lateinit var pagerAwait: Pager<Int, FilmForAdapterModel>

    @MockK
    private lateinit var pagerPremier: Pager<Int, ThisMonthFilmModel>

    private lateinit var repo: MoreRepository

    override fun setUp() {
        super.setUp()
        repo = MoreRepositoryImpl(pagerComing, pagerAwait, pagerPremier)
    }

    @Test
    fun `repo pagedPopularFlow returns correct flow`() {
        val flow = flowOf<PagingData<FilmForAdapterModel>>()
        mockkObject(repo)
        every { repo.pagedPopularFlow }.returns(flow)
        val result = repo.pagedPopularFlow
        Truth.assertThat(result).isEqualTo(flow)
    }

    @Test
    fun `repo pagedAwaitFlow returns correct flow`() {
        val flow = flowOf<PagingData<FilmForAdapterModel>>()
        mockkObject(repo)
        every { repo.pagedAwaitFlow }.returns(flow)
        val result = repo.pagedAwaitFlow
        Truth.assertThat(result).isEqualTo(flow)
    }

    @Test
    fun `repo pagedPremierFlow returns correct flow`() {
        val flow = flowOf<PagingData<ThisMonthFilmModel>>()
        mockkObject(repo)
        every { repo.pagedPremierFlow }.returns(flow)
        val result = repo.pagedPremierFlow
        Truth.assertThat(result).isEqualTo(flow)
    }
}
