package com.example.kinopedia.data.home

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.kinopedia.data.BaseRepo
import com.example.kinopedia.data.home.dto.TopFilms
import com.example.kinopedia.domain.repository.HomeRepository
import com.example.kinopedia.network.models.ThisMonthFilms
import com.example.kinopedia.network.services.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val api: ApiService): BaseRepo(), HomeRepository {
    private val monthFormat: SimpleDateFormat by lazy {
        SimpleDateFormat("MMMM", Locale.US)
    }

    private val calendar: Calendar by lazy {
        Calendar.getInstance()
    }

    private val year: Int by lazy {
        calendar.get(Calendar.YEAR)
    }

    private val month: String by lazy {
        monthFormat.format(calendar.time)
    }

    override val comingSoonFilms: Flow<TopFilms> = flow {
        val topFilms = api.getPopularFilms(1)
        emit(topFilms)
    }.flowOn(Dispatchers.IO)

    override val awaitFilms: Flow<TopFilms> = flow {
        val topFilms = api.getAwaitFilms(1)
        emit(topFilms)
    }.flowOn(Dispatchers.IO)

    override val premierFilms: Flow<ThisMonthFilms> = flow {
        val topFilms = api.getFilmsThisMonth(year,month, 1)
        emit(topFilms)
    }.flowOn(Dispatchers.IO)
}