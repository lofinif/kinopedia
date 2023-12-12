package com.example.kinopedia.domain.interactors

import com.example.kinopedia.BaseTest
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.domain.repository.CinemaRepository
import com.example.sharedtest.cinemasMock
import com.example.sharedtest.cityOSMMock
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.any

@OptIn(ExperimentalCoroutinesApi::class)
class GetCinemasInteractorTest : BaseTest() {

    @MockK
    private lateinit var repo: CinemaRepository

    private lateinit var interactor: GetCinemasInteractor

    override fun setUp() {
        super.setUp()
        interactor = GetCinemasInteractor(repo)
    }


    @Test
    fun `Repo getCity call returns success, interactor getCity also returns Success`() {
        coEvery { repo.getCity(any(), any()) }.returns(
            CallResult.Success(
                cityOSMMock
            )
        )
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getCity(any(), any())
            assertThat(result).isInstanceOf(CallResult.Success::class.java)
            assertThat((result as CallResult.Success).value).isEqualTo(cityOSMMock)
        }
    }

    @Test
    fun `Repo getCity call returns IOError, interactor getCity also returns IOError`() {
        coEvery { repo.getCity(any(), any()) }.returns(CallResult.IOError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getCity(any(), any())
            assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `Repo getCity call returns HttpError, interactor getCity also returns HttpError`() {
        coEvery { repo.getCity(any(), any()) }.returns(CallResult.HttpError(400, "message"))
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getCity(any(), any())
            assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            assertThat((result as CallResult.HttpError).code).isEqualTo(400)
            assertThat(result.message).isEqualTo("message")
        }
    }

    @Test
    fun `Repo getCity call returns UnknownError, interactor getCity also returns UnknownError`() {
        coEvery { repo.getCity(any(), any()) }.returns(CallResult.UnknownError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getCity(any(), any())
            assertThat(result).isInstanceOf(CallResult.UnknownError::class.java)
        }
    }

    @Test
    fun `Repo getCinemas call returns success, interactor getCinemas also returns Success`() {
        coEvery { repo.getCinemas(any()) }.returns(
            CallResult.Success(
                cinemasMock
            )
        )
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getCinemas(any())
            assertThat(result).isInstanceOf(CallResult.Success::class.java)
            assertThat((result as CallResult.Success).value).isEqualTo(cinemasMock)
        }
    }

    @Test
    fun `Repo getCinemas call returns IOError, interactor getCinemas also returns IOError`() {
        coEvery { repo.getCinemas(any()) }.returns(CallResult.IOError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getCinemas(any())
            assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `Repo getCinemas call returns HttpError, interactor getCinemas also returns HttpError`() {
        coEvery { repo.getCinemas(any()) }.returns(CallResult.HttpError(400, "message"))
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getCinemas(any())
            assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            assertThat((result as CallResult.HttpError).code).isEqualTo(400)
            assertThat(result.message).isEqualTo("message")
        }
    }

    @Test
    fun `Repo getCinemas call returns UnknownError, interactor getCinemas also returns UnknownError`() {
        coEvery { repo.getCinemas(any()) }.returns(CallResult.UnknownError)
        runTest(UnconfinedTestDispatcher()) {
            val result = interactor.getCinemas(any())
            assertThat(result).isInstanceOf(CallResult.UnknownError::class.java)
        }
    }
}