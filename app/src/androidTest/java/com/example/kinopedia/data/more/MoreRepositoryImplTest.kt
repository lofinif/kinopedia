package com.example.kinopedia.data.more

import androidx.paging.PagingData
import com.example.kinopedia.domain.repository.MoreRepository
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import com.example.sharedtest.filmForAdapterModelListMock
import com.example.sharedtest.thisMonthFilmModelListMock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MoreRepositoryImplTest @Inject constructor(): MoreRepository {

    override val pagedAwaitFlow: Flow<PagingData<FilmForAdapterModel>>
        get() = flowOf(PagingData.from(filmForAdapterModelListMock))

    override val pagedPopularFlow: Flow<PagingData<FilmForAdapterModel>>
        get() = flowOf(PagingData.from(filmForAdapterModelListMock))

    override val pagedPremierFlow: Flow<PagingData<ThisMonthFilmModel>>
        get() = flowOf(PagingData.from(thisMonthFilmModelListMock))
}