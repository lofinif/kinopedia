package com.example.kinopedia.data.cinema

import com.example.kinopedia.data.BaseRepo
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.cinema.dto.CityOSM
import com.example.kinopedia.data.safeApiCall
import com.example.kinopedia.domain.repository.CinemaRepository
import com.example.kinopedia.network.models.Cinemas
import com.example.kinopedia.network.services.ApiServiceOSM
import com.example.kinopedia.network.services.ApiServiceOverpass
import javax.inject.Inject

class CinemaRepositoryImpl @Inject constructor(
    private val apiServiceOSM: ApiServiceOSM,
    private val apiServiceOverpass: ApiServiceOverpass
) : BaseRepo(), CinemaRepository {
    override suspend fun getCity(latitude: Double, longitude: Double): CallResult<CityOSM> =
        safeApiCall {
            apiServiceOSM.getCity(latitude, longitude)
        }

    override suspend fun getCinemas(city: String): CallResult<Cinemas> = safeApiCall {
        val request =
            "[out:json];area[name=\"${city}\"](around:1000.0);nwr[amenity=cinema](area);out geom;"
        apiServiceOverpass.getCinemas(request)
    }
}