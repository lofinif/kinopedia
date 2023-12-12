package com.example.kinopedia.ui.genre.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.kinopedia.domain.usecase.GetFilmsForGenreUseCase
import com.example.kinopedia.factory.PagerSourceFactoryGenreId
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    val factory: PagerSourceFactoryGenreId,
    getFilmsForGenreUseCase: GetFilmsForGenreUseCase,
) : ViewModel() {

    var genreId = 0

    val pager by lazy { providePager(factory, genreId) }

    val flowGenre by lazy { getFilmsForGenreUseCase.getPagedFlow(pager).cachedIn(viewModelScope) }

    private fun providePager(
        factory: PagerSourceFactoryGenreId,
        genreId: Int
    ): Pager<Int, KinopoiskFilmModel> {
        factory.createPager(genreId)
        return Pager(PagingConfig(20)) {
            factory.createPager(genreId)
        }
    }
}