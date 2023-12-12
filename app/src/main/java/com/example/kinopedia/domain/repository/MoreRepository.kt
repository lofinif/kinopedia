package com.example.kinopedia.domain.repository

import androidx.paging.PagingData
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import kotlinx.coroutines.flow.Flow

interface MoreRepository {
    val pagedPopularFlow : Flow<PagingData<FilmForAdapterModel>>
    val pagedAwaitFlow : Flow<PagingData<FilmForAdapterModel>>
    val pagedPremierFlow : Flow<PagingData<ThisMonthFilmModel>>
}
