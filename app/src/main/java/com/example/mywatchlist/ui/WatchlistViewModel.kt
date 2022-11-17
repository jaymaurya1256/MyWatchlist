package com.example.mywatchlist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywatchlist.database.WatchlistDao
import com.example.mywatchlist.database.WatchlistTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(val db: WatchlistDao): ViewModel() {
    var watchlist = db.getAllMovies()

    fun addMovie() {
        viewModelScope.launch {
            db.addToWatchList(WatchlistTable(1,
                "Let me down slowly",
                "not available",
                "not available")
            )
            db.addToWatchList(WatchlistTable(2,
                "Let me down slowly",
                "not available",
                "not available")
            )
            db.addToWatchList(WatchlistTable(3,
                "Let me down slowly",
                "not available",
                "not available")
            )
            db.addToWatchList(WatchlistTable(4,
                "Let me down slowly",
                "not available",
                "not available")
            )

        }
    }
}
