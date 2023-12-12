package com.example.kinopedia.ui.film.model

data class KinopoiskFilmModel(
    val filmId: Int,
    val displayName: String,
    val displayNameOriginal: String,
    val displayDetails: String,
    val displayFilmLength: String,
    val displayDescription: String,
    val posterUrl: String,
    val displayGenre: String,
    val displayCountries: String,
    val displayRatingKinopoisk: String,
    val displayRatingImdb: String,
    val displayYear: String,
)
