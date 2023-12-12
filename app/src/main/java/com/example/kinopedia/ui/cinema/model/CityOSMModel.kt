package com.example.kinopedia.ui.cinema.model

import com.example.kinopedia.network.models.AddressOSM

data class CityOSMModel(
    val placeId: Int,
    val lat: Double,
    val lon: Double,
    val displayName: String,
    val address: AddressOSM
)
