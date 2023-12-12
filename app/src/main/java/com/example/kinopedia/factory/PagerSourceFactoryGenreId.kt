package com.example.kinopedia.factory

import com.example.kinopedia.data.search.PagingSourceSearchGenreFilms
import dagger.assisted.AssistedFactory

@AssistedFactory
interface PagerSourceFactoryGenreId {
    fun createPager(genreId: Int): PagingSourceSearchGenreFilms
}