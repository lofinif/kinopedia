package com.example.kinopedia.data.film.dto

import com.example.kinopedia.network.models.Countries
import com.example.kinopedia.network.models.Genre

data class FilmForAdapter(
    val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val posterUrl: String,
    val year: String?,
    val rating: String?,
    val posterUrlPreview: String?,
    val genres: List<Genre>?,
    val countries: List<Countries>?
)