package com.example.kinopedia.domain.interactors

import androidx.paging.PagingData
import com.example.kinopedia.BaseTest
import com.example.kinopedia.domain.repository.MoreRepository
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class GetMoreInteractorTest : BaseTest() {

    @MockK
    private lateinit var repo: MoreRepository

    private lateinit var interactor: GetMoreInteractor

    override fun setUp() {
        super.setUp()
        interactor = GetMoreInteractor(repo)
    }

    @Test
    fun `interactor pagedPopularFlow returns correct flow`() {
        val flow = flowOf<PagingData<FilmForAdapterModel>>()
        every { repo.pagedPopularFlow }.returns(flow)
        val result = interactor.pagedPopularFlow
        assertThat(result).isEqualTo(flow)
    }

    @Test
    fun `interactor pagedAwaitFlow returns correct flow`() {
        val flow = flowOf<PagingData<FilmForAdapterModel>>()
        every { repo.pagedAwaitFlow }.returns(flow)
        val result = interactor.pagedAwaitFlow
        assertThat(result).isEqualTo(flow)
    }

    @Test
    fun `interactor pagedPremierFlow returns correct flow`() {
        val flow = flowOf<PagingData<ThisMonthFilmModel>>()
        every { repo.pagedPremierFlow }.returns(flow)
        val result = interactor.pagedPremierFlow
        assertThat(result).isEqualTo(flow)
    }
}