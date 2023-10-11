package com.example.kinopedia.ui.film

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.data.FavouriteEntity
import com.example.kinopedia.data.FavouriteRepository
import com.example.kinopedia.network.ActorFilmPage
import com.example.kinopedia.network.ExternalSource
import com.example.kinopedia.network.Film
import com.example.kinopedia.network.FilmApi
import com.example.kinopedia.network.KinopoiskFilm
import com.example.kinopedia.network.LoadingStatus
import com.example.kinopedia.network.interceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.logging.HttpLoggingInterceptor

class FilmPageViewModel(private val repository: FavouriteRepository) : ViewModel() {


    private val liveDataFilm = MutableLiveData<KinopoiskFilm>()
    val data: LiveData<KinopoiskFilm> = liveDataFilm

    private val _status = MutableLiveData<LoadingStatus>()
    val status: LiveData<LoadingStatus> = _status

    private val liveDataSimilar = MutableLiveData<List<Film>>()
    val dataSimilar: LiveData<List<Film>> = liveDataSimilar

    private val liveDataActorFilmPage = MutableLiveData<List<ActorFilmPage>>()
    val dataActorFilmPage: LiveData<List<ActorFilmPage>> = liveDataActorFilmPage

    private val liveDataStaff = MutableLiveData<List<ActorFilmPage>>()
    val dataStaff: LiveData<List<ActorFilmPage>> = liveDataStaff

    private val liveDataExternalSources = MutableLiveData<List<ExternalSource>>()
    val dataExternalSources: LiveData<List<ExternalSource>> = liveDataExternalSources

    private val _year = MutableLiveData<String>()
    val year: LiveData<String> = _year.map { String.format("%1s", it) }

    private val _genre = MutableLiveData<String>()
    val genre: LiveData<String> = _genre.map { String.format("%2s", it) }

    private val _length = MutableLiveData<String>()
    val length: LiveData<String> = _length.map { String.format("%3s", it) }

    private val _country = MutableLiveData<String>()
    val country: LiveData<String> = _country.map { String.format("%4s", it) }

    fun getDataKinopoiskFilm(): KinopoiskFilm {
        return liveDataFilm.value ?: KinopoiskFilm(0, null, null,
            null, null, null, "", 0,"", "", emptyList(), emptyList(), 0.0, 0.0)
    }
    init {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    }

     suspend fun getFilmById(kinopoiskId: Int) = viewModelScope.launch(Dispatchers.IO) {
         Log.d("THREAD_DEBUG", "Current thread: " + Thread.currentThread().getName());
         val dash = "\u2014"
         _status.postValue(LoadingStatus.LOADING)
         try {
                 Log.d("THREAD_DEBUG", "Current thread: " + Thread.currentThread().getName());
                 val list = FilmApi.retrofitService.getFilmById(kinopoiskId)
                 liveDataFilm.postValue(list)

                 _year.postValue(data.value?.displayYear)
                 _genre.postValue(
                     if (data.value?.genres?.isEmpty() == true) dash else data.value?.genres?.get(0)?.genre.toString()
                 )
                 _country.postValue(
                     if (data.value?.countries?.isEmpty() == true) dash else data.value?.countries?.get(
                         0
                     )?.country.toString()
                 )
                 _length.postValue(data.value?.displayFilmLength.toString())
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

     fun getSimilarFilms(kinopoiskId: Int) = viewModelScope.launch{
         Log.e("THREAD_DEBUG", "Current thread: " + Thread.currentThread().getName());
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

    class FilmPageFactory(private val repository: FavouriteRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FilmPageViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FilmPageViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


}
