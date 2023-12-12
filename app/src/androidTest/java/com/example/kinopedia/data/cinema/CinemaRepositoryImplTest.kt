package com.example.kinopedia.data.cinema

import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.cinema.dto.CityOSM
import com.example.kinopedia.domain.repository.CinemaRepository
import com.example.kinopedia.network.models.Cinemas
import com.example.sharedtest.cinemasMock
import com.example.sharedtest.cityOSMMock
import javax.inject.Inject

class CinemaRepositoryImplTest @Inject constructor() : CinemaRepository {
    override suspend fun getCity(latitude: Double, longitude: Double): CallResult<CityOSM> {
        return CallResult.Success(cityOSMMock)
    }

    override suspend fun getCinemas(city: String): CallResult<Cinemas> {
        return CallResult.Success(cinemasMock)
    }

}