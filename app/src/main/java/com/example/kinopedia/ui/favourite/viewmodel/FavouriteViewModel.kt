package com.example.kinopedia.ui.favourite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.data.dao.FavouriteDao
import com.example.kinopedia.data.entities.FavouriteEntity
import com.example.kinopedia.data.repositories.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val repository: FavouriteRepository) : ViewModel() {

    private val _allFilms = repository.latestFilms.asLiveData()
    val allFilms: LiveData<List<FavouriteEntity>> = _allFilms

    fun deleteFavourite(filmId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(filmId)
        }
    }
}