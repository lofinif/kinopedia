package com.example.kinopedia.ui.favourite.mapper

import com.example.kinopedia.data.favourite.dto.FavouriteEntity
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.favourite.model.FavouriteEntityModel
import javax.inject.Inject

class FavouriteEntityToFavouriteEntityModelMapper @Inject constructor(): BaseMapper<FavouriteEntity, FavouriteEntityModel> {
    override fun map(item: FavouriteEntity): FavouriteEntityModel {
        val displayName = item.nameRu.ifEmpty { item.nameOriginal }
        val displayNameOriginal = item.nameOriginal.ifEmpty { item.nameRu }
    return FavouriteEntityModel(
        item.filmId,
        displayName,
        displayNameOriginal,
        item.year,
        item.genre,
        item.country,
        item.ratingKinopoisk,
        item.ratingImdb,
        item.posterUrl)
    }
}