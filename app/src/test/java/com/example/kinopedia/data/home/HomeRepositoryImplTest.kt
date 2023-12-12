package com.example.kinopedia.data.home

import com.example.kinopedia.BaseTest
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.domain.repository.HomeRepository
import com.example.kinopedia.network.services.ApiService
import com.example.sharedtest.thisMonthFilmsMock
import com.example.sharedtest.topFilmsMock
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
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
class HomeRepositoryImplTest : BaseTest() {

    @MockK
    private lateinit var api: ApiService

    private lateinit var repo: HomeRepository
    override fun setUp() {
        super.setUp()
        repo = HomeRepositoryImpl(api)
    }

    @Test
    fun `api getComingSoonFilms call finishes without exception, result is Success`() {
        coEvery { api.getPopularFilms(any()) }.returns(topFilmsMock)
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getComingSoonFilms()
            assertThat(result).isInstanceOf(CallResult.Success::class.java)
            assertThat((result as CallResult.Success).value).isEqualTo(topFilmsMock)
        }
    }

    @Test
    fun `api getComingSoonFilms throws IOException, result is IOError`() {
        coEvery { api.getPopularFilms(any()) }.throws(IOException())
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getComingSoonFilms()
            assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `api getComingSoonFilms throws HttpException, result is HttpError`() {
        coEvery { api.getPopularFilms(any()) }.throws(
            HttpException(
                Response.error<Nothing>(
                    400,
                    "message".toResponseBody()
                )
            )
        )
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getComingSoonFilms()
            assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            assertThat((result as CallResult.HttpError).code).isEqualTo(400)
        }
    }

    @Test
    fun `api getAwaitFilms call finishes without exception, result is Success`() {
        coEvery { api.getAwaitFilms(any()) }.returns(topFilmsMock)
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getAwaitFilms()
            assertThat(result).isInstanceOf(CallResult.Success::class.java)
            assertThat((result as CallResult.Success).value).isEqualTo(topFilmsMock)
        }
    }

    @Test
    fun `api getAwaitFilms throws IOException, result is IOError`() {
        coEvery { api.getAwaitFilms(any()) }.throws(IOException())
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getAwaitFilms()
            assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `api getAwaitFilms throws HttpException, result is HttpError`() {
        coEvery { api.getAwaitFilms(any()) }.throws(
            HttpException(
                Response.error<Nothing>(
                    400,
                    "message".toResponseBody()
                )
            )
        )
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getAwaitFilms()
            assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            assertThat((result as CallResult.HttpError).code).isEqualTo(400)
        }
    }

    @Test
    fun `api getPremierFilms call finishes without exception, result is Success`() {
        coEvery { api.getFilmsThisMonth(any(), any(), any()) }.returns(thisMonthFilmsMock)
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getPremierFilms()
            assertThat(result).isInstanceOf(CallResult.Success::class.java)
            assertThat((result as CallResult.Success).value).isEqualTo(thisMonthFilmsMock)
        }
    }

    @Test
    fun `api getPremierFilms throws IOException, result is IOError`() {
        coEvery { api.getFilmsThisMonth(any(), any(), any()) }.throws(IOException())
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getPremierFilms()
            assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `api getPremierFilms throws HttpException, result is HttpError`() {
        coEvery {
            api.getFilmsThisMonth(
                any(),
                any(),
                any()
            )
        }.throws(HttpException(Response.error<Nothing>(400, "message".toResponseBody())))
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getPremierFilms()
            assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            assertThat((result as CallResult.HttpError).code).isEqualTo(400)
        }
    }
}