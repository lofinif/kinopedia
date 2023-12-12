package com.example.kinopedia.ui.cinema

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.kinopedia.BaseTest
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.cinema.dto.CinemaOSM
import com.example.kinopedia.data.cinema.dto.CityOSM
import com.example.kinopedia.domain.interactors.GetCinemasInteractor
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.cinema.model.CinemaOSMModel
import com.example.kinopedia.ui.cinema.model.CityOSMModel
import com.example.kinopedia.ui.cinema.state.CinemaScreenState
import com.example.kinopedia.ui.cinema.viewmodel.NearestCinemaViewModel
import com.example.kinopedia.utils.LocationProvider
import com.example.sharedtest.cinemaOSMModelMock
import com.example.sharedtest.cinemasMock
import com.example.sharedtest.cityOSMMock
import com.example.sharedtest.cityOSMModelMock
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any

@OptIn(ExperimentalCoroutinesApi::class)
class NearestCinemaViewModelTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getCinemasInteractor: GetCinemasInteractor

    @MockK
    private lateinit var locationProvider: LocationProvider

    @MockK
    private lateinit var mapperCity: BaseMapper<CityOSM, CityOSMModel>

    @MockK
    private lateinit var mapperCinemas: BaseMapper<CinemaOSM, CinemaOSMModel>

    @RelaxedMockK
    private lateinit var observer: Observer<CinemaScreenState>

    private val arguments = mutableListOf<CinemaScreenState>()

    private lateinit var viewModel: NearestCinemaViewModel

    override fun setUp() {
        super.setUp()
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = NearestCinemaViewModel(getCinemasInteractor, locationProvider, mapperCity, mapperCinemas)
        viewModel.screenState.observeForever(observer)
        every { observer.onChanged(capture(arguments)) } answers { }
    }

    @Test
    fun `fetchCinemas success, Loading and Loaded events are received`() = runBlocking{
        coEvery { getCinemasInteractor.getCity(any(), any()) }.returns(CallResult.Success(
            cityOSMMock))
        coEvery { mapperCity.map(any()) }.returns(cityOSMModelMock)
        coEvery { getCinemasInteractor.getCinemas(any()) }.returns(CallResult.Success(cinemasMock))
        coEvery { mapperCinemas.map(any()) }.returns(cinemaOSMModelMock)

        viewModel.fetchCinemas(any(), any())

        assertThat(arguments[0]).isInstanceOf(CinemaScreenState.Loading::class.java)
        assertThat(arguments[1]).isInstanceOf(CinemaScreenState.Loaded::class.java)
        assertThat(viewModel.city.value).isEqualTo(cityOSMModelMock)
        assertThat(viewModel.cinemas.value).isEqualTo(listOf(cinemaOSMModelMock))
    }

    @Test
    fun `fetchCinemas error, Loading and Error events are received`() = runBlocking {
        coEvery { getCinemasInteractor.getCity(any(), any()) }.returns(CallResult.UnknownError)

        viewModel.fetchCinemas(any(), any())

        assertThat(arguments[0]).isInstanceOf(CinemaScreenState.Loading::class.java)
        assertThat(arguments[1]).isInstanceOf(CinemaScreenState.Error::class.java)
    }
}