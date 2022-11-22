package com.example.mywatchlist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WatchlistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWatchList(watchlistTable: WatchlistTable)

    @Query("DELETE FROM WatchlistTable WHERE id == :id")
    suspend fun removeFromList(id: Int)

    @Query("DELETE FROM WatchlistTable")
    suspend fun clearList()

    @Query("SELECT * FROM WatchlistTable")
    fun getAllMovies(): LiveData<List<WatchlistTable>>

    @Query("SELECT * FROM WatchlistTable WHERE id == :id")
    suspend fun getDetailsForId(id: Int): WatchlistTable?
}