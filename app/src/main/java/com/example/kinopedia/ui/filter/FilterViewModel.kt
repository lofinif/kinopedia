package com.example.kinopedia.ui.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.network.FilmApi
import com.example.kinopedia.network.Filters
import com.example.kinopedia.network.KinopoiskFilm
import com.example.kinopedia.network.LoadingStatus
import kotlinx.coroutines.launch

class FilterViewModel: ViewModel (){
    var pageCount = 1

    private val liveDataFilmsByFilter = MutableLiveData<List<KinopoiskFilm>>()
    val dataFilmsByFilter: LiveData<List<KinopoiskFilm>> = liveDataFilmsByFilter

    private val _status = MutableLiveData<LoadingStatus>()
    val status: LiveData<LoadingStatus> = _status

    private val _statusFilter = MutableLiveData<LoadingStatus>()
    val statusFilter: LiveData<LoadingStatus> = _statusFilter

    private val liveCountriesAndGenres = MutableLiveData<Filters>()
    val dataCountriesAndGenres: LiveData<Filters> = liveCountriesAndGenres

    fun getCountriesAndGenres() = viewModelScope.launch{
        _statusFilter.value = LoadingStatus.LOADING
        try {
        val list = FilmApi.retrofitService.getFilters()
         liveCountriesAndGenres.value = list
            _statusFilter.value = LoadingStatus.DONE
        } catch (e: Exception) {
            _statusFilter.value = LoadingStatus.ERROR
        }
    }

    fun getFilmsByFiler(
        countries: Array<Int>?, genres: Array<Int>?, order: String, type: String,
        keyword: String?, ratingFrom: Int, ratingTo: Int, yearFrom: Int,
        yearTo: Int, page: Int) = viewModelScope.launch {
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

    fun clearList(){
        liveDataFilmsByFilter.value = emptyList()
    }
}