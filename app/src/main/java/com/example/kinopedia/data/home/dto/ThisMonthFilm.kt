package com.example.kinopedia.data.home.dto

import com.example.kinopedia.network.models.Countries
import com.example.kinopedia.network.models.Genre

data class ThisMonthFilm(
    val kinopoiskId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val year: Int?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val genres: List<Genre>,
    val countries: List<Countries>,
    val premiereRu: String
)