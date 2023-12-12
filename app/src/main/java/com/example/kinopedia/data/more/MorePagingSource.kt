package com.example.kinopedia.data.more

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.network.services.ApiService
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MorePagingSource(
    private val apiService: ApiService,
    private val mapper: BaseMapper<FilmForAdapter, FilmForAdapterModel>,
) : PagingSource<Int, FilmForAdapterModel>() {
    companion object {
        private const val TAG = "MorePagingSource"
        private const val STARTING_PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, FilmForAdapterModel>): Int {
        return state.anchorPosition ?: STARTING_PAGE
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmForAdapterModel> =
        withContext(Dispatchers.IO) {
            val fromPage = params.key ?: STARTING_PAGE
            val page = params.key ?: 1
            try {
                val users = apiService.getAwaitFilms(fromPage)
                val mappedUsers = users.films.map(mapper::map)
                val nextKey = if (page < users.pagesCount) page + 1 else null
                LoadResult.Page(mappedUsers, null, nextKey)
            } catch (e: Exception) {
                Log.e(TAG, "error while fetching data from kinopoisk api", e)
                LoadResult.Error(e)
            }

        }
}