package com.example.kinopedia.ui.film.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.data.entities.FavouriteEntity
import com.example.kinopedia.data.repositories.FavouriteRepository
import com.example.kinopedia.network.models.ActorFilmPage
import com.example.kinopedia.network.models.ExternalSource
import com.example.kinopedia.network.models.Film
import com.example.kinopedia.network.services.FilmApi
import com.example.kinopedia.network.models.KinopoiskFilm
import com.example.kinopedia.network.services.LoadingStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmPageViewModel @Inject constructor(
    private val repository: FavouriteRepository
) : ViewModel() {

    val isFavourite: MutableLiveData<Boolean> = MutableLiveData()

    val isAdded: MutableLiveData<Boolean> = MutableLiveData()

    private val _film = MutableLiveData<KinopoiskFilm>()
    val film: LiveData<KinopoiskFilm> = _film

    private val _status = MutableLiveData(LoadingStatus.DEFAULT)
    val status: LiveData<LoadingStatus> = _status

    private val _similar = MutableLiveData<List<Film>>()
    val similar: LiveData<List<Film>> = _similar

    private val _actorFilmPage = MutableLiveData<List<ActorFilmPage>>()
    val actorFilmPage: LiveData<List<ActorFilmPage>> = _actorFilmPage

    private val _staff = MutableLiveData<List<ActorFilmPage>>()
    val staff: LiveData<List<ActorFilmPage>> = _staff

    private val _externalSources = MutableLiveData<List<ExternalSource>>()
    val externalSources: LiveData<List<ExternalSource>> = _externalSources


    fun getDataKinopoiskFilm(): KinopoiskFilm {
        return _film.value ?: KinopoiskFilm(0, null, null,
            null, null, null, "", 0,"", "", emptyList(), emptyList(), 0.0, 0.0)
    }

    fun getFilmById(kinopoiskId: Int) = viewModelScope.launch(Dispatchers.IO) {
             _status.postValue(LoadingStatus.LOADING)
             try {
                 val list = FilmApi.retrofitService.getFilmById(kinopoiskId)
                 _film.postValue(list)
                 _status.postValue(LoadingStatus.DONE)

         } catch (e: Exception) {
             _status.postValue(LoadingStatus.ERROR)
         }
    }

     fun getActors(kinopoiskId: Int) = viewModelScope.launch(Dispatchers.IO) {
             _status.postValue(LoadingStatus.LOADING)
         try {
             val list = FilmApi.retrofitService.getActorsAndStaff(kinopoiskId)
            val filteredListActor = list.filter { it.professionKey == "ACTOR" }
            val filteredListStaff = list.filter { it.professionKey != "ACTOR" }
            _actorFilmPage.postValue(filteredListActor)
            _staff.postValue(filteredListStaff)
            _status.postValue(LoadingStatus.DONE)
        } catch (e: Exception) {
            _status.postValue(LoadingStatus.ERROR)
    }
     }

     fun getSimilarFilms(kinopoiskId: Int) = viewModelScope.launch(Dispatchers.IO){
         _status.postValue(LoadingStatus.LOADING)
         try {
             val list = FilmApi.retrofitService.getSimilarFilms(kinopoiskId)
             _similar.postValue(list.items)
             _status.postValue(LoadingStatus.DONE)
         } catch (e: Exception) {
             _status.postValue(LoadingStatus.ERROR)
    }
     }

    fun getExternalSources(kinopoiskId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _status.postValue(LoadingStatus.LOADING)
        try {
            val list = FilmApi.retrofitService.getExternalSources(kinopoiskId)
            _externalSources.postValue(list.items)
            _status.postValue(LoadingStatus.DONE)
        } catch (e: Exception) {
            _status.postValue(LoadingStatus.ERROR)
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
