package com.example.mywatchlist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WatchlistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWatchList(watchlistTable: WatchlistTable)

    @Delete
    suspend fun removeFromList(watchlistTable: WatchlistTable)

    @Query("delete from WatchlistTable")
    suspend fun clearList()

    @Query("select * from WatchlistTable")
    fun getAllMovies(): LiveData<List<WatchlistTable>>
}