package com.example.kinopedia.network.models

data class PersonsFromSearch(
    val kinopoiskId: Int,
    val webUrl: String,
    val nameRu: String,
    val nameEn: String,
    val sex: String,
    val posterUrl: String
)