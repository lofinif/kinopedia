package com.example.kinopedia.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.network.Film
import com.example.kinopedia.network.FilmApi
import com.example.kinopedia.network.KinopoiskFilm
import com.example.kinopedia.network.LoadingStatus
import com.example.kinopedia.network.Persons
import com.example.kinopedia.network.interceptor
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor

class SearchViewModel: ViewModel() {
    var pageCount = 1

    private val liveDataFilmsByFilter = MutableLiveData<List<KinopoiskFilm>>()
    val dataFilmsByFilter: LiveData<List<KinopoiskFilm>> = liveDataFilmsByFilter

    private val _statusTopFilm = MutableLiveData<LoadingStatus>()
    val statusTopFilm: LiveData<LoadingStatus> = _statusTopFilm

    private val _statusSearchKeyWord = MutableLiveData<LoadingStatus>()
    val statusSearchKeyWord: LiveData<LoadingStatus> = _statusSearchKeyWord

    private val liveDataTopFilms = MutableLiveData<List<Film>>()
    val dataTopFilms: LiveData<List<Film>> = liveDataTopFilms

    private val liveDataPersons = MutableLiveData<Persons>()
    val dataPersons : LiveData<Persons> = liveDataPersons

    private val _genre = MutableLiveData<String>()
    val genre: LiveData<String> = _genre.map { String.format("%1s", it) }

    init {
        _statusSearchKeyWord.value = LoadingStatus.DONE
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    fun getFilmsByKeyWord(countries: Array<Int>?, genres: Array<Int>?, order: String, type: String,
                          keyword: String, ratingFrom: Int, ratingTo: Int, yearFrom: Int,
                          yearTo: Int, page: Int) =
        viewModelScope.launch {
            _statusSearchKeyWord.value = LoadingStatus.LOADING
        try {
            val list = FilmApi.retrofitService.getFilmByFilters(
                countries, genres, order, type, keyword,
                ratingFrom, ratingTo, yearFrom, yearTo, page)
            liveDataFilmsByFilter.value = list.items
            pageCount = list.totalPages
            _statusSearchKeyWord.value = LoadingStatus.DONE
           } catch (e: Exception){
            _statusSearchKeyWord.value = LoadingStatus.ERROR
       }
    }

    fun getTopFilms() = viewModelScope.launch {
        if (liveDataTopFilms.value.isNullOrEmpty()) {
            _statusTopFilm.value = LoadingStatus.LOADING
            try {
                val list = FilmApi.retrofitService.getTopFilms()
                liveDataTopFilms.value = list.films
                pageCount = list.pagesCount
                _statusTopFilm.value = LoadingStatus.DONE
            } catch (e: Exception) {
                _statusTopFilm.value = LoadingStatus.ERROR
            }
        }
    }
    fun loadNextItems(countries: Array<Int>?, genres: Array<Int>?, order: String, type: String,
                      keyword: String, ratingFrom: Int, ratingTo: Int, yearFrom: Int,
                      yearTo: Int, page: Int) = viewModelScope.launch {
        val list = FilmApi.retrofitService.getFilmByFilters(
            countries, genres, order, type, keyword,
            ratingFrom, ratingTo, yearFrom, yearTo, page)
        val currentList = liveDataFilmsByFilter.value?.toMutableList() ?: mutableListOf()
        currentList.addAll(list.items)
        liveDataFilmsByFilter.value = currentList.distinctBy{it.kinopoiskId}
    }
}