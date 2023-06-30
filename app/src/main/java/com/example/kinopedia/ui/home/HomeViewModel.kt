package com.example.kinopedia.ui.home

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.network.Film
import com.example.kinopedia.network.FilmApi
import com.example.kinopedia.network.KinopoiskFilm
import com.example.kinopedia.network.ThisMonthFilm
import com.example.kinopedia.network.interceptor
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor
import java.util.Locale

class HomeViewModel : ViewModel() {

    private val _imageComing = MutableLiveData<List<Film>>()
    val imageComing: LiveData<List<Film>> = _imageComing

    private val _imageTrending = MutableLiveData<List<Film>>()
    val imageTrending: LiveData<List<Film>> = _imageTrending

    private val _imageThisMonth = MutableLiveData<List<ThisMonthFilm>>()
    val imageThisMonth: LiveData<List<ThisMonthFilm>> = _imageThisMonth

    init {
        getAwaitFilms()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        getPopularFilms()
        getFilmsThisMonth()

    }
    private fun getAwaitFilms() = viewModelScope.launch {
       val list = FilmApi.retrofitService.getAwaitFilms()
       _imageComing.value = list.films
      }
    private fun getPopularFilms() = viewModelScope.launch {
        val list = FilmApi.retrofitService.getPopularFilms()
        _imageTrending.value = list.films
      }
    private fun getFilmsThisMonth() = viewModelScope.launch {
        val calendar = Calendar.getInstance()
        val monthFormat = SimpleDateFormat("MMMM", Locale.US)
        val year = calendar.get(Calendar.YEAR)
        val month = monthFormat.format(calendar)
        val list = FilmApi.retrofitService.getFilmsThisMonth(year, month)
        _imageThisMonth.value = list.items
      }

    }

