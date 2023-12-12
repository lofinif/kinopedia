package com.example.kinopedia.data.home

import com.example.kinopedia.data.BaseRepo
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.home.dto.TopFilms
import com.example.kinopedia.data.safeApiCall
import com.example.kinopedia.domain.repository.HomeRepository
import com.example.kinopedia.network.models.ThisMonthFilms
import com.example.kinopedia.network.services.ApiService
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val api: ApiService) : BaseRepo(),
    HomeRepository {

    companion object {
        const val TAG = "HomeRepositoryImpl"
    }

    private val monthFormat: SimpleDateFormat by lazy {
        SimpleDateFormat("MMMM", Locale.US)
    }

    private val calendar: Calendar by lazy { Calendar.getInstance() }


    private val year: Int by lazy {
        calendar.get(Calendar.YEAR)
    }

    private val month: String by lazy {
        monthFormat.format(calendar.time)
    }


    override suspend fun getComingSoonFilms(): CallResult<TopFilms> = safeApiCall {
        api.getPopularFilms(1)
    }

    override suspend fun getPremierFilms(): CallResult<ThisMonthFilms> = safeApiCall {
        api.getFilmsThisMonth(year, month, 1)
    }

    override suspend fun getAwaitFilms(): CallResult<TopFilms> = safeApiCall {
        api.getAwaitFilms(1)
    }

}