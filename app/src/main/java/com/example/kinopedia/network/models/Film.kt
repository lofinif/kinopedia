package com.example.kinopedia.network.models

data class Film(
    val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val posterUrl: String?,
    val year: String?,
    val rating: String?,
    val posterUrlPreview: String?,
    val genres: List<Genre>?,
    val countries: List<Countries>?
) {
    val dash = "\u2014"

    val displayName: String
        get() = nameRu ?: nameEn ?: dash

    val displayYear: String
        get() = year?.toString() ?: dash

    val displayOriginalName: String
        get() = nameEn ?: nameRu ?: dash

    val displayRatingKinopoisk: String = rating ?: dash


}