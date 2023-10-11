package com.example.kinopedia.network

data class Filters(
    val genres: List<FilterGenre>,
    val countries: List<FilterCountry>
)
