package com.example.kinopedia.network

data class CityOSM(
    val place_id: Int,
    val lat: Double,
    val lon: Double,
    val display_name: String,
    val address: AddressOSM
)
