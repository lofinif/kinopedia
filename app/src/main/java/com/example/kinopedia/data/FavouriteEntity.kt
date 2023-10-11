package com.example.kinopedia.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "favourite_database")

data class FavouriteEntity(
    @PrimaryKey
    val filmId: Int,
    val posterUrl: String,
    val nameRu: String,
    val year: String,
    val country: String,
    val genre: String,
    val nameOriginal: String,
    val ratingKinopoisk: String,
    val ratingImdb: String,
    val description: String,
    val dateAdded: String
)