package com.example.kinopedia.ui.filter.mapper

import com.example.kinopedia.network.models.FilterGenre
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.filter.model.FilterGenreModel
import javax.inject.Inject

class FilterGenreToFilterGenreModelMapper @Inject constructor(): BaseMapper<FilterGenre, FilterGenreModel>{
    override fun map(item: FilterGenre): FilterGenreModel {
        return FilterGenreModel(
            item.id,
            item.genre
        )
    }
}