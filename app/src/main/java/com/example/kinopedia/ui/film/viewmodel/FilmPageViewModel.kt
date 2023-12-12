package com.example.kinopedia.ui.film.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.favourite.dto.FavouriteEntity
import com.example.kinopedia.data.film.ExternalSource
import com.example.kinopedia.data.film.dto.ActorFilmPage
import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.data.film.dto.KinopoiskFilm
import com.example.kinopedia.data.favourite.FavouriteRepositoryImpl
import com.example.kinopedia.domain.interactors.GetFilmsInteractor
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.film.model.ActorFilmPageModel
import com.example.kinopedia.ui.film.model.ExternalSourceModel
import com.example.kinopedia.ui.film.model.KinopoiskFilmModel
import com.example.kinopedia.ui.film.state.FilmScreenState
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmPageViewModel @Inject constructor(
    private val filmMapper: BaseMapper<KinopoiskFilm, KinopoiskFilmModel>,
    private val externalMapper: BaseMapper<ExternalSource, ExternalSourceModel>,
    private val similarMapper: BaseMapper <FilmForAdapter, FilmForAdapterModel>,
    private val actorMapper: BaseMapper<ActorFilmPage, ActorFilmPageModel>,
    private val repository: FavouriteRepositoryImpl,
    private val getFilmsInteractor: GetFilmsInteractor
) : ViewModel() {

    val isFavourite: MutableLiveData<Boolean> = MutableLiveData()

    val isAdded: MutableLiveData<Boolean> = MutableLiveData()

    private val _screenState = MutableLiveData<FilmScreenState>()
    val screenState: LiveData<FilmScreenState> = _screenState

    private val _film = MutableLiveData<KinopoiskFilmModel>()
    val film: LiveData<KinopoiskFilmModel> = _film

    private val _similar = MutableLiveData<List<FilmForAdapterModel>?>()
    val similar: LiveData<List<FilmForAdapterModel>?> = _similar

    private val _actorFilmPage = MutableLiveData<List<ActorFilmPageModel>?>()
    val actorFilmPage: LiveData<List<ActorFilmPageModel>?> = _actorFilmPage

    private val _staff = MutableLiveData<List<ActorFilmPageModel>?>()
    val staff: LiveData<List<ActorFilmPageModel>?> = _staff

    private val _externalSources = MutableLiveData<List<ExternalSourceModel>?>()
    val externalSources: LiveData<List<ExternalSourceModel>?> = _externalSources

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
                _film.value = filmModel
                _actorFilmPage.value = actorsAndStaff.value.filter { it.professionKey == "ACTOR" }.map (actorMapper::map)
                _staff.value = actorsAndStaff.value.filter { it.professionKey != "ACTOR" }.map (actorMapper::map)
                _similar.value = similarFilms.value.items?.map(similarMapper::map)
                _externalSources.value = externalSources.value.items.map(externalMapper::map)
                _screenState.value = FilmScreenState.Loaded(filmModel)
            } else
                _screenState.value = FilmScreenState.Error
        }
    }



     fun saveFavourite  (
         dateAdded: String
    ){
        val favouriteEntity =
                FavouriteEntity(
                    filmId = film.value!!.filmId,
                    posterUrl = film.value!!.posterUrl,
                    nameRu = film.value!!.displayName,
                    year = film.value!!.displayYear,
                    country = film.value!!.displayCountries,
                    genre = film.value!!.displayGenre,
                    nameOriginal = film.value!!.displayNameOriginal,
                    ratingKinopoisk = film.value!!.displayRatingKinopoisk,
                    ratingImdb = film.value!!.displayRatingImdb,
                    description = film.value!!.displayDescription,
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
