package com.example.kinopedia.network.models

data class ThisMonthFilms(
    val total: Int,
    val items: List<ThisMonthFilm>
)
