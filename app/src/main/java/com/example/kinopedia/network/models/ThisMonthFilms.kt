package com.example.kinopedia.network.models

import com.example.kinopedia.data.home.dto.ThisMonthFilm

data class ThisMonthFilms(
    val total: Int,
    val items: List<ThisMonthFilm>
)
