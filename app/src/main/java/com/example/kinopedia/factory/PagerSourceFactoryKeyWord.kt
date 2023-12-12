package com.example.kinopedia.factory

import com.example.kinopedia.data.search.PagingSourceSearchKeyWord
import dagger.assisted.AssistedFactory

@AssistedFactory
interface PagerSourceFactoryKeyWord {
    fun create(keyWord: String): PagingSourceSearchKeyWord
}