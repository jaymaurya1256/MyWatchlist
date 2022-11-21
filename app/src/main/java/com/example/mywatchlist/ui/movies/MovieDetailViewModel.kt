package com.example.mywatchlist.ui.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywatchlist.database.WatchlistDatabase
import com.example.mywatchlist.database.WatchlistTable
import com.example.mywatchlist.network.entity.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val db: WatchlistDatabase) : ViewModel(){
    val movie = MutableLiveData<WatchlistTable>()

    fun getDetailOfMovie(id: Int) {
        viewModelScope.launch {
            movie.value = db.watchlistDao().getDetailsForId(id)
        }
    }

    fun addToWatchlist(id: Int, title: String, description: String, imageURL: String){
        viewModelScope.launch {
            db.watchlistDao().addToWatchList(WatchlistTable(id, title, description, imageURL))
        }
    }
}