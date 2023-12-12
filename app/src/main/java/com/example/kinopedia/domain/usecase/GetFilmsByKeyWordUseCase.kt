package com.example.kinopedia.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.kinopedia.domain.repository.SearchRepository
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilmsByKeyWordUseCase @Inject constructor(private val repository: SearchRepository) {
    fun getPagedFlow(pagerKeyWord: Pager<Int, KinopoiskFilmModel>): Flow<PagingData<KinopoiskFilmModel>> {
        return repository.getPagedFilmsKeyWord(pagerKeyWord)
    }
}