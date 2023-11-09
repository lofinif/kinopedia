package com.example.kinopedia.domain.repository

import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.film.dto.ActorFilmPage
import com.example.kinopedia.network.models.ExternalSources
import com.example.kinopedia.data.film.dto.KinopoiskFilm
import com.example.kinopedia.network.models.KinopoiskSimilarFilms

interface FilmRepository {
    suspend fun getSimilarFilms(kinopoiskId: Int): CallResult<KinopoiskSimilarFilms>
    suspend fun getFilmById(kinopoiskId: Int): CallResult<KinopoiskFilm>
    suspend fun getActors(kinopoiskId: Int): CallResult<List<ActorFilmPage>>
    suspend fun getExternalSources(kinopoiskId: Int): CallResult<ExternalSources>
}