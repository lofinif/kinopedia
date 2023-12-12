package com.example.kinopedia.data.more

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kinopedia.BaseTest
import com.example.kinopedia.network.services.ApiService
import com.example.kinopedia.ui.home.mapper.FilmForAdapterToFilmForAdapterModelMapper
import com.example.sharedtest.filmForAdapterModelListMock
import com.example.sharedtest.filmForAdapterModelMock
import com.example.sharedtest.topFilmsMock
import com.google.common.truth.Truth
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
class MorePagingSourceTest : BaseTest() {

    @MockK
    private lateinit var api: ApiService

    @MockK
    private lateinit var mapper: FilmForAdapterToFilmForAdapterModelMapper

    private lateinit var pagingSource: MorePagingSource

    private lateinit var pagingConfig: PagingConfig

    override fun setUp() {
        super.setUp()
        every { mapper.map(any()) }.returns(filmForAdapterModelMock)
        pagingConfig = PagingConfig(20)
        pagingSource = MorePagingSource(api, mapper)
    }

    @Test
    fun `getRefreshKey return 1`() {
        val result = pagingSource.getRefreshKey(PagingState(listOf(), null, pagingConfig, 0))
        Truth.assertThat(result).isEqualTo(1)
    }

    @Test
    fun `Api call finishes without exception, result is Page`() {
        coEvery { api.getAwaitFilms(any()) }.returns(topFilmsMock)
        runTest(UnconfinedTestDispatcher()) {
            val params = PagingSource.LoadParams.Append(1, 20, false)
            val result = pagingSource.load(params)
            Truth.assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
            Truth.assertThat((result as PagingSource.LoadResult.Page).data).isEqualTo(
                filmForAdapterModelListMock
            )
        }
    }

    @Test
    fun `Api throws HttpException, result is Error`() {
        coEvery { api.getAwaitFilms(any()) }.throws(
            HttpException(
                Response.error<Nothing>(400, "".toResponseBody())
            )
        )
        runTest(UnconfinedTestDispatcher()) {
            val params = PagingSource.LoadParams.Append(1, 20, false)
            val result = pagingSource.load(params)
            Truth.assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
            Truth.assertThat((result as PagingSource.LoadResult.Error).throwable).isInstanceOf(
                HttpException::class.java
            )
        }
    }

    @Test
    fun `Api throws IOException, result is Error`() {
        coEvery { api.getAwaitFilms(any()) }.throws(
            IOException()
        )
        runTest(UnconfinedTestDispatcher()) {
            val params = PagingSource.LoadParams.Append(1, 20, false)
            val result = pagingSource.load(params)
            Truth.assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
            Truth.assertThat((result as PagingSource.LoadResult.Error).throwable).isInstanceOf(
                IOException::class.java
            )
        }
    }
}