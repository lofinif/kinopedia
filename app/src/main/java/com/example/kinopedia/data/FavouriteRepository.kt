package com.example.kinopedia.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList

class FavouriteRepository(private val favouriteDao: FavouriteDao) {

    val allFilms: Flow<List<FavouriteEntity>> = favouriteDao.getFavourite()

    suspend fun insert(favouriteEntity: FavouriteEntity){
        favouriteDao.insert(favouriteEntity)
    }
    suspend fun checkId(filmId: Int){
        favouriteDao.checkId(filmId)
    }
}