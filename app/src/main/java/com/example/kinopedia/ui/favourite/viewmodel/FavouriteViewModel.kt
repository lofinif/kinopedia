package com.example.kinopedia.ui.favourite.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.kinopedia.data.dao.FavouriteDao
import com.example.kinopedia.data.entities.FavouriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(val favouriteDao: FavouriteDao): ViewModel() {

    val allFilms: MutableLiveData<List<FavouriteEntity>> = favouriteDao.getLatestItem().asLiveData() as MutableLiveData<List<FavouriteEntity>>

    var updatedList = allFilms.value?.toMutableList()

    fun updateData(){
        allFilms.value = updatedList
    }

    fun deleteFavourite(filmId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            favouriteDao.deleteById(filmId)
        }
    }
}