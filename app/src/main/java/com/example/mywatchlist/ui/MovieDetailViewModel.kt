package com.example.mywatchlist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywatchlist.database.WatchlistDatabase
import com.example.mywatchlist.database.WatchlistTable
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val db: WatchlistDatabase) : ViewModel(){
    fun getDetailOfMovie(id: Int): WatchlistTable{
        return db.watchlistDao().getDetailsForId(id)
    }
    fun addToWatchlist(id: Int, title: String, description: String, imageURL: String){
        viewModelScope.launch {
            db.watchlistDao().addToWatchList(WatchlistTable(id, title, description, imageURL))
        }
    }
}