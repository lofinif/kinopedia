package com.example.kinopedia.data.more

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kinopedia.data.home.dto.ThisMonthFilm
import com.example.kinopedia.network.services.ApiService
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MorePagingSourcePremier (
    private val apiService: ApiService,
    private val mapper: BaseMapper<ThisMonthFilm, ThisMonthFilmModel>,
): PagingSource<Int, ThisMonthFilmModel>() {
    private val monthFormat: SimpleDateFormat by lazy { SimpleDateFormat("MMMM", Locale.US) }
    private val calendar: Calendar by lazy { Calendar.getInstance() }
    private val year: Int by lazy { calendar.get(Calendar.YEAR) }
    private val month: String by lazy { monthFormat.format(calendar.time) }
    companion object {
        private const val TAG = "MorePagingSourcePremier"
        private const val STARTING_PAGE = 1
    }
    override fun getRefreshKey(state: PagingState<Int, ThisMonthFilmModel>): Int {
        return state.anchorPosition ?: STARTING_PAGE
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ThisMonthFilmModel> = withContext(
        Dispatchers.IO) {
        val fromPage = params.key ?: STARTING_PAGE
        val page = params.key ?: 1
        try {
            val result = apiService.getFilmsThisMonth(year, month, fromPage)
            val mappedFilms = result.items.map(mapper::map)
            val nextKey = if (page < result.total) page + 1 else null
            LoadResult.Page(mappedFilms, null, nextKey)
        } catch (e: Exception){
            Log.e(TAG, "error while fetching data from kinopoisk api", e)
            LoadResult.Error(e)
        }
    }
}
