package com.example.kinopedia.data.cinema.dto

import com.example.kinopedia.network.models.AddressOSM

data class CityOSM(
    val place_id: Int,
    val lat: Double,
    val lon: Double,
    val display_name: String,
    val address: AddressOSM
)
