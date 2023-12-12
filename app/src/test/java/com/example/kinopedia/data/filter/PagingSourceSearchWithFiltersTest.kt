package com.example.kinopedia.data.filter

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kinopedia.BaseTest
import com.example.kinopedia.network.services.ApiService
import com.example.kinopedia.ui.film.mapper.KinopoiskFilmToKinopoiskFilmModelMapper
import com.example.kinopedia.ui.filter.model.FilterSettings
import com.example.sharedtest.filmsByGenreMock
import com.example.sharedtest.kinopoiskFilmModelListMock
import com.example.sharedtest.kinopoiskFilmModelMock
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class PagingSourceSearchWithFiltersTest : BaseTest() {

    @MockK
    private lateinit var api: ApiService

    @MockK
    private lateinit var mapper: KinopoiskFilmToKinopoiskFilmModelMapper

    @MockK
    private lateinit var filterSettings: FilterSettings

    private lateinit var pagingSource: PagingSourceSearchWithFilters

    private lateinit var pagingConfig: PagingConfig

    override fun setUp() {
        super.setUp()
        every { mapper.map(any()) }.returns(kinopoiskFilmModelMock)
        every { filterSettings.countryId }.returns(1)
        every { filterSettings.genreId }.returns(1)
        every { filterSettings.sortType }.returns("sortType")
        every { filterSettings.type }.returns("type")
        every { filterSettings.keyWord }.returns("keyWord")
        every { filterSettings.minRating }.returns(0)
        every { filterSettings.maxRating }.returns(10)
        every { filterSettings.selectedYearFrom }.returns(1999)
        every { filterSettings.selectedYearTo }.returns(2020)
        pagingConfig = PagingConfig(20)
        pagingSource = PagingSourceSearchWithFilters(filterSettings, api, mapper)
    }

    @Test
    fun `getRefreshKey return 1`() {
        val result = pagingSource.getRefreshKey(PagingState(listOf(), null, pagingConfig, 0))
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `Api call finishes without exception, result is Page`() {
        coEvery {
            api.getFilmByFilters(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        }.returns(filmsByGenreMock)
        runTest(UnconfinedTestDispatcher()) {
            val params = PagingSource.LoadParams.Append(1, 20, false)
            val result = pagingSource.load(params)
            assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
            assertThat((result as PagingSource.LoadResult.Page).data).isEqualTo(
                kinopoiskFilmModelListMock
            )
        }
    }

    @Test
    fun `Api throws HttpException, result is Error`() {
        coEvery {
            api.getFilmByFilters(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        }.throws(
            HttpException(
                Response.error<Nothing>(400, "".toResponseBody())
            )
        )
        runTest(UnconfinedTestDispatcher()) {
            val params = PagingSource.LoadParams.Append(1, 20, false)
            val result = pagingSource.load(params)
            assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
            assertThat((result as PagingSource.LoadResult.Error).throwable).isInstanceOf(
                HttpException::class.java
            )
        }
    }

    @Test
    fun `Api throws IOException, result is Error`() {
        coEvery {
            api.getFilmByFilters(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        }.throws(IOException())
        runTest(UnconfinedTestDispatcher()) {
            val params = PagingSource.LoadParams.Append(1, 20, false)
            val result = pagingSource.load(params)
            assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
            assertThat((result as PagingSource.LoadResult.Error).throwable).isInstanceOf(
                IOException::class.java
            )
        }
    }
}