package com.example.kinopedia.data.home

import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.home.dto.TopFilms
import com.example.kinopedia.domain.repository.HomeRepository
import com.example.kinopedia.network.models.ThisMonthFilms
import com.example.sharedtest.thisMonthFilmsRepoMock
import com.example.sharedtest.topFilmsRepoMock
import com.example.sharedtest.topFilmsRepoMock2
import javax.inject.Inject

class HomeRepositoryImplTest @Inject constructor() : HomeRepository {
    override suspend fun getComingSoonFilms(): CallResult<TopFilms> {
        return CallResult.Success(topFilmsRepoMock)
    }

    override suspend fun getPremierFilms(): CallResult<ThisMonthFilms> {
        return CallResult.Success(thisMonthFilmsRepoMock)
    }

    override suspend fun getAwaitFilms(): CallResult<TopFilms> {
        return CallResult.Success(topFilmsRepoMock2)
    }
}