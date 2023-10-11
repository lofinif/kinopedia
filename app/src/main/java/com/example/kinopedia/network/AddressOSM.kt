package com.example.kinopedia.network

data class AddressOSM(
    val house_number: String?,
    val road: String?,
    val city: String?,
    val village: String?,
    val county: String?,
    val state: String?,
    val region: String?,
    val postcode: String?,
    val country: String?,
    val country_code: String?
    ){
    val displayCity = city ?: village
}

