package com.example.kinopedia.network

data class KinopoiskFilm(
    val kinopoiskId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val nameOriginal: String?,
    val year: Int?,
    val filmLength: Int?,
    val description: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val genres: List<Genre>
    ) {
    val displayName: String
        get() = nameRu ?: nameOriginal ?: nameEn ?: "-"

    val displayYear: String
        get() = year?.toString() ?: "-"

    val displayFilmLength: String
        get() = filmLength?.toString() ?: "-"

    val displayDescription: String
        get() = description ?: "-"
}