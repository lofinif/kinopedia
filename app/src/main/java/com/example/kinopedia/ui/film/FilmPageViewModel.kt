package com.example.kinopedia.ui.film

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.network.Film
import com.example.kinopedia.network.FilmApi
import com.example.kinopedia.network.Genre
import com.example.kinopedia.network.KinopoiskFilm
import com.example.kinopedia.network.interceptor
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex.Empty

class FilmPageViewModel : ViewModel() {

    private val liveDataFilm = MutableLiveData<KinopoiskFilm>()
    val data: LiveData<KinopoiskFilm> = liveDataFilm

    fun getDataKinopoiskFilm(): KinopoiskFilm {
        return liveDataFilm.value ?: KinopoiskFilm(0, null, null,
            null, null, null, "","", "", emptyList())
    }

    fun getFilmById(kinopoiskId: Int) = viewModelScope.launch {
        val list = FilmApi.retrofitService.getFilmById(kinopoiskId)
        liveDataFilm.value = list
    }
}
