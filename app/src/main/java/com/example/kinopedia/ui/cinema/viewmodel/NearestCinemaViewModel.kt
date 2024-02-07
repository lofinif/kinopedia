package com.example.kinopedia.ui.cinema.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.data.CallResult
import com.example.kinopedia.data.cinema.dto.CinemaOSM
import com.example.kinopedia.data.cinema.dto.CityOSM
import com.example.kinopedia.domain.interactors.GetCinemasInteractor
import com.example.kinopedia.ui.BaseMapper
import com.example.kinopedia.ui.cinema.model.CinemaOSMModel
import com.example.kinopedia.ui.cinema.model.CityOSMModel
import com.example.kinopedia.ui.cinema.state.CinemaScreenState
import com.example.kinopedia.utils.LocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearestCinemaViewModel @Inject constructor(
    private val getCinemasInteractor: GetCinemasInteractor,
    val locationProvider: LocationProvider,
    private val mapperCity: BaseMapper<CityOSM, CityOSMModel>,
    private val mapperCinemas: BaseMapper<CinemaOSM, CinemaOSMModel>
) : ViewModel() {

    companion object {
        const val TAG = "NearestCinemaViewModel"
    }

    var filteredElements = emptyList<CinemaOSMModel>()

    var latitude = 0.0
    var longitude = 0.0

    private val _screenState = MutableLiveData<CinemaScreenState>()
    val screenState: LiveData<CinemaScreenState> = _screenState

    private val _city = MutableLiveData<CityOSMModel?>()
    val city: LiveData<CityOSMModel?> = _city

    private val _cinemas = MutableLiveData<List<CinemaOSMModel>?>(null)
    val cinemas: LiveData<List<CinemaOSMModel>?> = _cinemas

    fun fetchCinemas(latitude: Double, longitude: Double) {
        _screenState.value = CinemaScreenState.Loading
        Log.e(TAG, "start fetching")
        viewModelScope.launch {
            when (val getCity = getCinemasInteractor.getCity(latitude, longitude)) {
                is CallResult.Success -> {
                    _city.value = getCity.value.let { mapperCity.map(it) }
                    val city = city.value?.address?.displayCity.toString()
                    when (val getCinemas = getCinemasInteractor.getCinemas(city)) {
                        is CallResult.Success -> {
                            _cinemas.value = getCinemas.value.elements.map(mapperCinemas::map)
                            _screenState.value = CinemaScreenState.Loaded
                        }
                        else -> {
                            Log.e(TAG, "error fetching cinema overpass api")
                            _screenState.value = CinemaScreenState.Error
                        }
                    }
                }
                else -> {
                    Log.e(TAG, "error fetching city overpass api")
                    _screenState.value = CinemaScreenState.Error
                }
            }
        }
    }

    fun setCallBack(callback: (Location) -> Unit) {
        locationProvider.setCallback(callback)
    }
}