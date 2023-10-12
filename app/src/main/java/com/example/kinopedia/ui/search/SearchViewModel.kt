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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor
import java.util.logging.Level

class SearchViewModel: ViewModel() {
    var pageCount = 1

    private val liveDataFilmsByFilter = MutableLiveData<List<KinopoiskFilm>>()
    val dataFilmsByFilter: LiveData<List<KinopoiskFilm>> = liveDataFilmsByFilter

    private val _statusTopFilm = MutableLiveData(LoadingStatus.DEFAULT)
    val statusTopFilm: LiveData<LoadingStatus> = _statusTopFilm

    private val _statusSearchKeyWord = MutableLiveData(LoadingStatus.DONE)
    val statusSearchKeyWord: LiveData<LoadingStatus> = _statusSearchKeyWord

    private val liveDataTopFilms = MutableLiveData<List<Film>>()
    val dataTopFilms: LiveData<List<Film>> = liveDataTopFilms

    init {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    }
    fun getFilmsByKeyWord(countries: Array<Int>?, genres: Array<Int>?, order: String, type: String,
                          keyword: String, ratingFrom: Int, ratingTo: Int, yearFrom: Int,
                          yearTo: Int, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            _statusSearchKeyWord.postValue(LoadingStatus.LOADING)
        try {
            val list = FilmApi.retrofitService.getFilmByFilters(
                countries, genres, order, type, keyword,
                ratingFrom, ratingTo, yearFrom, yearTo, page)
            liveDataFilmsByFilter.postValue(list.items)
            pageCount = list.totalPages
            _statusSearchKeyWord.postValue(LoadingStatus.DONE)
           } catch (e: Exception){
            _statusSearchKeyWord.postValue(LoadingStatus.ERROR)
       }
    }

    fun getTopFilms() {
        if (liveDataTopFilms.value.isNullOrEmpty()) {
        viewModelScope.launch(Dispatchers.IO) {
                _statusTopFilm.postValue(LoadingStatus.LOADING)
                try {
                    val list = FilmApi.retrofitService.getTopFilms()
                    liveDataTopFilms.postValue(list.films)
                    pageCount = list.pagesCount
                    _statusTopFilm.postValue(LoadingStatus.DONE)
                } catch (e: Exception) {
                    _statusTopFilm.postValue(LoadingStatus.ERROR)
                }
            }
        }
    }
    fun loadNextItems(countries: Array<Int>?, genres: Array<Int>?, order: String, type: String,
                      keyword: String, ratingFrom: Int, ratingTo: Int, yearFrom: Int,
                      yearTo: Int, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        val list = FilmApi.retrofitService.getFilmByFilters(
            countries, genres, order, type, keyword,
            ratingFrom, ratingTo, yearFrom, yearTo, page)
        val currentList = liveDataFilmsByFilter.value?.toMutableList() ?: mutableListOf()
        currentList.addAll(list.items)
        liveDataFilmsByFilter.postValue(currentList.distinctBy{it.kinopoiskId})
    }
}