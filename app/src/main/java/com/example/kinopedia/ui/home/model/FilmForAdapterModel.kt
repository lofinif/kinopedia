package com.example.kinopedia.ui.home.model

data class FilmForAdapterModel(
    val filmId: Int,
    val displayName: String,
    val displayOriginalName: String,
    val displayGenre: String,
    val displayCountry: String,
    val displayRatingKinopoisk: String,
    val displayYear: String,
    val displayRatingImdb: String,
    val posterUrl: String
)