package com.example.kinopedia.network.models

import com.example.kinopedia.data.film.dto.FilmForAdapter

data class KinopoiskSimilarFilms(
    val items: List<FilmForAdapter>?
)
