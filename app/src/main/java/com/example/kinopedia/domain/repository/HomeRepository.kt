package com.example.kinopedia.domain.repository

import androidx.paging.PagingData
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.home.dto.TopFilms
import com.example.kinopedia.network.models.ThisMonthFilms
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    val comingSoonFilms: Flow<TopFilms>
    val awaitFilms: Flow<TopFilms>
    val premierFilms: Flow<ThisMonthFilms>
}