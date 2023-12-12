package com.example.kinopedia.domain.interactors

import com.example.kinopedia.data.dao.FavouriteDao
import com.example.kinopedia.data.favourite.dto.FavouriteEntity
import com.example.kinopedia.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetFavouriteInteractor @Inject constructor(
    private val favouriteRepository: FavouriteRepository,
    private val favouriteDao: FavouriteDao
) {
    val latestFilms get() = favouriteRepository.latestFilms

    suspend fun insert(favouriteEntity: FavouriteEntity) {
        favouriteDao.insert(favouriteEntity)
    }

    suspend fun checkId(filmId: Int): Int {
        return favouriteDao.checkId(filmId).first()
    }

    suspend fun delete(filmId: Int) {
        favouriteDao.deleteById(filmId)
    }
}