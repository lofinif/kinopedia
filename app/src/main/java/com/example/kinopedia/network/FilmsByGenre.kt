package com.example.kinopedia.network

data class FilmsByGenre(
    val total : Int,
    val totalPages: Int,
    val items: List<KinopoiskFilm>
)
