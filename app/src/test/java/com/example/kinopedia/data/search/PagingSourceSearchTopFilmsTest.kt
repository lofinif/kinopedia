package com.example.kinopedia.data.search

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kinopedia.BaseTest
import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.network.services.ApiService
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.sharedtest.filmForAdapterModelListMock
import com.example.sharedtest.filmForAdapterModelMock
import com.example.sharedtest.topFilmsMock
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class PagingSourceSearchTopFilmsTest : BaseTest() {

    @MockK
    private lateinit var api: ApiService

    @MockK
    private lateinit var mapper: BaseMapper<FilmForAdapter, FilmForAdapterModel>


    private lateinit var pagingSource: PagingSourceSearchTopFilms

    private lateinit var pagingConfig: PagingConfig

    override fun setUp() {
        super.setUp()
        every { mapper.map(any()) }.returns(filmForAdapterModelMock)
        pagingConfig = PagingConfig(20)
        pagingSource = PagingSourceSearchTopFilms(api, mapper)
    }

    @Test
    fun `getRefreshKey returns 1`() {
        val result = pagingSource.getRefreshKey(PagingState(emptyList(), null, PagingConfig(20), 0))
        Truth.assertThat(result).isEqualTo(1)
    }

    @Test
    fun `Api call finishes without exception, result is Page`() = runBlocking {
        coEvery { api.getTopFilms(any()) }.returns(topFilmsMock)
        val params = PagingSource.LoadParams.Refresh(1, 20, false)
        val result = pagingSource.load(params)
        Truth.assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
        Truth.assertThat((result as PagingSource.LoadResult.Page).data).isEqualTo(
            filmForAdapterModelListMock
        )
    }

    @Test
    fun `Api throws HttpException, result is Error`() = runBlocking {
        coEvery { api.getTopFilms(any()) }.throws(
            HttpException(
                Response.error<Nothing>(400, "".toResponseBody())
            )
        )
        val params = PagingSource.LoadParams.Refresh(1, 20, false)
        val result = pagingSource.load(params)
        Truth.assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
        Truth.assertThat((result as PagingSource.LoadResult.Error).throwable).isInstanceOf(
            HttpException::class.java
        )
    }

    @Test
    fun `Api throws IOException, result is Error`() = runBlocking {
        coEvery { api.getTopFilms(any()) }.throws(
            IOException()
        )
        val params = PagingSource.LoadParams.Refresh(1, 20, false)
        val result = pagingSource.load(params)
        Truth.assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
        Truth.assertThat((result as PagingSource.LoadResult.Error).throwable).isInstanceOf(
            IOException::class.java
        )
    }
}