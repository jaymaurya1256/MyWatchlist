package com.example.mywatchlist.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WatchlistTable::class], version = 1)
abstract class WatchlistDatabase: RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao
}
