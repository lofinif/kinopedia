package com.example.kinopedia.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

data class TopFilms(
    val pagesCount: Int,
    val films: List<Film>
)
