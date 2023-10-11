package com.example.kinopedia

import android.app.Application
import com.example.kinopedia.data.FavouriteDatabase
import com.example.kinopedia.data.FavouriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FavouriteApplication: Application() {

    val database by lazy { FavouriteDatabase.getDatabase(this) }

    val repository by lazy { FavouriteRepository(database.favouriteDao()) }



}