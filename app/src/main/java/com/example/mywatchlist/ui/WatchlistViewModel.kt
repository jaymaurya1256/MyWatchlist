package com.example.mywatchlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywatchlist.database.DatabaseHolder
import com.example.mywatchlist.database.WatchlistTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class WatchlistViewModel: ViewModel() {
    lateinit var watchlist: LiveData<List<WatchlistTable>>
    fun getMoviesFromDatabase(){
        viewModelScope.launch {
            watchlist = DatabaseHolder.DB.daoProvider().getAllMovies()
        }
    }

}