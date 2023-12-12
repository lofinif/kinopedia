package com.example.kinopedia.data.film

import com.example.kinopedia.BaseTest
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.domain.repository.FilmRepository
import com.example.kinopedia.network.services.ApiService
import com.example.sharedtest.actorFilmPageMock
import com.example.sharedtest.externalSourcesMock
import com.example.sharedtest.kinopoiskFilmMock
import com.example.sharedtest.kinopoiskSimilarFilmsMock
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.mockito.kotlin.any
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class FilmRepositoryImplTest : BaseTest() {

    @MockK
    private lateinit var api: ApiService

    private lateinit var repo: FilmRepository

    override fun setUp() {
        super.setUp()
        repo = FilmRepositoryImpl(api)
    }


    @Test
    fun `api getSimilarFilms call finishes without exception, result is Success`() {
        coEvery { api.getSimilarFilms(any()) }.returns(kinopoiskSimilarFilmsMock)
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getSimilarFilms(any())
            Truth.assertThat(result).isInstanceOf(CallResult.Success::class.java)
            Truth.assertThat((result as CallResult.Success).value).isEqualTo(
                kinopoiskSimilarFilmsMock
            )
        }
    }

    @Test
    fun `api getSimilarFilms throws IOException, result is IOError`() {
        coEvery { api.getSimilarFilms(any()) }.throws(IOException())
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getSimilarFilms(any())
            Truth.assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `api getSimilarFilms throws HttpException, result is HttpError`() {
        coEvery { api.getSimilarFilms(any()) }.throws(
            HttpException(
                Response.error<Nothing>(
                    400,
                    "".toResponseBody()
                )
            )
        )
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getSimilarFilms(any())
            Truth.assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            Truth.assertThat((result as CallResult.HttpError).code).isEqualTo(400)
        }
    }

    @Test
    fun `api getFilmById call finishes without exception, result is Success`() {
        coEvery { api.getFilmById(any()) }.returns(kinopoiskFilmMock)
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getFilmById(any())
            Truth.assertThat(result).isInstanceOf(CallResult.Success::class.java)
            Truth.assertThat((result as CallResult.Success).value).isEqualTo(
                kinopoiskFilmMock
            )
        }
    }

    @Test
    fun `api getFilmById throws IOException, result is IOError`() {
        coEvery { api.getFilmById(any()) }.throws(IOException())
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getFilmById(any())
            Truth.assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `api getFilmById throws HttpException, result is HttpError`() {
        coEvery { api.getFilmById(any()) }.throws(
            HttpException(
                Response.error<Nothing>(
                    400,
                    "".toResponseBody()
                )
            )
        )
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getFilmById(any())
            Truth.assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            Truth.assertThat((result as CallResult.HttpError).code).isEqualTo(400)
        }
    }

    @Test
    fun `api getActors call finishes without exception, result is Success`() {
        coEvery { api.getActorsAndStaff(any()) }.returns(listOf(actorFilmPageMock))
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getActors(any())
            Truth.assertThat(result).isInstanceOf(CallResult.Success::class.java)
            Truth.assertThat((result as CallResult.Success).value).isEqualTo(
                listOf(actorFilmPageMock)
            )
        }
    }

    @Test
    fun `api getActors throws IOException, result is IOError`() {
        coEvery { api.getActorsAndStaff(any()) }.throws(IOException())
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getActors(any())
            Truth.assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `api getActors throws HttpException, result is HttpError`() {
        coEvery { api.getActorsAndStaff(any()) }.throws(
            HttpException(
                Response.error<Nothing>(
                    400,
                    "".toResponseBody()
                )
            )
        )
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getActors(any())
            Truth.assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            Truth.assertThat((result as CallResult.HttpError).code).isEqualTo(400)
        }
    }

    @Test
    fun `api getExternalSources call finishes without exception, result is Success`() {
        coEvery { api.getExternalSources(any()) }.returns(externalSourcesMock)
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getExternalSources(any())
            Truth.assertThat(result).isInstanceOf(CallResult.Success::class.java)
            Truth.assertThat((result as CallResult.Success).value).isEqualTo(
                externalSourcesMock
            )
        }
    }

    @Test
    fun `api getExternalSources throws IOException, result is IOError`() {
        coEvery { api.getExternalSources(any()) }.throws(IOException())
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getExternalSources(any())
            Truth.assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `api getExternalSources throws HttpException, result is HttpError`() {
        coEvery { api.getExternalSources(any()) }.throws(
            HttpException(
                Response.error<Nothing>(
                    400,
                    "".toResponseBody()
                )
            )
        )
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getExternalSources(any())
            Truth.assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            Truth.assertThat((result as CallResult.HttpError).code).isEqualTo(400)
        }
    }

}