package com.example.kinopedia.ui.home.model

data class ThisMonthFilmModel(
    val filmId: Int,
    val displayName: String,
    val displayOriginalName: String,
    val displayGenre: String,
    val displayCountry: String,
    val displayYear: String,
    val posterUrl: String,
    val dash: String
)
