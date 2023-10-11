package com.example.kinopedia.ui.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.network.FilmApi
import com.example.kinopedia.network.KinopoiskFilm
import com.example.kinopedia.network.LoadingStatus
import com.example.kinopedia.network.interceptor
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor

class GenreViewModel: ViewModel() {
    var pageCount = 1

    private val liveDataFilmsByFilter = MutableLiveData<List<KinopoiskFilm>>()
    val dataFilmsByFilter: LiveData<List<KinopoiskFilm>> = liveDataFilmsByFilter

    private val _status = MutableLiveData<LoadingStatus>()
    val status: LiveData<LoadingStatus> = _status

    private val _genre = MutableLiveData<String>()
    val genre: LiveData<String> = _genre.map { String.format("%1s", it) }

    init {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    }


    fun getFilmsByFiler(
        countries: Array<Int>?, genres: Array<Int>, order: String, type: String,
        keyword: String, ratingFrom: Int, ratingTo: Int, yearFrom: Int,
        yearTo: Int, page: Int
    ) = viewModelScope.launch {
        if (liveDataFilmsByFilter.value.isNullOrEmpty()) {
            _status.value = LoadingStatus.LOADING
            try {
                val list = FilmApi.retrofitService.getFilmByFilters(
                    countries, genres, order, type, keyword,
                    ratingFrom, ratingTo, yearFrom, yearTo, page
                )

                pageCount = list.totalPages
                liveDataFilmsByFilter.value = list.items
                _status.value = LoadingStatus.DONE
            } catch (e: Exception) {
                _status.value = LoadingStatus.ERROR
            }
        }
    }
    fun loadNextItems( countries: Array<Int>?, genres: Array<Int>, order: String, type: String,
                       keyword: String, ratingFrom: Int, ratingTo: Int, yearFrom: Int,
                       yearTo: Int, page: Int) = viewModelScope.launch {
        val list = FilmApi.retrofitService.getFilmByFilters(
            countries, genres, order, type, keyword,
            ratingFrom, ratingTo, yearFrom, yearTo, page
        )
        val currentList = liveDataFilmsByFilter.value?.toMutableList() ?: mutableListOf()
        currentList.addAll(list.items)
        liveDataFilmsByFilter.value = currentList.distinctBy{it.kinopoiskId}
    }
}