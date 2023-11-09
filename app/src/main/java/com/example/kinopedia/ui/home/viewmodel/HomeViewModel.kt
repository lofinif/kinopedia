package com.example.kinopedia.ui.home.viewmodel

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
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

class HomeViewModel : ViewModel() {

    private val _statusAwait = MutableLiveData(LoadingStatus.DEFAULT)
    val statusAwait: LiveData<LoadingStatus> = _statusAwait

    private val _statusPopular = MutableLiveData(LoadingStatus.DEFAULT)
    val statusPopular: LiveData<LoadingStatus> = _statusPopular

    private val _statusMonth = MutableLiveData(LoadingStatus.DEFAULT)
    val statusMonth: LiveData<LoadingStatus> = _statusMonth

    private val _coming = MutableLiveData<List<FilmForAdapter>>()
    val coming: LiveData<List<FilmForAdapter>> = _coming

    private val _trending = MutableLiveData<List<FilmForAdapter>>()
    val trending: LiveData<List<FilmForAdapter>> = _trending

    private val _thisMonth = MutableLiveData<List<ThisMonthFilm>>()
    val thisMonth: LiveData<List<ThisMonthFilm>> = _thisMonth
    fun getAwaitFilms() {
        if (_coming.value.isNullOrEmpty()) {
        viewModelScope.launch(Dispatchers.IO) {
                _statusAwait.postValue(LoadingStatus.LOADING)
                try {
                    val list = FilmApi.retrofitService.getAwaitFilms(1)
                    _coming.postValue(list.filmForAdapters)
                    _statusAwait.postValue(LoadingStatus.DONE)
                } catch (e: Exception) {
                    _coming.postValue(emptyList())
                    _statusAwait.postValue(LoadingStatus.ERROR)
                }
            }
        }
    }
        fun getPopularFilms() {
            if (_trending.value.isNullOrEmpty()) {
                viewModelScope.launch(Dispatchers.IO) {
                    _statusPopular.postValue(LoadingStatus.LOADING)
                    try {
                        val list = FilmApi.retrofitService.getPopularFilms(1)
                        _trending.postValue(list.filmForAdapters)
                        _statusPopular.postValue(LoadingStatus.DONE)
                    } catch (e: Exception) {
                        _trending.postValue(emptyList())
                        _statusPopular.postValue(LoadingStatus.ERROR)
                    }
                }
            }
        }
            fun getFilmsThisMonth() {
                if (_thisMonth.value.isNullOrEmpty()) {
                     viewModelScope.launch(Dispatchers.IO){
                        _statusMonth.postValue(LoadingStatus.LOADING)
                        try {
                            val calendar = Calendar.getInstance()
                            val monthFormat = SimpleDateFormat("MMMM", Locale.US)
                            val year = calendar.get(Calendar.YEAR)
                            val month = monthFormat.format(calendar)
                            val list = FilmApi.retrofitService.getFilmsThisMonth(year, month, 1)
                            _thisMonth.postValue(list.items)
                            _statusMonth.postValue(LoadingStatus.DONE)
                        } catch (e: Exception) {
                            _thisMonth.postValue(emptyList())
                            _statusMonth.postValue(LoadingStatus.ERROR)
                        }
                    }
                }
            }
        }

