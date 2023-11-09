package com.example.kinopedia.ui.film.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.entities.FavouriteEntity
import com.example.kinopedia.data.film.ExternalSource
import com.example.kinopedia.data.film.dto.ActorFilmPage
import com.example.kinopedia.data.film.dto.KinopoiskFilm
import com.example.kinopedia.data.repositories.FavouriteRepository
import com.example.kinopedia.domain.interactors.GetFilmsInteractor
import com.example.kinopedia.network.models.KinopoiskSimilarFilms
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.example.kinopedia.ui.film.state.FilmScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmPageViewModel @Inject constructor(
    private val repository: FavouriteRepository,
    private val filmMapper: BaseMapper<KinopoiskFilm, KinopoiskFilmModel>,
    private val getFilmsInteractor: GetFilmsInteractor
) : ViewModel() {

    val isFavourite: MutableLiveData<Boolean> = MutableLiveData()

    val isAdded: MutableLiveData<Boolean> = MutableLiveData()

    private val _screenState = MutableLiveData<FilmScreenState>()
    val screenState: LiveData<FilmScreenState> = _screenState

    private val _film = MutableLiveData<KinopoiskFilm?>()
    val film: LiveData<KinopoiskFilm?> = _film

    private val _similar = MutableLiveData<KinopoiskSimilarFilms?>()
    val similar: LiveData<KinopoiskSimilarFilms?> = _similar

    private val _actorFilmPage = MutableLiveData<List<ActorFilmPage>?>()
    val actorFilmPage: LiveData<List<ActorFilmPage>?> = _actorFilmPage

    private val _staff = MutableLiveData<List<ActorFilmPage>?>()
    val staff: LiveData<List<ActorFilmPage>?> = _staff

    private val _externalSources = MutableLiveData<List<ExternalSource>?>()
    val externalSources: LiveData<List<ExternalSource>?> = _externalSources

    fun fetchFilm(kinopoiskId: Int){
        _screenState.value = FilmScreenState.Loading
        viewModelScope.launch {
            val film = getFilmsInteractor.getFilmById(kinopoiskId)
            val similarFilms = getFilmsInteractor.getSimilarFilms(kinopoiskId)
            val actorsAndStaff = getFilmsInteractor.getActors(kinopoiskId)
            val externalSources = getFilmsInteractor.getExternalSources(kinopoiskId)
            if (film is CallResult.Success && similarFilms is CallResult.Success
                && actorsAndStaff is CallResult.Success && externalSources is CallResult.Success) {
                val filmModel = filmMapper.map(film.value)
                _actorFilmPage.value = actorsAndStaff.value.filter { it.professionKey == "ACTOR" }
                _staff.value = actorsAndStaff.value.filter { it.professionKey != "ACTOR" }
                _similar.value = similarFilms.value
                _externalSources.value = externalSources.value.items
                _screenState.value = FilmScreenState.Loaded(filmModel)
            } else
                _screenState.value = FilmScreenState.Error
        }
    }

     fun saveFavourite  (
         filmId: Int,
         posterUrl: String,
         nameRu: String,
         year: String,
         country: String,
         genre: String,
         nameOriginal: String,
         ratingKinopoisk: String,
         ratingImdb: String,
         description: String,
         dateAdded: String
    ){

        val favouriteEntity = FavouriteEntity(
            filmId = filmId,
            posterUrl = posterUrl,
            nameRu = nameRu,
            year = year,
            country = country,
            genre = genre,
            nameOriginal = nameOriginal,
            ratingKinopoisk = ratingKinopoisk,
            ratingImdb = ratingImdb,
            description = description,
            dateAdded = dateAdded

        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(favouriteEntity)
        }
     }

    fun deleteFavourite(filmId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(filmId)
        }

    }

    fun checkButtonState(kinopoiskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val exists = repository.checkId(kinopoiskId) > 0
            isFavourite.postValue(exists)
        }
    }
    fun checkAdd(kinopoiskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val exists = repository.checkId(kinopoiskId) > 0
            isAdded.postValue(exists)
        }
    }
}
