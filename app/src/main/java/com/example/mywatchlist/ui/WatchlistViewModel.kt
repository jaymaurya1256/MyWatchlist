package com.example.mywatchlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywatchlist.database.DatabaseHolder
import com.example.mywatchlist.database.WatchlistDatabase
import com.example.mywatchlist.database.WatchlistTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(val watchList: WatchlistDatabase): ViewModel() {
    var watchlist = watchList.daoProvider().getAllMovies()

    fun addMovie() {
        viewModelScope.launch {
            watchList.daoProvider().addToWatchList(WatchlistTable(1,
                "Let me down slowly",
                "not available",
                "not available")
            )
            watchList.daoProvider().addToWatchList(WatchlistTable(2,
                "Let me down slowly",
                "not available",
                "not available")
            )
            watchList.daoProvider().addToWatchList(WatchlistTable(3,
                "Let me down slowly",
                "not available",
                "not available")
            )
            watchList.daoProvider().addToWatchList(WatchlistTable(4,
                "Let me down slowly",
                "not available",
                "not available")
            )

        }
    }
}
