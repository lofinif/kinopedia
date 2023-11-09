package com.example.kinopedia.data.film.dto

data class ActorFilmPage(
    val staffId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val description: String?,
    val posterUrl: String,
    val professionText: String?,
    val professionKey: String?
) {

    private val dash = "\u2014"

    val displayName = if (!nameRu.isNullOrBlank()) {
        nameRu
    } else {
        nameEn
    }
    val displayDescription = if (!description.isNullOrBlank()) description
        else  professionText?.replace("ы$".toRegex(),"")?.lowercase() ?: dash
}