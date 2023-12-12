package com.example.kinopedia.domain.interactors

import com.example.kinopedia.BaseTest
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.domain.repository.FilmRepository
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
import org.junit.Test
import org.mockito.kotlin.any

@OptIn(ExperimentalCoroutinesApi::class)
class GetFilmsInteractorTest : BaseTest() {

    @MockK
    private lateinit var repo: FilmRepository

    private lateinit var interactor: GetFilmsInteractor

    override fun setUp() {
        super.setUp()
        interactor = GetFilmsInteractor(repo)
    }

    @Test
    fun `Repo getFilmById call returns success, interactor getFilmById also returns Success`() {
        coEvery { repo.getFilmById(any()) }.returns(
            CallResult.Success(kinopoiskFilmMock)
        )
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getFilmById(any())
            Truth.assertThat(result).isInstanceOf(CallResult.Success::class.java)
            Truth.assertThat((result as CallResult.Success).value).isEqualTo(kinopoiskFilmMock)
        }
    }

    @Test
    fun `Repo getFilmById call returns IOError, interactor getFilmById also returns IOError`() {
        coEvery { repo.getFilmById(any()) }.returns(CallResult.IOError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getFilmById(any())
            Truth.assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `Repo getFilmById call returns HttpError, interactor getFilmById also returns HttpError`() {
        coEvery { repo.getFilmById(any()) }.returns(CallResult.HttpError(400, "message"))
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getFilmById(any())
            Truth.assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            Truth.assertThat((result as CallResult.HttpError).code).isEqualTo(400)
            Truth.assertThat(result.message).isEqualTo("message")
        }
    }

    @Test
    fun `Repo getFilmById call returns UnknownError, interactor getFilmById also returns UnknownError`() {
        coEvery { repo.getFilmById(any()) }.returns(CallResult.UnknownError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getFilmById(any())
            Truth.assertThat(result).isInstanceOf(CallResult.UnknownError::class.java)
        }
    }

    @Test
    fun `Repo getSimilarFilms call returns success, interactor getSimilarFilms also returns Success`() {
        coEvery { repo.getSimilarFilms(any()) }.returns(
            CallResult.Success(kinopoiskSimilarFilmsMock)
        )
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getSimilarFilms(any())
            Truth.assertThat(result).isInstanceOf(CallResult.Success::class.java)
            Truth.assertThat((result as CallResult.Success).value)
                .isEqualTo(kinopoiskSimilarFilmsMock)
        }
    }

    @Test
    fun `Repo getSimilarFilms call returns IOError, interactor getSimilarFilms also returns IOError`() {
        coEvery { repo.getSimilarFilms(any()) }.returns(CallResult.IOError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getSimilarFilms(any())
            Truth.assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `Repo getSimilarFilms call returns HttpError, interactor getSimilarFilms also returns HttpError`() {
        coEvery { repo.getSimilarFilms(any()) }.returns(CallResult.HttpError(400, "message"))
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getSimilarFilms(any())
            Truth.assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            Truth.assertThat((result as CallResult.HttpError).code).isEqualTo(400)
            Truth.assertThat(result.message).isEqualTo("message")
        }
    }

    @Test
    fun `Repo getSimilarFilms call returns UnknownError, interactor getSimilarFilms also returns UnknownError`() {
        coEvery { repo.getSimilarFilms(any()) }.returns(CallResult.UnknownError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getSimilarFilms(any())
            Truth.assertThat(result).isInstanceOf(CallResult.UnknownError::class.java)
        }
    }

    @Test
    fun `Repo getActors call returns success, interactor getActors also returns Success`() {
        coEvery { repo.getActors(any()) }.returns(
            CallResult.Success(listOf(actorFilmPageMock))
        )
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getActors(any())
            Truth.assertThat(result).isInstanceOf(CallResult.Success::class.java)
            Truth.assertThat((result as CallResult.Success).value)
                .isEqualTo(listOf(actorFilmPageMock))
        }
    }

    @Test
    fun `Repo getActors call returns IOError, interactor getActors also returns IOError`() {
        coEvery { repo.getActors(any()) }.returns(CallResult.IOError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getActors(any())
            Truth.assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `Repo getActors call returns HttpError, interactor getActors also returns HttpError`() {
        coEvery { repo.getActors(any()) }.returns(CallResult.HttpError(400, "message"))
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getActors(any())
            Truth.assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            Truth.assertThat((result as CallResult.HttpError).code).isEqualTo(400)
            Truth.assertThat(result.message).isEqualTo("message")
        }
    }

    @Test
    fun `Repo getActors call returns UnknownError, interactor getActors also returns UnknownError`() {
        coEvery { repo.getActors(any()) }.returns(CallResult.UnknownError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getActors(any())
            Truth.assertThat(result).isInstanceOf(CallResult.UnknownError::class.java)
        }
    }

    @Test
    fun `Repo getExternalSources call returns success, interactor getExternalSources also returns Success`() {
        coEvery { repo.getExternalSources(any()) }.returns(
            CallResult.Success(externalSourcesMock)
        )
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getExternalSources(any())
            Truth.assertThat(result).isInstanceOf(CallResult.Success::class.java)
            Truth.assertThat((result as CallResult.Success).value).isEqualTo(externalSourcesMock)
        }
    }

    @Test
    fun `Repo getExternalSources call returns IOError, interactor getExternalSources also returns IOError`() {
        coEvery { repo.getExternalSources(any()) }.returns(CallResult.IOError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getExternalSources(any())
            Truth.assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `Repo getExternalSources call returns HttpError, interactor getExternalSources also returns HttpError`() {
        coEvery { repo.getExternalSources(any()) }.returns(CallResult.HttpError(400, "message"))
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getExternalSources(any())
            Truth.assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            Truth.assertThat((result as CallResult.HttpError).code).isEqualTo(400)
            Truth.assertThat(result.message).isEqualTo("message")
        }
    }

    @Test
    fun `Repo getExternalSources call returns UnknownError, interactor getExternalSources also returns UnknownError`() {
        coEvery { repo.getExternalSources(any()) }.returns(CallResult.UnknownError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getExternalSources(any())
            Truth.assertThat(result).isInstanceOf(CallResult.UnknownError::class.java)
        }
    }
}