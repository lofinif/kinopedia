package com.example.kinopedia.data.repositories

import com.example.kinopedia.data.dao.FavouriteDao
import com.example.kinopedia.data.entities.FavouriteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class FavouriteRepository(private val favouriteDao: FavouriteDao) {

    val latestFilms: Flow<List<FavouriteEntity>> = favouriteDao.getLatestItem()
    suspend fun insert(favouriteEntity: FavouriteEntity){
        favouriteDao.insert(favouriteEntity)
    }
     suspend fun checkId(filmId: Int): Int{
       return favouriteDao.checkId(filmId).first()
    }

    suspend fun delete(filmId: Int){
        favouriteDao.deleteById(filmId)
    }
}