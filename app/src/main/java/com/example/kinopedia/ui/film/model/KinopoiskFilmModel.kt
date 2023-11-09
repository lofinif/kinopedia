package com.example.kinopedia.ui.film.model

import com.example.kinopedia.network.models.Countries
import com.example.kinopedia.network.models.Genre

data class KinopoiskFilmModel(
    val displayName: String,
    val displayNameOriginal: String,
    val displayDetails: String?,
    val displayFilmLength: String?,
    val displayDescription: String?,
    val posterUrl: String,
    val displayGenre: String?,
    val displayCountries: String?,
    val displayRatingKinopoisk: String?,
    val displayRatingImdb: String?
)
