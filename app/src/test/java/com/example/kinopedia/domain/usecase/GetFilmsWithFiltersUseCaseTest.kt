package com.example.kinopedia.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.kinopedia.BaseTest
import com.example.kinopedia.domain.repository.FilterRepository
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class GetFilmsWithFiltersUseCaseTest: BaseTest() {

    @MockK
    lateinit var repo: FilterRepository
    @MockK
    lateinit var pagerFilters: Pager<Int, KinopoiskFilmModel>

    private lateinit var useCase: GetFilmsWithFiltersUseCase
    override fun setUp() {
        super.setUp()
        useCase = GetFilmsWithFiltersUseCase(repo)
    }
    @Test
    fun `useCase returns correct flow`(){
        val flow = flowOf<PagingData<KinopoiskFilmModel>>()
        every { repo.filmsWithFilters(pagerFilters) }.returns(flow)
        val result = useCase.getFilmsWithFilters(pagerFilters)
        assertThat(result).isEqualTo(flow)
    }
}