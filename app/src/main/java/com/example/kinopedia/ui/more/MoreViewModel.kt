package com.example.kinopedia.ui.more

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.network.Film
import com.example.kinopedia.network.FilmApi
import com.example.kinopedia.network.LoadingStatus
import com.example.kinopedia.network.ThisMonthFilm
import com.example.kinopedia.network.interceptor
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor
import java.util.Locale

class MoreViewModel : ViewModel() {
    var pageCountTrendingAndAwait = 1
    var pageCountThisMonth = 1
    private val calendar = Calendar.getInstance()
    private val monthFormat = SimpleDateFormat("MMMM", Locale.US)
    private val year = calendar.get(Calendar.YEAR)
    private val month = monthFormat.format(calendar)
    var titleFragment = ""

    private val _coming = MutableLiveData<List<Film>>()
    val coming: LiveData<List<Film>> = _coming

    private val _status = MutableLiveData<LoadingStatus>()
    val status: LiveData<LoadingStatus> = _status

    private val _trending = MutableLiveData<List<Film>>()
    val trending: LiveData<List<Film>> = _trending

    private val _thisMonth = MutableLiveData<List<ThisMonthFilm>>()
    val thisMonth: LiveData<List<ThisMonthFilm>> = _thisMonth



    init {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    fun getAwaitFilms(page: Int) = viewModelScope.launch {
        titleFragment = "Скоро выйдет"
        _status.value = LoadingStatus.LOADING
        try {
            val list = FilmApi.retrofitService.getAwaitFilms(page)
            _coming.value = list.films
            pageCountTrendingAndAwait = list.pagesCount
            _status.value = LoadingStatus.DONE
        } catch (e: Exception) {
            _status.value = LoadingStatus.ERROR
        }
    }

    fun getPopularFilms(page: Int) = viewModelScope.launch {
        titleFragment = "Популярно"
        _status.value = LoadingStatus.LOADING
        try {
            val list = FilmApi.retrofitService.getPopularFilms(page)
            _trending.value = list.films
            pageCountTrendingAndAwait = list.pagesCount
            _status.value = LoadingStatus.DONE
        } catch (e: Exception) {
            _status.value = LoadingStatus.ERROR
        }
    }

    fun getFilmsThisMonth(page: Int) = viewModelScope.launch {
        titleFragment = "Премьеры"
        _status.value = LoadingStatus.LOADING
        try {
            val list = FilmApi.retrofitService.getFilmsThisMonth(year, month, page)
            _thisMonth.value = list.items
            Log.i("thisMonth", thisMonth.value.toString())
            _status.value = LoadingStatus.DONE
        } catch (e: Exception) {
            _status.value = LoadingStatus.ERROR
        }
    }

    fun loadNextTrending(page: Int) = viewModelScope.launch {
        val list = FilmApi.retrofitService.getPopularFilms(page)
        val currentList = _trending.value?.toMutableList() ?: mutableListOf()
        currentList.addAll(list.films)
        _trending.value = currentList.distinctBy{it.filmId}
    }

    fun loadNextComingSoon(page:Int) = viewModelScope.launch {
        val list = FilmApi.retrofitService.getFilmsThisMonth(year, month, page)
        val currentList = _coming.value?.toMutableList()?.distinctBy { it.filmId } ?: mutableListOf()
        _coming.value = currentList.distinctBy{it.filmId}

    }
    fun loadNextComingThisMonth(page:Int) = viewModelScope.launch {
        val list = FilmApi.retrofitService.getFilmsThisMonth(year, month, page)
        val currentList = _thisMonth.value?.toMutableList()?.distinctBy { it.kinopoiskId } ?: mutableListOf()
        _thisMonth.value = currentList.distinctBy{it.kinopoiskId}
    }
}