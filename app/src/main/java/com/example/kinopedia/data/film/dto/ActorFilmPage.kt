package com.example.kinopedia.data.film.dto

data class ActorFilmPage(
    val staffId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val description: String?,
    val posterUrl: String,
    val professionText: String?,
    val professionKey: String?
)