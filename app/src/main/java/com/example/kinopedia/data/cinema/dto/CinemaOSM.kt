package com.example.kinopedia.data.cinema.dto

import com.example.kinopedia.network.models.GeometryOverpass
import com.example.kinopedia.network.models.TagsOverpass

data class CinemaOSM(
    val id: Long?,
    val lat: Double?,
    val lon: Double?,
    val type: String?,
    val tags: TagsOverpass?,
    val geometry: List<GeometryOverpass>?
)
