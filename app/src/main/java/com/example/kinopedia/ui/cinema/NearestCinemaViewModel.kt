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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor
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

    fun getCinemas(data: String) {
        if (_cinemas.value == null) {
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