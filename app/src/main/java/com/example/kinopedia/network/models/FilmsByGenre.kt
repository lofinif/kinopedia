package com.example.kinopedia.network.models

import com.example.kinopedia.data.film.dto.KinopoiskFilm

data class FilmsByGenre(
    val total : Int,
    val totalPages: Int,
    val items: List<KinopoiskFilm>
)
