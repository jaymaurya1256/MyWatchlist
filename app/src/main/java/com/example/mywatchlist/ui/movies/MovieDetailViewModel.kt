package com.example.mywatchlist.ui.movies

import android.util.Log
import androidx.lifecycle.*
import com.example.mywatchlist.database.WatchlistDao
import com.example.mywatchlist.database.WatchlistDatabase
import com.example.mywatchlist.database.WatchlistTable
import com.example.mywatchlist.network.api.MoviesService
import com.example.mywatchlist.network.entity.listofcast.ListOfCast
import com.example.mywatchlist.network.entity.moviedetails.MoviesDetails
import com.example.mywatchlist.network.entity.movieslist.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

private const val TAG = "MovieDetailViewModel"
@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val db: WatchlistDao, private val api: MoviesService) : ViewModel(){
    var requestedMovie = MutableLiveData<MoviesDetails>()
    var castList = MutableLiveData<ListOfCast>()
    var listOfAllIdOfSavedMovies = MutableLiveData(listOf(-1))
    private var savedMovies = db.getAllMovies()


    fun updateMovieIdList(){
        Log.d(TAG, "updateMovieIdList: entered in updateMovielist")
        Log.d(TAG, "updateMovieIdList: savedMoviesIdBefore ${listOfAllIdOfSavedMovies.value}")
        Log.d(TAG, "updateMovieIdList: savedMoviesBefore ${savedMovies.value}")
        while (savedMovies.value != null) {
            for (i in savedMovies.value!!){
                listOfAllIdOfSavedMovies.value = listOfAllIdOfSavedMovies.value?.plus(i.id)
            }
        }
        Log.d(TAG, "updateMovieIdList: savedMoviesIdAfter ${listOfAllIdOfSavedMovies.value}")
        Log.d(TAG, "updateMovieIdList: savedMovieAfter ${savedMovies.value}")


    }

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

    fun getCast(movieId: Int) {
        try {
            viewModelScope.launch {
                castList.value = api.getCast(movieId)
            }
        } catch (e: Exception) {
            Log.d(TAG, "getCast: $e")
        }
    }

    fun removeFromWatchlist(id: Int) {
        viewModelScope.launch {
            db.removeFromList(id)
            updateMovieIdList()
        }
    }

    fun addToWatchlist(id: Int, title: String, description: String, imageURL: String, isAdult: Boolean){
        viewModelScope.launch {
            db.addToWatchList(WatchlistTable(id, title, description, imageURL, isAdult))
            updateMovieIdList()
        }
    }
}