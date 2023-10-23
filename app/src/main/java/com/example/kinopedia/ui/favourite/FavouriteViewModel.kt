package com.example.kinopedia.ui.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.kinopedia.data.FavouriteDao
import com.example.kinopedia.data.FavouriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(favouriteDao: FavouriteDao): ViewModel() {

    val allFilms: MutableLiveData<List<FavouriteEntity>> = favouriteDao.getLatestItem().asLiveData() as MutableLiveData<List<FavouriteEntity>>

    var updatedList = allFilms.value?.toMutableList()

    fun updateData(){
        allFilms.value = updatedList
    }
}