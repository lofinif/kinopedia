package com.example.kinopedia.data.favourite

import com.example.kinopedia.data.dao.FavouriteDao
import com.example.kinopedia.data.favourite.dto.FavouriteEntity
import com.example.kinopedia.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(private val favouriteDao: FavouriteDao) :
    FavouriteRepository {

    override val latestFilms: Flow<List<FavouriteEntity>> = favouriteDao.getLatestItem()
    override suspend fun insert(favouriteEntity: FavouriteEntity) {
        favouriteDao.insert(favouriteEntity)
    }

    override suspend fun checkId(filmId: Int): Int {
        return favouriteDao.checkId(filmId).first()
    }

    override suspend fun delete(filmId: Int) {
        favouriteDao.deleteById(filmId)
    }
}