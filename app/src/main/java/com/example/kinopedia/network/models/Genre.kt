package com.example.kinopedia.network.models

data class Genre(
    val genre: String?
) {
    private val dash = "\u2014"
    val displayGenre: String
        get() = genre ?: dash
}