package com.example.kinopedia.data.search

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.kinopedia.BaseTest
import com.example.kinopedia.domain.repository.SearchRepository
import com.example.kinopedia.network.services.ApiService
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class SearchRepositoryImplTest : BaseTest() {


    @MockK
    lateinit var api: ApiService

    @MockK
    private lateinit var pager: Pager<Int, FilmForAdapterModel>

    @MockK
    private lateinit var pagerGenre: Pager<Int, KinopoiskFilmModel>

    @MockK
    private lateinit var pagerKeyWord: Pager<Int, KinopoiskFilmModel>

    private lateinit var repo: SearchRepository

    override fun setUp() {
        super.setUp()
        repo = SearchRepositoryImpl(pager)
    }


    @Test
    fun `pagedFlow returns correct flow`() {
        val flow = flowOf<PagingData<FilmForAdapterModel>>()
        every { repo.pagedFlow }.returns(flow)

        val result = repo.pagedFlow
        Truth.assertThat(result).isEqualTo(flow)
    }

    @Test
    fun `getPagedGenreFlow returns correct flow`() {
        val flow = flowOf<PagingData<KinopoiskFilmModel>>()
        every { repo.getPagedGenreFlow(pagerGenre) }.returns(flow)

        val result = repo.getPagedGenreFlow(pagerGenre)
        Truth.assertThat(result).isEqualTo(flow)
    }

    @Test
    fun `getPagedFilmsKeyWord returns correct flow`() {
        val flow = flowOf<PagingData<KinopoiskFilmModel>>()
        every { repo.getPagedFilmsKeyWord(pagerKeyWord) }.returns(flow)

        val result = repo.getPagedFilmsKeyWord(pagerKeyWord)
        Truth.assertThat(result).isEqualTo(flow)
    }
}