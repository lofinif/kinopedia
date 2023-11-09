package com.example.kinopedia.network.models

import com.example.kinopedia.data.film.ExternalSource

data class ExternalSources(
    val total: Int,
    val items: List<ExternalSource>
)
