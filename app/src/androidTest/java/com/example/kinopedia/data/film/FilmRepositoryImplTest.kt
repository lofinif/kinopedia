package com.example.kinopedia.data.film

import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.film.dto.ActorFilmPage
import com.example.kinopedia.data.film.dto.KinopoiskFilm
import com.example.kinopedia.domain.repository.FilmRepository
import com.example.kinopedia.network.models.ExternalSources
import com.example.kinopedia.network.models.KinopoiskSimilarFilms
import com.example.sharedtest.actorFilmPageListRepoMock
import com.example.sharedtest.externalSourcesMock
import com.example.sharedtest.kinopoiskFilmMock
import com.example.sharedtest.kinopoiskSimilarFilmsMock
import javax.inject.Inject

class FilmRepositoryImplTest @Inject constructor() : FilmRepository {
    override suspend fun getSimilarFilms(kinopoiskId: Int): CallResult<KinopoiskSimilarFilms> {
        return CallResult.Success(kinopoiskSimilarFilmsMock)
    }

    override suspend fun getFilmById(kinopoiskId: Int): CallResult<KinopoiskFilm> {
        return CallResult.Success(kinopoiskFilmMock)
    }

    override suspend fun getActors(kinopoiskId: Int): CallResult<List<ActorFilmPage>> {
        return CallResult.Success(actorFilmPageListRepoMock)
    }

    override suspend fun getExternalSources(kinopoiskId: Int): CallResult<ExternalSources> {
        return CallResult.Success(externalSourcesMock)
    }
}