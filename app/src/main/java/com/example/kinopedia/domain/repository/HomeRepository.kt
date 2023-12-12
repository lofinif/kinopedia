package com.example.kinopedia.domain.repository

import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.home.dto.TopFilms
import com.example.kinopedia.network.models.ThisMonthFilms

interface HomeRepository {
    suspend fun getComingSoonFilms(): CallResult<TopFilms>
    suspend fun getPremierFilms(): CallResult<ThisMonthFilms>
    suspend fun getAwaitFilms(): CallResult<TopFilms>

}