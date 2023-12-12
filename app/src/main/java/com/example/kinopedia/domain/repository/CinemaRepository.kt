package com.example.kinopedia.domain.repository

import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.cinema.dto.CityOSM
import com.example.kinopedia.network.models.Cinemas

interface CinemaRepository {
    suspend fun getCity(latitude: Double, longitude: Double): CallResult<CityOSM>
    suspend fun getCinemas(city: String): CallResult<Cinemas>
}