package com.example.kinopedia.network

data class Actor(
    val staffId: Int,
    val nameRu: String,
    val nameEn: String,
    val description: String,
    val posterUrl: String,
    val professionText: String,
    val professionKey: String
)
