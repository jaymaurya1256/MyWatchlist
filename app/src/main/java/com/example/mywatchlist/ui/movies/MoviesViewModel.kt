package com.example.mywatchlist.ui.movies

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywatchlist.database.WatchlistDao
import com.example.mywatchlist.database.WatchlistTable
import com.example.mywatchlist.network.api.MoviesService
import com.example.mywatchlist.network.entity.movieslist.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

private const val TAG = "MoviesViewModel"

@HiltViewModel
class MoviesViewModel @Inject constructor(private val api: MoviesService, private val db: WatchlistDao) : ViewModel(){
    var movies = MutableLiveData<List<Movie>>()

    fun getMoviesFromWeb(){
        viewModelScope.launch {
            try {
                movies.value = api.getMovies().results
                Log.d(TAG, "getMovies: Success")
            } catch (e: Exception){
                Log.d(TAG, "getMovies: ${e.message}")
            }
        }
    }

    fun getMoviesFromWebGenre(genreId: Int){
        viewModelScope.launch {
            try {
                movies.value = api.getMoviesGenre(genreId).results
                Log.d(TAG, "getMovies: Success from lifecycle scope and the contents are: ${movies.value}")
            } catch (e: Exception){
                Log.d(TAG, "getMovies: ${e.message}")
            }
        }
    }

    fun addMovieToWatchlist(id: Int, title: String, description: String, image: String, isAdult: Boolean) {
        viewModelScope.launch {
            db.addToWatchList(
                WatchlistTable(id, title, description, image, isAdult)
            )
        }
    }

    fun searchMovies(keyword: String){
        viewModelScope.launch{
            try {
                movies.value = api.searchMovie(keyword).results
                Log.d(TAG, "searchMovies: Success with result ${movies.value}")
            }catch (e: Exception) {
                Log.d(TAG, "searchMovies: $e")
            }
        }
    }
}