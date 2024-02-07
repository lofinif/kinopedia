package com.example.kinopedia.data.filter

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kinopedia.network.services.ApiService
import com.example.kinopedia.ui.film.mapper.KinopoiskFilmToKinopoiskFilmModelMapper
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.example.kinopedia.ui.filter.model.FilterSettings
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PagingSourceSearchWithFilters @AssistedInject constructor(
    @Assisted private val filterSettings: FilterSettings,
    private val apiService: ApiService,
    private val mapper: KinopoiskFilmToKinopoiskFilmModelMapper
) : PagingSource<Int, KinopoiskFilmModel>() {

    companion object {
        private const val TAG = "PagingSourceSearchWithFilters"
        const val STARTING_PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, KinopoiskFilmModel>): Int {
        return state.anchorPosition ?: STARTING_PAGE
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KinopoiskFilmModel> =
        withContext(
            Dispatchers.IO
        ) {
            val countryId =
                if (filterSettings.countryId == -1) null else arrayOf(filterSettings.countryId)
            val genreId =
                if (filterSettings.genreId == -1) null else arrayOf(filterSettings.genreId)
            val fromPage = params.key ?: STARTING_PAGE
            val page = params.key ?: 1
            try {
                val result = apiService.getFilmByFilters(
                    countryId,
                    genreId,
                    filterSettings.sortType,
                    filterSettings.type,
                    filterSettings.keyWord,
                    filterSettings.minRating,
                    filterSettings.maxRating,
                    filterSettings.selectedYearFrom,
                    filterSettings.selectedYearTo,
                    fromPage
                )
                val mappedFilms = result.items.map(mapper::map)
                val nextKey = if (page < result.totalPages) page + 1 else null
                LoadResult.Page(mappedFilms, null, nextKey)
            } catch (e: Exception) {
                Log.e(TAG, "error while fetching data from kinopoisk api", e)
                LoadResult.Error(e)
            }
        }
}