package com.example.kinopedia.network.models

data class ThisMonthFilm(
    val kinopoiskId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val year: Int?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val genres: List<Genre>?,
    val countries: List<Countries>?,
    val premiereRu: String
) {
    val dash = "\u2014"

    val displayName: String =
        if (!nameRu.isNullOrEmpty()) {
            nameRu
        } else {
            if (!nameEn.isNullOrEmpty()) {
                nameEn
            } else {
                dash
            }
        }
    val displayYear: String
        get() = year?.toString() ?: dash
    val displayOriginalName =
        if (!nameEn.isNullOrEmpty()) {
            nameEn
        } else {
            if (!nameRu.isNullOrEmpty()) {
                nameRu
            } else {
                dash
            }
        }
            val displayRating: String = dash


}