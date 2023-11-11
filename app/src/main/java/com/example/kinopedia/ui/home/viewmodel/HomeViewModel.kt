package com.example.kinopedia.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.data.film.dto.FilmForAdapter
import com.example.kinopedia.data.home.dto.TopFilms
import com.example.kinopedia.domain.interactors.GetHomeInteractor
import com.example.kinopedia.network.models.ThisMonthFilm
import com.example.kinopedia.network.services.LoadingStatus
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.home.model.FilmForAdapterModelHome
import com.example.kinopedia.ui.home.model.ThisMonthFilmModel
import com.example.kinopedia.ui.home.state.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeInteractor: GetHomeInteractor,
    private val mapper: BaseMapper<FilmForAdapter, FilmForAdapterModelHome>,
    private val mapperPremiers: BaseMapper<ThisMonthFilm, ThisMonthFilmModel>
) : ViewModel() {

    val flowAwaitFilms = getHomeInteractor.awaitFilms.shareIn(viewModelScope, SharingStarted.WhileSubscribed(replayExpirationMillis = 0))
        .map {
            it.films.map { filmForAdapter ->
                mapper.map(filmForAdapter)
            }
        }

    val flowComingSoon = getHomeInteractor.comingSoonFilms.shareIn(viewModelScope, SharingStarted.WhileSubscribed(replayExpirationMillis = 0))
        .map {
            it.films.map { filmForAdapter ->
                mapper.map(filmForAdapter)
            }
        }
    val flowPremiers = getHomeInteractor.premierFilms.shareIn(viewModelScope, SharingStarted.WhileSubscribed(replayExpirationMillis = 0))
        .map {
            it.items.map { premiers ->
                mapperPremiers.map(premiers)
            }
        }
    private val _statusAwait = MutableLiveData(LoadingStatus.DEFAULT)
    val statusAwait: LiveData<LoadingStatus> = _statusAwait

    private val _screenState = MutableLiveData<HomeScreenState>()
    val screenState: LiveData<HomeScreenState> = _screenState

    private val _statusPopular = MutableLiveData(LoadingStatus.DEFAULT)
    val statusPopular: LiveData<LoadingStatus> = _statusPopular

    private val _statusMonth = MutableLiveData(LoadingStatus.DEFAULT)
    val statusMonth: LiveData<LoadingStatus> = _statusMonth

    private val _coming = MutableLiveData<TopFilms>()
    val coming: LiveData<TopFilms> = _coming

    private val _trending = MutableLiveData<TopFilms>()
    val trending: LiveData<TopFilms> = _trending

    private val _thisMonth = MutableLiveData<List<TopFilms>>()
    val thisMonth: LiveData<List<TopFilms>> = _thisMonth
    fun fetchAwaitFilms() {


/*        fun getPopularFilms() {
            if (_trending.value?.filmForAdapters.isNullOrEmpty()) {
                viewModelScope.launch(Dispatchers.IO) {
                    _statusPopular.postValue(LoadingStatus.LOADING)
                    try {
                        val list = FilmApi.retrofitService.getPopularFilms(1)
                        _trending.postValue(list)
                        _statusPopular.postValue(LoadingStatus.DONE)
                    } catch (e: Exception) {
                        _statusPopular.postValue(LoadingStatus.ERROR)
                    }
                }
            }
        }
            fun getFilmsThisMonth() {
                if (_thisMonth.value.isNullOrEmpty()) {
                     viewModelScope.launch(Dispatchers.IO){
                        _statusMonth.postValue(LoadingStatus.LOADING)
                        try {
                            val calendar = Calendar.getInstance()
                            val monthFormat = SimpleDateFormat("MMMM", Locale.US)
                            val year = calendar.get(Calendar.YEAR)
                            val month = monthFormat.format(calendar)
                            val list = FilmApi.retrofitService.getFilmsThisMonth(year, month, 1)
                            _statusMonth.postValue(LoadingStatus.DONE)
                        } catch (e: Exception) {
                            _thisMonth.postValue(emptyList())
                            _statusMonth.postValue(LoadingStatus.ERROR)
                        }
                    }
                }*/
            }
        }

