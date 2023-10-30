package com.example.kinopedia.ui.genre.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.network.services.FilmApi
import com.example.kinopedia.network.models.KinopoiskFilm
import com.example.kinopedia.network.services.LoadingStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenreViewModel: ViewModel() {
    var pageCount = 1

    private val _filmsByFilter = MutableLiveData<List<KinopoiskFilm>>()
    val filmsByFilter: LiveData<List<KinopoiskFilm>> = _filmsByFilter

    private val _status = MutableLiveData(LoadingStatus.DEFAULT)
    val status: LiveData<LoadingStatus> = _status

    fun getFilmsByFiler(
        countries: Array<Int>?, genres: Array<Int>, order: String, type: String,
        keyword: String, ratingFrom: Int, ratingTo: Int, yearFrom: Int,
        yearTo: Int, page: Int
    ) {
        if (_filmsByFilter.value.isNullOrEmpty()) {
        viewModelScope.launch(Dispatchers.IO) {
                _status.postValue(LoadingStatus.LOADING)
                try {
                    val list = FilmApi.retrofitService.getFilmByFilters(
                        countries, genres, order, type, keyword,
                        ratingFrom, ratingTo, yearFrom, yearTo, page
                    )
                    pageCount = list.totalPages
                    _filmsByFilter.postValue(list.items)
                    _status.postValue(LoadingStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(LoadingStatus.ERROR)
                }
            }
        }
    }
    fun loadNextItems( countries: Array<Int>?, genres: Array<Int>, order: String, type: String,
                       keyword: String, ratingFrom: Int, ratingTo: Int, yearFrom: Int,
                       yearTo: Int, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        val list = FilmApi.retrofitService.getFilmByFilters(
            countries, genres, order, type, keyword,
            ratingFrom, ratingTo, yearFrom, yearTo, page
        )
        val currentList = _filmsByFilter.value?.toMutableList() ?: mutableListOf()
        currentList.addAll(list.items)
        _filmsByFilter.postValue(currentList.distinctBy{it.kinopoiskId})
    }
}