package com.example.kinopedia.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    val pagedFlow: Flow<PagingData<FilmForAdapterModel>>
    fun getPagedGenreFlow(pagerGenre: Pager<Int, KinopoiskFilmModel>): Flow<PagingData<KinopoiskFilmModel>>

    fun getPagedFilmsKeyWord(pagerKeyWord: Pager<Int, KinopoiskFilmModel>): Flow<PagingData<KinopoiskFilmModel>>
}
