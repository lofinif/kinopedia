package com.example.kinopedia.domain.interactors

import com.example.kinopedia.domain.repository.FilmRepository
import javax.inject.Inject

class GetFilmsInteractor @Inject constructor(private val filmRepository: FilmRepository) {
    suspend fun getFilmById(kinopoiskId: Int) = filmRepository.getFilmById(kinopoiskId)
    suspend fun getSimilarFilms(kinopoiskId: Int) = filmRepository.getSimilarFilms(kinopoiskId)
    suspend fun getActors(kinopoiskId: Int) = filmRepository.getActors(kinopoiskId)
    suspend fun getExternalSources(kinopoiskId: Int) = filmRepository.getExternalSources(kinopoiskId)

}
