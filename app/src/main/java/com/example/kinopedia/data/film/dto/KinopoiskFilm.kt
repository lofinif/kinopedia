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
    )


