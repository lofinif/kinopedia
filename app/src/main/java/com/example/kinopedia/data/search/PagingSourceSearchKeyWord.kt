package com.example.kinopedia.data.search

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kinopedia.data.film.dto.KinopoiskFilm
import com.example.kinopedia.network.services.ApiService
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PagingSourceSearchKeyWord @AssistedInject constructor(
    @Assisted private val keyWord: String,
    private val apiService: ApiService,
    private val mapper: BaseMapper<KinopoiskFilm, KinopoiskFilmModel>
) : PagingSource<Int, KinopoiskFilmModel>() {


    companion object {
        private const val TAG = "PagingSourceSearchTopFilms"
        const val STARTING_PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, KinopoiskFilmModel>): Int {
        return state.anchorPosition ?: STARTING_PAGE
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KinopoiskFilmModel> =
        withContext(
            Dispatchers.IO
        ) {
            Log.e("KeyWordSource", "keyword: $keyWord")
            val fromPage = params.key ?: STARTING_PAGE
            val page = params.key ?: 1
            try {
                val result = apiService.getFilmByFilters(keyword = keyWord, page = fromPage)
                val mappedFilms = result.items.map(mapper::map)
                val nextKey = if (page < result.totalPages) page + 1 else null
                LoadResult.Page(mappedFilms, null, nextKey)
            } catch (e: Exception) {
                Log.e(TAG, "error while fetching data from kinopoisk api", e)
                LoadResult.Error(e)
            }
        }
}