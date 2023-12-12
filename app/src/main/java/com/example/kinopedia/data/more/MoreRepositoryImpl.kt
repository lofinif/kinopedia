package com.example.kinopedia.data.more

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.kinopedia.domain.repository.MoreRepository
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class MoreRepositoryImpl @Inject constructor(
    @Named("popular") private val pagerPopular: Pager<Int, FilmForAdapterModel>,
    @Named("await") private val pagerAwait: Pager<Int, FilmForAdapterModel>,
    @Named("premier") private val pagerPremier: Pager<Int, ThisMonthFilmModel>,
): MoreRepository {
    override val pagedAwaitFlow: Flow<PagingData<FilmForAdapterModel>>
        get() = pagerAwait.flow
    override val pagedPopularFlow: Flow<PagingData<FilmForAdapterModel>>
        get() = pagerPopular.flow
    override val pagedPremierFlow: Flow<PagingData<ThisMonthFilmModel>>
        get() = pagerPremier.flow

}