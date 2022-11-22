package com.example.mywatchlist.ui.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywatchlist.database.WatchlistDao
import com.example.mywatchlist.database.WatchlistTable
import com.example.mywatchlist.network.api.MoviesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(val db: WatchlistDao, val api: MoviesService): ViewModel() {
    var watchlist = db.getAllMovies()

    fun removeFromList(id: Int){
        viewModelScope.launch {
            db.removeFromList(id)
        }
    }

}
