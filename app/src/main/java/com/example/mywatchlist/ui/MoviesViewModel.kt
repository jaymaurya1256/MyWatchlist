package com.example.mywatchlist.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.mywatchlist.database.WatchlistDao
import com.example.mywatchlist.database.WatchlistTable
import com.example.mywatchlist.network.api.MoviesService
import com.example.mywatchlist.network.entity.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "MoviesViewModel"

@HiltViewModel
class MoviesViewModel @Inject constructor(private val api: MoviesService, private val db: WatchlistDao) : ViewModel(){
    var movies = MutableLiveData<List<Movie>>()

    fun getMoviesFromWeb(){
        viewModelScope.launch() {
            try {
                movies.value = api.getMovies().results
                Log.d(TAG, "getMovies: Success")
            } catch (e: Exception){
                Log.d(TAG, "getMovies: ${e.message}")
            }
        }
    }

    fun addMovieToWatchlist(id: Int, title: String, description: String, image: String) {
        viewModelScope.launch {
            db.addToWatchList(
                WatchlistTable(id, title, description, image)
            )
        }
    }

}