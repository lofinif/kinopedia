package com.example.kinopedia.data.search

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.kinopedia.domain.repository.SearchRepository
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.sharedtest.filmForAdapterModelListRepoMock
import com.example.sharedtest.kinopoiskFilmModelListMock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SearchRepositoryImplTest @Inject constructor() : SearchRepository {
    override val pagedFlow: Flow<PagingData<FilmForAdapterModel>>
        get() = flowOf(PagingData.from(filmForAdapterModelListRepoMock))

    override fun getPagedFilmsKeyWord(pagerKeyWord: Pager<Int, KinopoiskFilmModel>): Flow<PagingData<KinopoiskFilmModel>> {
        return flowOf(PagingData.from(kinopoiskFilmModelListMock))
    }

    override fun getPagedGenreFlow(pagerGenre: Pager<Int, KinopoiskFilmModel>): Flow<PagingData<KinopoiskFilmModel>> {
        return flowOf(PagingData.from(kinopoiskFilmModelListMock))
    }
}