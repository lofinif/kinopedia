package com.example.kinopedia.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.network.models.Film
import com.example.kinopedia.network.services.FilmApi
import com.example.kinopedia.network.models.KinopoiskFilm
import com.example.kinopedia.network.services.LoadingStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    var pageCount = 1

    private val _filmsByFilter = MutableLiveData<List<KinopoiskFilm>>()
    val filmsByFilter: LiveData<List<KinopoiskFilm>> = _filmsByFilter

    private val _statusTopFilm = MutableLiveData(LoadingStatus.DEFAULT)
    val statusTopFilm: LiveData<LoadingStatus> = _statusTopFilm

    private val _statusSearchKeyWord = MutableLiveData(LoadingStatus.DONE)
    val statusSearchKeyWord: LiveData<LoadingStatus> = _statusSearchKeyWord

    private val _topFilms = MutableLiveData<List<Film>>()
    val topFilms: LiveData<List<Film>> = _topFilms

    fun getFilmsByKeyWord(countries: Array<Int>?, genres: Array<Int>?, order: String, type: String,
                          keyword: String, ratingFrom: Int, ratingTo: Int, yearFrom: Int,
                          yearTo: Int, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            _statusSearchKeyWord.postValue(LoadingStatus.LOADING)
        try {
            val list = FilmApi.retrofitService.getFilmByFilters(
                countries, genres, order, type, keyword,
                ratingFrom, ratingTo, yearFrom, yearTo, page)
            _filmsByFilter.postValue(list.items)
            pageCount = list.totalPages
            _statusSearchKeyWord.postValue(LoadingStatus.DONE)
           } catch (e: Exception){
            _statusSearchKeyWord.postValue(LoadingStatus.ERROR)
       }
    }

    fun getTopFilms() {
        if (_topFilms.value.isNullOrEmpty()) {
        viewModelScope.launch(Dispatchers.IO) {
                _statusTopFilm.postValue(LoadingStatus.LOADING)
                try {
                    val list = FilmApi.retrofitService.getTopFilms()
                    _topFilms.postValue(list.films)
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
        val currentList = _filmsByFilter.value?.toMutableList() ?: mutableListOf()
        currentList.addAll(list.items)
        _filmsByFilter.postValue(currentList.distinctBy{it.kinopoiskId})
    }
}