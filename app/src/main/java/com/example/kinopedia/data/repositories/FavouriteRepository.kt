package com.example.kinopedia.data.repositories

import com.example.kinopedia.data.dao.FavouriteDao
import com.example.kinopedia.data.entities.FavouriteEntity
import kotlinx.coroutines.flow.Flow

class FavouriteRepository(private val favouriteDao: FavouriteDao) {

    val allFilms: Flow<List<FavouriteEntity>> = favouriteDao.getFavourite()

    suspend fun insert(favouriteEntity: FavouriteEntity){
        favouriteDao.insert(favouriteEntity)
    }
    suspend fun checkId(filmId: Int){
        favouriteDao.checkId(filmId)
    }
}