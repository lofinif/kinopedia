package com.example.kinopedia.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.kinopedia.BaseTest
import com.example.kinopedia.domain.repository.SearchRepository
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class GetFilmsByKeyWordUseCaseTest : BaseTest() {

    @MockK
    private lateinit var repo: SearchRepository

    @MockK
    private lateinit var pager: Pager<Int, KinopoiskFilmModel>

    private lateinit var useCase: GetFilmsByKeyWordUseCase

    override fun setUp() {
        super.setUp()
        useCase = GetFilmsByKeyWordUseCase(repo)
    }

    @Test
    fun `useCase returns correct flow`() {
        val flow = flowOf<PagingData<KinopoiskFilmModel>>()
        every { repo.getPagedFilmsKeyWord(pager) }.returns(flow)
        val result = useCase.getPagedFlow(pager)
        assertThat(result).isEqualTo(flow)
    }

}