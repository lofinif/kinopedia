package com.example.kinopedia.network.models

data class CinemaOSM(
    val id: Long?,
    val lat: Double?,
    val lon: Double?,
    val type: String?,
    val tags: TagsOverpass?,
    val geometry: List<GeometryOverpass>?
){
    val latitude = lat ?: geometry?.get(0)?.lat
    val longitude = lon ?: geometry?.get(0)?.lon
}
