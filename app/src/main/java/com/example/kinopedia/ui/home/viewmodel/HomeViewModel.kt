package com.example.kinopedia.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.domain.interactors.GetHomeInteractor
import com.example.kinopedia.data.home.dto.ThisMonthFilm
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.home.model.FilmForAdapterModel
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import com.example.kinopedia.ui.home.state.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeInteractor: GetHomeInteractor,
    private val mapper: BaseMapper<FilmForAdapter, FilmForAdapterModel>,
    private val mapperPremiers: BaseMapper<ThisMonthFilm, ThisMonthFilmModel>
) : ViewModel() {
    companion object {
        const val TAG = "HomeViewModel"
    }

    private val _comingSoon = MutableLiveData<List<FilmForAdapterModel>?>()
    val comingSoon: LiveData<List<FilmForAdapterModel>?> = _comingSoon

    private val _premier = MutableLiveData<List<ThisMonthFilmModel>?>()
    val premier: LiveData<List<ThisMonthFilmModel>?> = _premier

    private val _await = MutableLiveData<List<FilmForAdapterModel>?>()
    val await: LiveData<List<FilmForAdapterModel>?> = _await

    private val _screenState = MutableLiveData<HomeScreenState>()
    val screenState: LiveData<HomeScreenState> = _screenState

    fun fetchLists() {
        _screenState.value = HomeScreenState.Loading
        viewModelScope.launch {
            val fetchComing = getHomeInteractor.getComingSoonFilms()
            val fetchAwait = getHomeInteractor.getAwaitFilms()
            val fetchPremier = getHomeInteractor.getPremierFilms()
            if (fetchComing is CallResult.Success && fetchAwait is CallResult.Success
                && fetchPremier is CallResult.Success){
                _premier.value = fetchPremier.value.items.map(mapperPremiers::map)
                _await.value = fetchAwait.value.films.map(mapper::map)
                _comingSoon.value = fetchComing.value.films.map(mapper::map)
                _screenState.value = HomeScreenState.Loaded
            } else _screenState.value = HomeScreenState.Error
        }
    }
}

