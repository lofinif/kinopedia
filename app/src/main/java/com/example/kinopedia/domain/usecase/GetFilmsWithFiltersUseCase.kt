package com.example.kinopedia.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.kinopedia.domain.repository.FilterRepository
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilmsWithFiltersUseCase @Inject constructor(val repository: FilterRepository) {
    fun getFilmsWithFilters(pagerFilter: Pager<Int, KinopoiskFilmModel>):
             Flow<PagingData<KinopoiskFilmModel>> {
        return repository.filmsWithFilters(pagerFilter)
    }
}