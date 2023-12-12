package com.example.kinopedia.ui.film

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.kinopedia.BaseTest
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.favourite.FavouriteRepositoryImpl
import com.example.kinopedia.data.film.ExternalSource
import com.example.kinopedia.data.film.dto.ActorFilmPage
import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.data.film.dto.KinopoiskFilm
import com.example.kinopedia.domain.interactors.GetFilmsInteractor
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.film.model.ActorFilmPageModel
import com.example.kinopedia.ui.film.model.ExternalSourceModel
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.example.kinopedia.ui.film.state.FilmScreenState
import com.example.kinopedia.ui.film.viewmodel.FilmPageViewModel
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.sharedtest.actorFilmPageMock
import com.example.sharedtest.actorFilmPageModelMock
import com.example.sharedtest.externalSourceModelMock
import com.example.sharedtest.externalSourcesMock
import com.example.sharedtest.favouriteEntityMock
import com.example.sharedtest.filmForAdapterModelMock
import com.example.sharedtest.kinopoiskFilmMock
import com.example.sharedtest.kinopoiskFilmModelMock
import com.example.sharedtest.kinopoiskSimilarFilmsMock
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
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
class FilmPageViewModelTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var filmMapper: BaseMapper<KinopoiskFilm, KinopoiskFilmModel>

    @MockK
    private lateinit var externalMapper: BaseMapper<ExternalSource, ExternalSourceModel>

    @MockK
    private lateinit var similarMapper: BaseMapper<FilmForAdapter, FilmForAdapterModel>

    @MockK
    private lateinit var actorMapper: BaseMapper<ActorFilmPage, ActorFilmPageModel>

    @MockK
    private lateinit var repository: FavouriteRepositoryImpl

    @MockK
    private lateinit var getFilmsInteractor: GetFilmsInteractor

    @RelaxedMockK
    private lateinit var observer: Observer<FilmScreenState>

    private val arguments = mutableListOf<FilmScreenState>()

    private lateinit var viewModel: FilmPageViewModel

    override fun setUp() {
        super.setUp()
        every { filmMapper.map(any()) }.returns(kinopoiskFilmModelMock)
        every { similarMapper.map(any()) }.returns(filmForAdapterModelMock)
        every { actorMapper.map(any()) }.returns(actorFilmPageModelMock)
        every { externalMapper.map(any()) }.returns(externalSourceModelMock)
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = FilmPageViewModel(
            filmMapper,
            externalMapper,
            similarMapper,
            actorMapper,
            repository,
            getFilmsInteractor
        )
        viewModel.screenState.observeForever(observer)
        every { observer.onChanged(capture(arguments)) } answers { }
    }

    @Test
    fun `fetchFilm success, Loading and Loaded events are received`() = runBlocking {

        coEvery { getFilmsInteractor.getFilmById(any()) }.returns(CallResult.Success(kinopoiskFilmMock))
        coEvery { getFilmsInteractor.getSimilarFilms(any()) }.returns(CallResult.Success(kinopoiskSimilarFilmsMock))
        coEvery { getFilmsInteractor.getActors(any()) }.returns(CallResult.Success(listOf(actorFilmPageMock)))
        coEvery { getFilmsInteractor.getExternalSources(any()) }.returns(CallResult.Success(externalSourcesMock))

        viewModel.fetchFilm(any())

        assertThat(arguments[0]).isInstanceOf(FilmScreenState.Loading::class.java)
        assertThat(arguments[1]).isInstanceOf(FilmScreenState.Loaded::class.java)
    }

    @Test
    fun `saveFavourite inserts into repository`() = runBlocking {
        viewModel.saveFavourite("any()")
        coVerify { repository.insert(favouriteEntityMock) }
    }
}