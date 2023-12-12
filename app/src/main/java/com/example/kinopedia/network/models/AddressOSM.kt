package com.example.kinopedia.network.models

data class AddressOSM(
    val houseNumber: String?,
    val road: String?,
    val city: String?,
    val village: String?,
    val county: String?,
    val state: String?,
    val region: String?,
    val postcode: String?,
    val country: String?,
    val countryCode: String?
    ){
    val displayCity = city ?: village
}

