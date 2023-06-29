package com.example.kinopedia.network

import com.squareup.moshi.Json
import org.jetbrains.annotations.Nullable

data class Film(
    val filmId: Int,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val genres: List<Genre>
)

