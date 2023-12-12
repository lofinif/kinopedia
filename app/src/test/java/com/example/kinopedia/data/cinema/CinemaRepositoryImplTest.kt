package com.example.kinopedia.data.cinema

import com.example.kinopedia.BaseTest
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.domain.repository.CinemaRepository
import com.example.kinopedia.network.services.ApiServiceOSM
import com.example.kinopedia.network.services.ApiServiceOverpass
import com.example.sharedtest.cinemasMock
import com.example.sharedtest.cityOSMMock
import com.google.common.truth.Truth.assertThat
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
class CinemaRepositoryImplTest : BaseTest() {

    @MockK
    private lateinit var apiServiceOSM: ApiServiceOSM

    @MockK
    private lateinit var apiServiceOverpass: ApiServiceOverpass

    private lateinit var repo: CinemaRepository

    override fun setUp() {
        super.setUp()
        repo = CinemaRepositoryImpl(apiServiceOSM, apiServiceOverpass)
    }


    @Test
    fun `apiServiceOSM call finishes without exception, result is Success`() {
        coEvery { apiServiceOSM.getCity(any(), any()) }.returns(cityOSMMock)
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getCity(any(), any())
            assertThat(result).isInstanceOf(CallResult.Success::class.java)
            assertThat((result as CallResult.Success).value).isEqualTo(
                cityOSMMock
            )
        }
    }

    @Test
    fun `apiServiceOSM throws IOException, result is IOError`() {
        coEvery { apiServiceOSM.getCity(any(), any()) }.throws(IOException())
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getCity(any(), any())
            assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `apiServiceOSM throws HttpException, result is HttpError`() {
        coEvery {
            apiServiceOSM.getCity(
                any(),
                any()
            )
        }.throws(HttpException(Response.error<Nothing>(400, "".toResponseBody())))
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getCity(any(), any())
            assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            assertThat((result as CallResult.HttpError).code).isEqualTo(400)
        }
    }

    @Test
    fun `apiServiceOverpass call finishes without exception, result is Success`() {
        coEvery { apiServiceOverpass.getCinemas(any()) }.returns(cinemasMock)
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getCinemas(any())
            assertThat(result).isInstanceOf(CallResult.Success::class.java)
            assertThat((result as CallResult.Success).value).isEqualTo(
                cinemasMock
            )
        }
    }

    @Test
    fun `apiServiceOverpass throws IOException, result is IOError`() {
        coEvery { apiServiceOverpass.getCinemas(any()) }.throws(IOException())
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getCinemas(any())
            assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `apiServiceOverpass throws HttpException, result is HttpError`() {
        coEvery { apiServiceOverpass.getCinemas(any()) }.throws(
            HttpException(
                Response.error<Nothing>(
                    400,
                    "".toResponseBody()
                )
            )
        )
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getCinemas(any())
            assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            assertThat((result as CallResult.HttpError).code).isEqualTo(400)
        }
    }
}