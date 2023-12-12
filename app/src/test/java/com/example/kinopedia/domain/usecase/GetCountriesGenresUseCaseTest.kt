package com.example.kinopedia.domain.usecase

import com.example.kinopedia.BaseTest
import com.example.kinopedia.data.filter.dto.Filters
import com.example.kinopedia.domain.repository.FilterRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class GetCountriesGenresUseCaseTest: BaseTest() {

    @MockK
    private lateinit var repo: FilterRepository

    private lateinit var useCase: GetCountriesGenresUseCase
    override fun setUp() {
        super.setUp()
        useCase = GetCountriesGenresUseCase(repo)
    }

    @Test
    fun `useCase returns correct flow`(){
        val flow = flowOf<Filters>()
        every { repo.countriesGenres }.returns(flow)
        val result = useCase.countriesGenres
        assertThat(result).isEqualTo(flow)
    }

}