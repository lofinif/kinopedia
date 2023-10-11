package com.example.kinopedia.ui.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.kinopedia.data.FavouriteDao
import com.example.kinopedia.data.FavouriteEntity


class FavouriteViewModel(favouriteDao: FavouriteDao): ViewModel() {

    val allFilms: MutableLiveData<List<FavouriteEntity>> = favouriteDao.getLatestItem().asLiveData() as MutableLiveData<List<FavouriteEntity>>

    var updatedList = allFilms.value?.toMutableList()

    fun updateData(){
        allFilms.value = updatedList
    }


    class FavouriteFactory(private val favouriteDao: FavouriteDao): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FavouriteViewModel(favouriteDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}