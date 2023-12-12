package com.example.kinopedia.data.filter

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.kinopedia.data.filter.dto.Filters
import com.example.kinopedia.domain.repository.FilterRepository
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.example.sharedtest.filtersRepoMock
import com.example.sharedtest.kinopoiskFilmModelListMock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FilterRepositoryImplTest @Inject constructor(): FilterRepository {
    override val countriesGenres: Flow<Filters>
        get() = flowOf(filtersRepoMock)

    override fun filmsWithFilters(pagerFilters: Pager<Int, KinopoiskFilmModel>): Flow<PagingData<KinopoiskFilmModel>>{
        return flowOf(PagingData.from(kinopoiskFilmModelListMock))
    }
}