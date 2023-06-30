package com.example.kinopedia.network

data class Film(
    val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val genres: List<Genre>
) {
    val displayName: String
        get() = nameRu ?: nameEn ?: "Unknown"
}