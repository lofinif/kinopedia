package com.example.kinopedia.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.kinopedia.data.filter.dto.Filters
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    val countriesGenres: Flow<Filters>
    fun filmsWithFilters(pagerFilters: Pager<Int, KinopoiskFilmModel>): Flow<PagingData<KinopoiskFilmModel>>
}