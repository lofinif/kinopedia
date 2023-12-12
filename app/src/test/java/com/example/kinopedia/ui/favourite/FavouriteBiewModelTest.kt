package com.example.kinopedia.ui.favourite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.kinopedia.BaseTest
import com.example.kinopedia.data.favourite.dto.FavouriteEntity
import com.example.kinopedia.domain.interactors.GetFavouriteInteractor
import com.example.kinopedia.ui.favourite.viewmodel.FavouriteViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavouriteViewModelTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getFavouriteInteractor: GetFavouriteInteractor

    private lateinit var viewModel: FavouriteViewModel

    override fun setUp() {
        super.setUp()
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val flow = flowOf<List<FavouriteEntity>>()
        coEvery { getFavouriteInteractor.latestFilms }.returns(flow)
        viewModel = FavouriteViewModel(getFavouriteInteractor)
    }

    @Test
    fun `deleteFavourite calls repository delete`() = runBlocking {
        val filmId = 1

        coEvery { getFavouriteInteractor.delete(filmId) } just Runs

        viewModel.deleteFavourite(filmId)

        coVerify { getFavouriteInteractor.delete(filmId) }
    }

    @Test
    fun `allFilms LiveData is updated after deleteFavourite`() = runBlocking {
        val filmId = 1
        val observer = mockk<Observer<List<FavouriteEntity>>>()
        val slot = slot<List<FavouriteEntity>>()

        every { observer.onChanged(emptyList()) }.returns(Unit)
        coEvery { getFavouriteInteractor.delete(any()) }.returns(observer.onChanged(emptyList()))

        viewModel.allFilms.observeForever(observer)

        viewModel.deleteFavourite(filmId)

        verify { observer.onChanged(capture(slot)) }
        assertThat(slot.captured).isEqualTo(emptyList<FavouriteEntity>())
    }
}