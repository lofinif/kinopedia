package com.example.kinopedia.network.models

import com.example.kinopedia.data.film.dto.FilmForAdapter

data class TopFilms(
    val pagesCount: Int,
    val filmForAdapters: List<FilmForAdapter>
)
