package com.example.kinopedia.ui.film

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.network.FilmApi
import kotlinx.coroutines.launch

class FilmPageViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private fun getFilmById() = viewModelScope.launch {
        val list = FilmApi.retrofitService.getFilmById(1)
    }
}