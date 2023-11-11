package com.example.kinopedia.data.home.dto

import com.example.kinopedia.data.film.dto.FilmForAdapter

data class TopFilms(
    val pagesCount: Int,
    val films: List<FilmForAdapter>
)
