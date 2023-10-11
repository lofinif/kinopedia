package com.example.kinopedia.network

import com.squareup.moshi.Json

data class TagsOverpass(
    val name: String?,
    val phone: String?,
    val website: String?,
    @Json(name = "addr:street") val street: String?,
    @Json(name = "addr:housenumber") val housenumber: String?,
    @Json(name = "addr:city") val city: String?
    ){
    val address: String = "$street, $housenumber"
}
