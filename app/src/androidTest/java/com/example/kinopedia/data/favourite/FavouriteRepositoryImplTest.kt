package com.example.kinopedia.data.favourite

import com.example.kinopedia.data.favourite.dto.FavouriteEntity
import com.example.kinopedia.domain.repository.FavouriteRepository
import com.example.sharedtest.favouriteEntityMock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FavouriteRepositoryImplTest @Inject constructor(): FavouriteRepository{
    override val latestFilms: Flow<List<FavouriteEntity>>
        get() = flowOf(listOf(favouriteEntityMock))

    override suspend fun insert(favouriteEntity: FavouriteEntity) {
        //do nothing
    }

    override suspend fun checkId(filmId: Int): Int {
        //do nothing
        return 1
    }

    override suspend fun delete(filmId: Int) {
        //do nothing
    }
}