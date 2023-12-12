package com.example.kinopedia.domain.repository

import com.example.kinopedia.data.favourite.dto.FavouriteEntity
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    val latestFilms: Flow<List<FavouriteEntity>>
    suspend fun insert(favouriteEntity: FavouriteEntity)
    suspend fun checkId(filmId: Int): Int
    suspend fun delete(filmId: Int)
}