package com.example.kinopedia.domain.interactors

import com.example.kinopedia.BaseTest
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.domain.repository.HomeRepository
import com.example.sharedtest.thisMonthFilmsMock
import com.example.sharedtest.topFilmsMock
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetHomeInteractorTest : BaseTest() {

    @MockK
    private lateinit var repo: HomeRepository

    private lateinit var interactor: GetHomeInteractor

    override fun setUp() {
        super.setUp()
        interactor = GetHomeInteractor(repo)
    }

    @Test
    fun `Repo getComingSoonFilms call returns success, interactor getComingSoonFilms also returns Success`() {
        coEvery { repo.getComingSoonFilms() }.returns(
            CallResult.Success(topFilmsMock)
        )
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getComingSoonFilms()
            Truth.assertThat(result).isInstanceOf(CallResult.Success::class.java)
            Truth.assertThat((result as CallResult.Success).value).isEqualTo(topFilmsMock)
        }
    }

    @Test
    fun `Repo getComingSoonFilms call returns IOError, interactor getComingSoonFilms also returns IOError`() {
        coEvery { repo.getComingSoonFilms() }.returns(CallResult.IOError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getComingSoonFilms()
            Truth.assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `Repo getComingSoonFilms call returns HttpError, interactor getComingSoonFilms also returns HttpError`() {
        coEvery { repo.getComingSoonFilms() }.returns(CallResult.HttpError(400, "message"))
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getComingSoonFilms()
            Truth.assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            Truth.assertThat((result as CallResult.HttpError).code).isEqualTo(400)
            Truth.assertThat(result.message).isEqualTo("message")
        }
    }

    @Test
    fun `Repo getComingSoonFilms call returns UnknownError, interactor getComingSoonFilms also returns UnknownError`() {
        coEvery { repo.getComingSoonFilms() }.returns(CallResult.UnknownError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getComingSoonFilms()
            Truth.assertThat(result).isInstanceOf(CallResult.UnknownError::class.java)
        }
    }

    @Test
    fun `Repo getAwaitFilms call returns success, interactor getAwaitFilms also returns Success`() {
        coEvery { repo.getAwaitFilms() }.returns(
            CallResult.Success(topFilmsMock)
        )
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getAwaitFilms()
            Truth.assertThat(result).isInstanceOf(CallResult.Success::class.java)
            Truth.assertThat((result as CallResult.Success).value).isEqualTo(topFilmsMock)
        }
    }

    @Test
    fun `Repo getAwaitFilms call returns IOError, interactor getAwaitFilms also returns IOError`() {
        coEvery { repo.getAwaitFilms() }.returns(CallResult.IOError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getAwaitFilms()
            Truth.assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `Repo getAwaitFilms call returns HttpError, interactor getAwaitFilms also returns HttpError`() {
        coEvery { repo.getAwaitFilms() }.returns(CallResult.HttpError(400, "message"))
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getAwaitFilms()
            Truth.assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            Truth.assertThat((result as CallResult.HttpError).code).isEqualTo(400)
            Truth.assertThat(result.message).isEqualTo("message")
        }
    }

    @Test
    fun `Repo getAwaitFilms call returns UnknownError, interactor getAwaitFilms also returns UnknownError`() {
        coEvery { repo.getAwaitFilms() }.returns(CallResult.UnknownError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getAwaitFilms()
            Truth.assertThat(result).isInstanceOf(CallResult.UnknownError::class.java)
        }
    }

    @Test
    fun `Repo getPremierFilms call returns success, interactor getPremierFilms also returns Success`() {
        coEvery { repo.getPremierFilms() }.returns(
            CallResult.Success(thisMonthFilmsMock)
        )
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getPremierFilms()
            Truth.assertThat(result).isInstanceOf(CallResult.Success::class.java)
            Truth.assertThat((result as CallResult.Success).value).isEqualTo(thisMonthFilmsMock)
        }
    }

    @Test
    fun `Repo getPremierFilms call returns IOError, interactor getPremierFilms also returns IOError`() {
        coEvery { repo.getPremierFilms() }.returns(CallResult.IOError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getPremierFilms()
            Truth.assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `Repo getPremierFilms call returns HttpError, interactor getPremierFilms also returns HttpError`() {
        coEvery { repo.getPremierFilms() }.returns(CallResult.HttpError(400, "message"))
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getPremierFilms()
            Truth.assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            Truth.assertThat((result as CallResult.HttpError).code).isEqualTo(400)
            Truth.assertThat(result.message).isEqualTo("message")
        }
    }

    @Test
    fun `Repo getPremierFilms call returns UnknownError, interactor getPremierFilms also returns UnknownError`() {
        coEvery { repo.getPremierFilms() }.returns(CallResult.UnknownError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getPremierFilms()
            Truth.assertThat(result).isInstanceOf(CallResult.UnknownError::class.java)
        }
    }

}