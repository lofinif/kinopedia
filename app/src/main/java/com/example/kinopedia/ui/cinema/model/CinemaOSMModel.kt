package com.example.kinopedia.ui.cinema.model

import com.example.kinopedia.network.models.TagsOverpass

data class CinemaOSMModel(
    val id: Long?,
    val latitude: Double?,
    val longitude: Double?,
    val tags: TagsOverpass?,
)