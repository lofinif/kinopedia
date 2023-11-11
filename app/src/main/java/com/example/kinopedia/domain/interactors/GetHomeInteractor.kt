package com.example.kinopedia.domain.interactors

import com.example.kinopedia.domain.repository.HomeRepository
import javax.inject.Inject

class GetHomeInteractor @Inject constructor(private val homeRepository: HomeRepository) {
    val comingSoonFilms get() = homeRepository.comingSoonFilms
    val awaitFilms get() = homeRepository.awaitFilms
    val premierFilms get() = homeRepository.premierFilms

}