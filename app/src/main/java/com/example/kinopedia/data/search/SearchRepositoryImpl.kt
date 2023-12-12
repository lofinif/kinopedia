package com.example.kinopedia.data.search

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.kinopedia.domain.repository.SearchRepository
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class SearchRepositoryImpl @Inject constructor(
    @Named("TopFilms") private val pager: Pager<Int, FilmForAdapterModel>,
) : SearchRepository {

    override val pagedFlow: Flow<PagingData<FilmForAdapterModel>>
        get() = pager.flow


    override fun getPagedGenreFlow(pagerGenre: Pager<Int, KinopoiskFilmModel>): Flow<PagingData<KinopoiskFilmModel>> {
        return pagerGenre.flow
    }

    override fun getPagedFilmsKeyWord(pagerKeyWord: Pager<Int, KinopoiskFilmModel>): Flow<PagingData<KinopoiskFilmModel>> {
        return pagerKeyWord.flow
    }


}