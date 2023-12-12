package com.example.kinopedia.factory

import com.example.kinopedia.data.filter.PagingSourceSearchWithFilters
import com.example.kinopedia.ui.filter.model.FilterSettings
import dagger.assisted.AssistedFactory

@AssistedFactory
interface PagerSourceFactoryFilters {
    fun create(filterSettings: FilterSettings): PagingSourceSearchWithFilters
}