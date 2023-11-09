package com.example.kinopedia.data.film

import com.example.kinopedia.data.BaseRepo
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.safeApiCall
import com.example.kinopedia.domain.repository.FilmRepository
import com.example.kinopedia.data.film.dto.ActorFilmPage
import com.example.kinopedia.network.models.ExternalSources
import com.example.kinopedia.data.film.dto.KinopoiskFilm
import com.example.kinopedia.network.models.KinopoiskSimilarFilms
import com.example.kinopedia.network.services.ApiService
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(private val api: ApiService): BaseRepo(), FilmRepository{
    override suspend fun getSimilarFilms(kinopoiskId: Int): CallResult<KinopoiskSimilarFilms> = safeApiCall {
        api.getSimilarFilms(kinopoiskId)
    }
    override suspend fun getFilmById(kinopoiskId: Int): CallResult<KinopoiskFilm> = safeApiCall {
        api.getFilmById(kinopoiskId)
    }

    override suspend fun getActors(kinopoiskId: Int): CallResult<List<ActorFilmPage>> = safeApiCall {
        api.getActorsAndStaff(kinopoiskId)
    }

    override suspend fun getExternalSources(kinopoiskId: Int): CallResult<ExternalSources> = safeApiCall {
        api.getExternalSources(kinopoiskId)
    }
}