package com.example.kinopedia.data.film.dto

import com.example.kinopedia.network.models.Countries
import com.example.kinopedia.network.models.Genre

data class KinopoiskFilm(
    val kinopoiskId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val nameOriginal: String?,
    val year: Int?,
    val filmLength: Int?,
    val description: String?,
    val ratingAwait: Int?,
    val posterUrl: String,
    val posterUrlPreview: String?,
    val genres: List<Genre>,
    val countries: List<Countries>,
    val ratingKinopoisk: Double?,
    val ratingImdb: Double?
    ) {
    private val dash = "\u2014"
    val displayName: String
        get() = nameRu ?: nameOriginal ?: nameEn ?: dash
    val displayOriginalName: String
        get() = nameOriginal ?: nameEn ?: nameRu ?: dash

    val displayYear: String
        get() = year?.toString() ?: dash

    val displayFilmLength: String
        get() = filmLength?.toString() ?: dash

    val displayDescription: String
        get() = description ?: dash

    val displayRatingKinopoisk: String =
        ratingKinopoisk?.toString()
            ?: if (ratingAwait != null) {
                "$ratingAwait%"
            } else {
                dash
            }
    val displayRatingImdb: String = ratingImdb?.toString() ?: dash
    val displayGenres = if (genres.isNotEmpty()) genres[0].genre.toString() else dash
    val displayCountry = if (countries.isNotEmpty()) countries[0].country else dash
}


