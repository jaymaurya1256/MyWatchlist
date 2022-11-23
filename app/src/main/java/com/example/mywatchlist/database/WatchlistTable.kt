package com.example.mywatchlist.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.Nullable
import javax.inject.Inject

@Entity
data class WatchlistTable(
    @PrimaryKey val id : Int,
    val title: String,
    @Nullable val description: String,
    @Nullable val image: String,
    @Nullable val isAdult: Boolean
)