package com.example.kinopedia.data.filter

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.kinopedia.data.filter.dto.Filters
import com.example.kinopedia.domain.repository.FilterRepository
import com.example.kinopedia.network.services.ApiService
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(val apiService: ApiService): FilterRepository {
    override val countriesGenres: Flow<Filters> = flow {
        val filters = apiService.getFilters()
        emit(filters)
    }.flowOn(Dispatchers.IO)

    override fun filmsWithFilters(pagerFilters: Pager<Int, KinopoiskFilmModel>): Flow<PagingData<KinopoiskFilmModel>> {
        return pagerFilters.flow
    }
}