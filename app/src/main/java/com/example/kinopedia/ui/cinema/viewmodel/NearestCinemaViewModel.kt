package com.example.kinopedia.ui.cinema.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.network.models.Cinemas
import com.example.kinopedia.network.models.CityOSM
import com.example.kinopedia.network.services.LoadingStatus
import com.example.kinopedia.network.services.OSMApi
import com.example.kinopedia.network.services.OverpassApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class NearestCinemaViewModel : ViewModel() {

    private val _city = MutableLiveData<CityOSM>()
    val city: LiveData<CityOSM> = _city

    private val _status = MutableLiveData(LoadingStatus.DEFAULT)
    val status: LiveData<LoadingStatus> = _status

    private val _cinemas = MutableLiveData<Cinemas>()
    val cinemas: LiveData<Cinemas> = _cinemas


    fun getCity(latitude: Double, longitude: Double) {
        if (_city.value == null) {
            viewModelScope.launch(Dispatchers.IO) {
                _status.postValue(LoadingStatus.LOADING)
                try {
                    val list = OSMApi.retrofitService.getCity(latitude, longitude)
                    _city.postValue(list)
                    _status.postValue(LoadingStatus.DONE)
                } catch (E: Exception) {
                    _status.postValue(LoadingStatus.ERROR)
                    Log.e("NearestCinemaViewModel", "getCity error")
                }
            }
        }
    }

    fun getCinemas() {
        if (_cinemas.value == null) {
           val data =
                "[out:json];area[name=\"${city.value?.address?.displayCity.toString()}\"](around:1000.0);nwr[amenity=cinema](area);out geom;"
            viewModelScope.launch(Dispatchers.IO) {
                _status.postValue(LoadingStatus.LOADING)
                try {
                    viewModelScope.launch {
                        val list = OverpassApi.retrofitService.getCinemas(data)
                        _cinemas.postValue(list)
                        _status.postValue(LoadingStatus.DONE)
                    }
                } catch (E: Exception) {
                    _status.postValue(LoadingStatus.ERROR)
                    Log.e("NearestCinemaViewModel", "getCinemas error")
                }
            }
        }
    }
}