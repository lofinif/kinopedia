package com.example.kinopedia.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kinopedia.data.dao.FavouriteDao
import com.example.kinopedia.data.favourite.dto.FavouriteEntity

@Database(entities = [FavouriteEntity::class], version = 3, exportSchema = false)
abstract class FavouriteDatabase : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao


    companion object {
        @Volatile
        var INSTANCE: FavouriteDatabase? = null

        fun getDatabase(context: Context): FavouriteDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    FavouriteDatabase::class.java,
                    "favourite_database"
                )
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }

        }
    }

}

