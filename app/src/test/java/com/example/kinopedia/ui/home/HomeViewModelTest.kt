package com.example.kinopedia.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.kinopedia.BaseTest
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.data.home.dto.ThisMonthFilm
import com.example.kinopedia.domain.interactors.GetHomeInteractor
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import com.example.kinopedia.ui.home.state.HomeScreenState
import com.example.kinopedia.ui.home.viewmodel.HomeViewModel
import com.example.sharedtest.filmForAdapterModelMock
import com.example.sharedtest.thisMonthFilmModelMock
import com.example.sharedtest.thisMonthFilmsMock
import com.example.sharedtest.topFilmsMock
import com.google.common.truth.Truth
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

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest: BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var mapper: BaseMapper<FilmForAdapter, FilmForAdapterModel>

    @MockK
    private lateinit var mapperPremiers: BaseMapper<ThisMonthFilm, ThisMonthFilmModel>

    @MockK
    private lateinit var getHomeInteractor: GetHomeInteractor

    @RelaxedMockK
    private lateinit var observer: Observer<HomeScreenState>

    private val arguments = mutableListOf<HomeScreenState>()

    private lateinit var viewModel: HomeViewModel

    override fun setUp() {
        super.setUp()
        every { mapper.map(any()) }.returns(filmForAdapterModelMock)
        every { mapperPremiers.map(any()) }.returns(thisMonthFilmModelMock)
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = HomeViewModel(
            getHomeInteractor,
            mapper,
            mapperPremiers
        )
        viewModel.screenState.observeForever(observer)
        every { observer.onChanged(capture(arguments)) } answers { }
    }

    @Test
    fun `fetchFilm success, Loading and Loaded events are received`() = runBlocking {

        coEvery { getHomeInteractor.getAwaitFilms() }.returns(
            CallResult.Success(
                topFilmsMock
            )
        )
        coEvery { getHomeInteractor.getComingSoonFilms() }.returns(
            CallResult.Success(
                topFilmsMock
            )
        )
        coEvery { getHomeInteractor.getPremierFilms() }.returns(
            CallResult.Success(
                thisMonthFilmsMock
            )
        )

        viewModel.fetchLists()

        Truth.assertThat(arguments[0]).isInstanceOf(HomeScreenState.Loading::class.java)
        Truth.assertThat(arguments[1]).isInstanceOf(HomeScreenState.Loaded::class.java)
    }
    @Test
    fun `fetchFilm UnknownError, Loading and Error events are received`() = runBlocking {

        coEvery { getHomeInteractor.getAwaitFilms() }.returns(
            CallResult.UnknownError
        )
        coEvery { getHomeInteractor.getComingSoonFilms() }.returns(
            CallResult.UnknownError
        )
        coEvery { getHomeInteractor.getPremierFilms() }.returns(
            CallResult.UnknownError
        )

        viewModel.fetchLists()

        Truth.assertThat(arguments[0]).isInstanceOf(HomeScreenState.Loading::class.java)
        Truth.assertThat(arguments[1]).isInstanceOf(HomeScreenState.Error::class.java)
    }
    @Test
    fun `fetchFilm HttpError, Loading and Error events are received`() = runBlocking {

        coEvery { getHomeInteractor.getAwaitFilms() }.returns(
            CallResult.HttpError(400, "message")
        )
        coEvery { getHomeInteractor.getComingSoonFilms() }.returns(
            CallResult.HttpError(400, "message")
        )
        coEvery { getHomeInteractor.getPremierFilms() }.returns(
            CallResult.HttpError(400, "message")
        )

        viewModel.fetchLists()

        Truth.assertThat(arguments[0]).isInstanceOf(HomeScreenState.Loading::class.java)
        Truth.assertThat(arguments[1]).isInstanceOf(HomeScreenState.Error::class.java)
    }
    @Test
    fun `fetchFilm IOError, Loading and Error events are received`() = runBlocking {

        coEvery { getHomeInteractor.getAwaitFilms() }.returns(
            CallResult.IOError
        )
        coEvery { getHomeInteractor.getComingSoonFilms() }.returns(
            CallResult.IOError
        )
        coEvery { getHomeInteractor.getPremierFilms() }.returns(
            CallResult.IOError
        )

        viewModel.fetchLists()

        Truth.assertThat(arguments[0]).isInstanceOf(HomeScreenState.Loading::class.java)
        Truth.assertThat(arguments[1]).isInstanceOf(HomeScreenState.Error::class.java)
    }
}
