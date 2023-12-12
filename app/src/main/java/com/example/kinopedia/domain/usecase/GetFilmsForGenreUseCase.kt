package com.example.kinopedia.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.kinopedia.domain.repository.SearchRepository
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilmsForGenreUseCase @Inject constructor(val repository: SearchRepository) {
    fun getPagedFlow(pagerGenre: Pager<Int, KinopoiskFilmModel>):
            Flow<PagingData<KinopoiskFilmModel>> {
        return repository.getPagedGenreFlow(pagerGenre)
    }
}