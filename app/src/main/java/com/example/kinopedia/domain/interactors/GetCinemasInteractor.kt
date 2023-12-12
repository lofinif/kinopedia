package com.example.kinopedia.domain.interactors

import com.example.kinopedia.domain.repository.CinemaRepository
import javax.inject.Inject

class GetCinemasInteractor @Inject constructor(private val cinemaRepository: CinemaRepository) {
    suspend fun getCity(latitude: Double, longitude: Double) = cinemaRepository.getCity(latitude, longitude)

    suspend fun getCinemas(city: String) = cinemaRepository.getCinemas(city)

}