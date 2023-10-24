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

    private val liveDataFilm = MutableLiveData<KinopoiskFilm>()
    val data: LiveData<KinopoiskFilm> = liveDataFilm

    private val _status = MutableLiveData(LoadingStatus.DEFAULT)
    val status: LiveData<LoadingStatus> = _status

    private val liveDataSimilar = MutableLiveData<List<Film>>()
    val dataSimilar: LiveData<List<Film>> = liveDataSimilar

    private val liveDataActorFilmPage = MutableLiveData<List<ActorFilmPage>>()
    val dataActorFilmPage: LiveData<List<ActorFilmPage>> = liveDataActorFilmPage

    private val liveDataStaff = MutableLiveData<List<ActorFilmPage>>()
    val dataStaff: LiveData<List<ActorFilmPage>> = liveDataStaff

    private val liveDataExternalSources = MutableLiveData<List<ExternalSource>>()
    val dataExternalSources: LiveData<List<ExternalSource>> = liveDataExternalSources


    fun getDataKinopoiskFilm(): KinopoiskFilm {
        return liveDataFilm.value ?: KinopoiskFilm(0, null, null,
            null, null, null, "", 0,"", "", emptyList(), emptyList(), 0.0, 0.0)
    }

    fun getFilmById(kinopoiskId: Int) = viewModelScope.launch(Dispatchers.IO) {
             _status.postValue(LoadingStatus.LOADING)
             try {
                 val list = FilmApi.retrofitService.getFilmById(kinopoiskId)
                 liveDataFilm.postValue(list)
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
            liveDataActorFilmPage.postValue(filteredListActor)
            liveDataStaff.postValue(filteredListStaff)
            _status.postValue(LoadingStatus.DONE)
        } catch (e: Exception) {
            _status.postValue(LoadingStatus.ERROR)
    }
     }

     fun getSimilarFilms(kinopoiskId: Int) = viewModelScope.launch(Dispatchers.IO){
         _status.postValue(LoadingStatus.LOADING)
         try {
             val list = FilmApi.retrofitService.getSimilarFilms(kinopoiskId)
             liveDataSimilar.postValue(list.items)
             _status.postValue(LoadingStatus.DONE)
         } catch (e: Exception) {
             _status.postValue(LoadingStatus.ERROR)
    }
     }

    fun getExternalSources(kinopoiskId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _status.postValue(LoadingStatus.LOADING)
        try {
            val list = FilmApi.retrofitService.getExternalSources(kinopoiskId)
            liveDataExternalSources.postValue(list.items)
            _status.postValue(LoadingStatus.DONE)
        } catch (e: Exception) {
            _status.postValue(LoadingStatus.ERROR)
        }
    }

     fun saveRepository  (
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

/*
    class FilmPageFactory(private val repository: FavouriteRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FilmPageViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FilmPageViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
*/


}
