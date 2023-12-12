package com.example.kinopedia.ui.favourite.model

data class FavouriteEntityModel (
    val filmId: Int,
    val displayName: String,
    val displayNameOriginal: String,
    val displayYear: String,
    val displayGenre: String,
    val displayCountry: String,
    val displayKinopoiskRating: String,
    val displayImdbRating: String,
    val posterUrl: String
)
