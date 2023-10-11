package com.example.kinopedia.ui.cinema

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.network.Cinemas
import com.example.kinopedia.network.CityOSM
import com.example.kinopedia.network.LoadingStatus
import com.example.kinopedia.network.OSMApi
import com.example.kinopedia.network.OverpassApi
import com.example.kinopedia.network.interceptorOSM
import com.example.kinopedia.network.interceptorOverpass
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.Exception

class NearestCinemaViewModel : ViewModel() {

    private val _city = MutableLiveData<CityOSM>()
    val city: LiveData<CityOSM> = _city

    private val _status = MutableLiveData<LoadingStatus>()
    val status: LiveData<LoadingStatus> = _status

    private val _cinemas = MutableLiveData<Cinemas>()
    val cinemas: LiveData<Cinemas> = _cinemas

    init {
        _status.value = LoadingStatus.LOADING
        interceptorOSM.level = HttpLoggingInterceptor.Level.BODY
        interceptorOverpass.level = HttpLoggingInterceptor.Level.BODY
    }

    fun getCity(latitude: Double, longitude: Double) = viewModelScope.launch {
        if (_city.value == null ) {
            _status.value = LoadingStatus.LOADING
            try {
                val list = OSMApi.retrofitService.getCity(latitude, longitude)
                _city.value = list
                _status.value = LoadingStatus.DONE
            } catch (E: Exception) {
                _status.value = LoadingStatus.ERROR
                Log.e("NearestCinemaViewModel", "getCity error")
            }
        }
    }

    fun getCinemas(data: String) = viewModelScope.launch {
        if (_cinemas.value == null) {
            _status.value = LoadingStatus.LOADING
            try {
                viewModelScope.launch {
                    val list = OverpassApi.retrofitService.getCinemas(data)
                    _cinemas.value = list
                    _status.value = LoadingStatus.DONE
                }
            } catch (E: Exception) {
                _status.value = LoadingStatus.ERROR
                Log.e("NearestCinemaViewModel", "getCinemas error")
            }
        }
    }
}