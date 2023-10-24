package com.example.kinopedia.network.models

data class Filters(
    val genres: List<FilterGenre>,
    val countries: List<FilterCountry>
)
