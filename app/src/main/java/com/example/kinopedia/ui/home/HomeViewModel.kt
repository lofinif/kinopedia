package com.example.kinopedia.ui.home

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.network.Film
import com.example.kinopedia.network.FilmApi
import com.example.kinopedia.network.LoadingStatus
import com.example.kinopedia.network.ThisMonthFilm
import kotlinx.coroutines.launch
import java.util.Locale

class HomeViewModel : ViewModel() {

    private val _statusAwait = MutableLiveData<LoadingStatus>()
    val statusAwait: LiveData<LoadingStatus> = _statusAwait

    private val _statusPopular = MutableLiveData<LoadingStatus>()
    val statusPopular: LiveData<LoadingStatus> = _statusPopular

    private val _statusMonth = MutableLiveData<LoadingStatus>()
    val statusMonth: LiveData<LoadingStatus> = _statusMonth

    private val _coming = MutableLiveData<List<Film>>()
    val coming: LiveData<List<Film>> = _coming

    private val _trending = MutableLiveData<List<Film>>()
    val trending: LiveData<List<Film>> = _trending

    private val _thisMonth = MutableLiveData<List<ThisMonthFilm>>()
    val thisMonth: LiveData<List<ThisMonthFilm>> = _thisMonth
    fun getAwaitFilms() = viewModelScope.launch {
        if (_coming.value.isNullOrEmpty()) {
            _statusAwait.value = LoadingStatus.LOADING
            try {
                val list = FilmApi.retrofitService.getAwaitFilms(1)
                _coming.value = list.films
                _statusAwait.value = LoadingStatus.DONE
            } catch (e: Exception) {
                _coming.value = emptyList()
                _statusAwait.value = LoadingStatus.ERROR
            }
        }
    }
        fun getPopularFilms() = viewModelScope.launch {
            if (_trending.value.isNullOrEmpty()) {
                _statusPopular.value = LoadingStatus.LOADING
                try {
                    val list = FilmApi.retrofitService.getPopularFilms(1)
                    _trending.value = list.films
                    _statusPopular.value = LoadingStatus.DONE
                } catch (e: Exception) {
                    _trending.value = emptyList()
                    _statusPopular.value = LoadingStatus.ERROR
                }
            }
        }
            fun getFilmsThisMonth() = viewModelScope.launch {
                if (_thisMonth.value.isNullOrEmpty()) {
                    _statusMonth.value = LoadingStatus.LOADING
                    try {
                        val calendar = Calendar.getInstance()
                        val monthFormat = SimpleDateFormat("MMMM", Locale.US)
                        val year = calendar.get(Calendar.YEAR)
                        val month = monthFormat.format(calendar)
                        val list = FilmApi.retrofitService.getFilmsThisMonth(year, month, 1)
                        _thisMonth.value = list.items
                        _statusMonth.value = LoadingStatus.DONE
                    } catch (e: Exception) {
                        _thisMonth.value = emptyList()
                        _statusMonth.value = LoadingStatus.ERROR
                    }
                }
            }
        }

