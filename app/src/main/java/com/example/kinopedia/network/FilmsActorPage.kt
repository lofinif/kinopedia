package com.example.kinopedia.network

data class FilmsActorPage(
    val filmId: Int?,
    val nameRu: String?,
    val nameEn: String?,
    val rating: String?,
    val general: Boolean?,
    val description: String?,
    val professionKey: String?
)
