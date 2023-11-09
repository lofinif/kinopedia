package com.example.kinopedia.ui.more.viewmodel

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.network.services.FilmApi
import com.example.kinopedia.network.services.LoadingStatus
import com.example.kinopedia.network.models.ThisMonthFilm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class MoreViewModel : ViewModel() {
    var pageCountTrendingAndAwait = 1
    var pageCountThisMonth = 1
    private val calendar = Calendar.getInstance()
    private val monthFormat = SimpleDateFormat("MMMM", Locale.US)
    private val year = calendar.get(Calendar.YEAR)
    private val month = monthFormat.format(calendar)
    var titleFragment = ""

    private val _coming = MutableLiveData<List<FilmForAdapter>>()
    val coming: LiveData<List<FilmForAdapter>> = _coming

    private val _status = MutableLiveData(LoadingStatus.DEFAULT)
    val status: LiveData<LoadingStatus> = _status

    private val _trending = MutableLiveData<List<FilmForAdapter>>()
    val trending: LiveData<List<FilmForAdapter>> = _trending

    private val _thisMonth = MutableLiveData<List<ThisMonthFilm>>()
    val thisMonth: LiveData<List<ThisMonthFilm>> = _thisMonth

    fun getAwaitFilms(page: Int) {
        titleFragment = "Скоро выйдет"
        if (_coming.value.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                _status.postValue(LoadingStatus.LOADING)
                try {
                    val list = FilmApi.retrofitService.getAwaitFilms(page)
                    _coming.postValue(list.filmForAdapters)
                    pageCountTrendingAndAwait = list.pagesCount
                    _status.postValue(LoadingStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(LoadingStatus.ERROR)
                }
            }
        }
    }

    fun getPopularFilms(page: Int) {
        titleFragment = "Популярно"
        if(_trending.value.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                _status.postValue(LoadingStatus.LOADING)
                try {
                    val list = FilmApi.retrofitService.getPopularFilms(page)
                    _trending.postValue(list.filmForAdapters)
                    pageCountTrendingAndAwait = list.pagesCount
                    _status.postValue(LoadingStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(LoadingStatus.ERROR)
                }
            }
        }
    }

    fun getFilmsThisMonth(page: Int) {
        titleFragment = "Премьеры"
        if(_thisMonth.value.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                _status.postValue(LoadingStatus.LOADING)
                try {
                    val list = FilmApi.retrofitService.getFilmsThisMonth(year, month, page)
                    _thisMonth.postValue(list.items)
                    Log.i("thisMonth", thisMonth.value.toString())
                    _status.postValue(LoadingStatus.DONE)
                } catch (e: Exception) {
                    _status.postValue(LoadingStatus.ERROR)
                }
            }
        }
    }

    fun loadNextTrending(page: Int) = viewModelScope.launch(Dispatchers.IO) {
        val list = FilmApi.retrofitService.getPopularFilms(page)
        val currentList = _trending.value?.toMutableList() ?: mutableListOf()
        currentList.addAll(list.filmForAdapters)
        _trending.postValue(currentList.distinctBy{it.filmId})
    }

    fun loadNextComingSoon(page:Int) = viewModelScope.launch(Dispatchers.IO) {
        val list = FilmApi.retrofitService.getFilmsThisMonth(year, month, page)
        val currentList = _coming.value?.toMutableList()?.distinctBy { it.filmId } ?: mutableListOf()
        _coming.postValue(currentList.distinctBy{it.filmId})

    }
    fun loadNextComingThisMonth(page:Int) = viewModelScope.launch(Dispatchers.IO) {
        val list = FilmApi.retrofitService.getFilmsThisMonth(year, month, page)
        val currentList = _thisMonth.value?.toMutableList()?.distinctBy { it.kinopoiskId } ?: mutableListOf()
        _thisMonth.postValue(currentList.distinctBy{it.kinopoiskId})
    }
}