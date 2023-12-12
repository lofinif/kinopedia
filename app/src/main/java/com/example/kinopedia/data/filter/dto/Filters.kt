package com.example.kinopedia.data.filter.dto

import com.example.kinopedia.network.models.FilterCountry
import com.example.kinopedia.network.models.FilterGenre

data class Filters(
    val genres: List<FilterGenre>,
    val countries: List<FilterCountry>
)
