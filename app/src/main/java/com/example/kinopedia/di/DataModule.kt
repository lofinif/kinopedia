package com.example.kinopedia.di

import android.content.Context
import androidx.room.Room
import com.example.kinopedia.data.FavouriteDao
import com.example.kinopedia.data.FavouriteDatabase
import com.example.kinopedia.data.FavouriteDatabase.Companion.INSTANCE
import com.example.kinopedia.data.FavouriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): FavouriteDatabase {
        return INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(context, FavouriteDatabase::class.java, "favourite_database")
                .fallbackToDestructiveMigration().build()
            INSTANCE = instance
            instance
        }
    }
    @Provides
    @Singleton
    fun provideRepository(database: FavouriteDatabase): FavouriteRepository {
        return FavouriteRepository(database.favouriteDao())
    }

    @Provides
    @Singleton
    fun provideFavouriteDao(database: FavouriteDatabase): FavouriteDao {
        return database.favouriteDao()
    }

}