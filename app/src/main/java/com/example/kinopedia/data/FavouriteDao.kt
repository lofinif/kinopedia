package com.example.kinopedia.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM favourite_database")
    fun getFavourite(): Flow<List<FavouriteEntity>>

    @Query("SELECT * FROM favourite_database WHERE filmId = :id")
    fun getFavourite(id: Long): Flow<FavouriteEntity>

    @Query("SELECT COUNT(*) FROM favourite_database WHERE filmId = :id")
    fun checkId(id: Int): Int

    @Query("SELECT * FROM favourite_database ORDER BY dateAdded DESC")
    fun getLatestItem(): Flow<List<FavouriteEntity>>

    @Query("DELETE FROM favourite_database WHERE filmId = :id")
    suspend fun deleteById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favourite: FavouriteEntity)

    @Update
    suspend fun update(favourite: FavouriteEntity)

    @Delete
    suspend fun delete(favourite: FavouriteEntity)
}