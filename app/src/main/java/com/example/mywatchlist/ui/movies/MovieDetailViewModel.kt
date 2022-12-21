package com.example.mywatchlist.ui.movies

import android.util.Log
import androidx.lifecycle.*
import com.example.mywatchlist.database.WatchlistDao
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
class MovieDetailViewModel @Inject constructor(
    private val db: WatchlistDao,
    private val api: MoviesService,
) : ViewModel() {
    val movieDetails = MutableLiveData<MoviesDetails>()
    val castList = MutableLiveData<ListOfCast>()
    val message = MutableLiveData<String>()

    fun getMovieFromDB(movieId: Int) = db.getMovie(movieId)

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                movieDetails.value = api.getDetail(movieId)
                getCast(movieId)
            } catch (_: Exception) {
                Log.e(TAG, "getMovieDetails: ")
            }
        }
    }

    private fun getCast(movieId: Int) {
        try {
            viewModelScope.launch {
                castList.value = api.getCast(movieId)
            }
        } catch (e: Exception) {
            Log.d(TAG, "getCast: $e")
        }
    }

    fun switchWatchlistStatus(movieDetail: MoviesDetails) {
        viewModelScope.launch {
            val movie = db.getMovieOneShot(movieDetail.id)
            if (movie != null) {
                db.removeFromList(movie.id)
                message.value = "${movieDetail.title} removed from the watchlist"
            } else {
                db.addToWatchList(
                    WatchlistTable(
                        movieDetail.id,
                        movieDetail.title,
                        movieDetail.overview,
                        movieDetail.poster_path ?: movieDetail.backdrop_path ?: "",
                        movieDetail.adult
                    )
                )
                message.value = "${movieDetail.title} added to the watchlist"
            }
        }
    }
}