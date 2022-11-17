package com.example.mywatchlist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DAO {
    @Insert
    suspend fun addToWatchList(watchlistTable: WatchlistTable)

    @Delete
    suspend fun removeFromList(watchlistTable: WatchlistTable)

    @Query("delete from WatchlistTable")
    suspend fun clearList()

    @Query("select * from WatchlistTable")
    fun getAllMovies(): LiveData<List<WatchlistTable>>
}