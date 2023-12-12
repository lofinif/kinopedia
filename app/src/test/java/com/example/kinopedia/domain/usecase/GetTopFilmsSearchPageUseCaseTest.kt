package com.example.kinopedia.domain.usecase

import androidx.paging.PagingData
import com.example.kinopedia.BaseTest
import com.example.kinopedia.domain.repository.SearchRepository
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class GetTopFilmsSearchPageUseCaseTest: BaseTest() {

    @MockK
    private lateinit var repo: SearchRepository

    private lateinit var useCase: GetTopFilmsSearchPageUseCase

    override fun setUp() {
        super.setUp()
        useCase = GetTopFilmsSearchPageUseCase(repo)
    }
    @Test
    fun `useCase return correct paged flow`(){
        val flow = flowOf<PagingData<FilmForAdapterModel>>()
        every { repo.pagedFlow }.returns(flow)
        val result = useCase.pagedFlow
        assertThat(result).isEqualTo(flow)
    }
}