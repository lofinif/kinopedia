package com.example.kinopedia.domain.interactors

import com.example.kinopedia.domain.repository.HomeRepository
import javax.inject.Inject

class GetHomeInteractor @Inject constructor(private val homeRepository: HomeRepository) {

    suspend fun getComingSoonFilms() = homeRepository.getComingSoonFilms()
    suspend fun getAwaitFilms() = homeRepository.getAwaitFilms()
    suspend fun getPremierFilms() = homeRepository.getPremierFilms()

}