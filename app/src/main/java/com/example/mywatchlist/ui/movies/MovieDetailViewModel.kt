package com.example.mywatchlist.ui.movies

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywatchlist.database.WatchlistDao
import com.example.mywatchlist.database.WatchlistDatabase
import com.example.mywatchlist.database.WatchlistTable
import com.example.mywatchlist.network.api.MoviesService
import com.example.mywatchlist.network.entity.moviedetails.MoviesDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

private const val TAG = "MovieDetailViewModel"
@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val db: WatchlistDao, private val api: MoviesService) : ViewModel(){
    var requestedMovie = MutableLiveData<MoviesDetails>()

    fun getMovieDetails(movieId: Int){
        Log.d(TAG, "getMovieDetails: function called in view model")
        viewModelScope.launch {
            try {
                Log.d(TAG, "getMovieDetails: calling from movies detail view model")
                requestedMovie.value = api.getDetail(movieId)
                Log.d(TAG, "getMovieDetails: success")
            } catch (e: Exception){
                Log.d(TAG, "getMovieDetails: failed with - $e")
            }
        }
    }


    fun addToWatchlist(id: Int, title: String, description: String, imageURL: String, isAdult: Boolean){
        viewModelScope.launch {
            db.addToWatchList(WatchlistTable(id, title, description, imageURL, isAdult))
        }
    }
}