package com.example.kinopedia.ui.favourite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.data.favourite.dto.FavouriteEntity
import com.example.kinopedia.domain.interactors.GetFavouriteInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getFavouriteInteractor: GetFavouriteInteractor
) : ViewModel() {

    private val _allFilms = getFavouriteInteractor.latestFilms.asLiveData()
    val allFilms: LiveData<List<FavouriteEntity>> = _allFilms

    fun deleteFavourite(filmId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getFavouriteInteractor.delete(filmId)
        }
    }
}