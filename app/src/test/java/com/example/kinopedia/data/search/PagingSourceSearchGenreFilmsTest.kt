package com.example.kinopedia.data.search

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kinopedia.BaseTest
import com.example.kinopedia.data.film.dto.KinopoiskFilm
import com.example.kinopedia.network.services.ApiService
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.example.sharedtest.filmsByGenreMock
import com.example.sharedtest.kinopoiskFilmModelListMock
import com.example.sharedtest.kinopoiskFilmModelMock
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class PagingSourceSearchGenreFilmsTest : BaseTest() {

    @MockK
    private lateinit var api: ApiService

    @MockK
    private lateinit var mapper: BaseMapper<KinopoiskFilm, KinopoiskFilmModel>

    private lateinit var pagingSource: PagingSourceSearchGenreFilms

    private lateinit var pagingConfig: PagingConfig

    override fun setUp() {
        super.setUp()
        every { mapper.map(any()) }.returns(kinopoiskFilmModelMock)
        pagingConfig = PagingConfig(20)
        pagingSource = PagingSourceSearchGenreFilms(1, api, mapper)
    }

    @Test
    fun `getRefreshKey returns 1`() {
        val result = pagingSource.getRefreshKey(PagingState(emptyList(), null, PagingConfig(20), 0))
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `Api call finishes without exception, result is Page`() = runBlocking {
        coEvery { api.getFilmByFilters(genres = any(), page = any()) }.returns(filmsByGenreMock)
        val params = PagingSource.LoadParams.Refresh(1, 20, false)
        val result = pagingSource.load(params)
        assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
        assertThat((result as PagingSource.LoadResult.Page).data).isEqualTo(
            kinopoiskFilmModelListMock
        )
    }

    @Test
    fun `Api throws HttpException, result is Error`() = runBlocking {
        coEvery { api.getFilmByFilters(genres = any(), page = any()) }.throws(
            HttpException(
                Response.error<Nothing>(400, "".toResponseBody())
            )
        )
        val params = PagingSource.LoadParams.Refresh(1, 20, false)
        val result = pagingSource.load(params)
        assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
        assertThat((result as PagingSource.LoadResult.Error).throwable).isInstanceOf(
            HttpException::class.java
        )
    }

    @Test
    fun `Api throws IOException, result is Error`() = runBlocking {
        coEvery { api.getFilmByFilters(genres = any(), page = any()) }.throws(
            IOException()
        )
        val params = PagingSource.LoadParams.Refresh(1, 20, false)
        val result = pagingSource.load(params)
        assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
        assertThat((result as PagingSource.LoadResult.Error).throwable).isInstanceOf(
            IOException::class.java
        )
    }
}