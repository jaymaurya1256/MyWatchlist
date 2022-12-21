package com.example.mywatchlist.ui.movies

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywatchlist.database.WatchlistDao
import com.example.mywatchlist.database.WatchlistTable
import com.example.mywatchlist.network.api.MoviesService
import com.example.mywatchlist.network.entity.movieslist.Movie
import com.example.mywatchlist.util.Filters
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "MoviesViewModel"

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val api: MoviesService,
    private val db: WatchlistDao,
) : ViewModel() {
    var movies = MutableLiveData<List<Movie>>()
    private var page = 0

    var networkJob: Job? = null

    var currentFilter = Filters.POPULAR
        set(value) {
            if (field != value) {
                field = value
                page = 0
                movies.value = emptyList()
                getMovies(value)
            }
            field = value
        }

    init {
        getMovies(currentFilter)
    }

    fun getMoreMovies() {
        if (networkJob == null) {
            Log.d(TAG, "getMoreMovies: Attempting to fetch more movies, current filter: $currentFilter")
            getMovies(currentFilter, true)
        }
    }

    private fun getMovies(filter: Filters, paging: Boolean = false) {
        networkJob = viewModelScope.launch {
            try {
                movies.value = when (filter) {
                    Filters.TOP_RATED -> {
                        if (paging) movies.value?.plus(api.getMoviesTopRated(++page).results) else api.getMoviesTopRated(++page).results
                    }
                    Filters.NEW_RELEASES -> {
                        if (paging) movies.value?.plus(api.getMoviesNewReleases(++page).results) else api.getMoviesNewReleases(++page).results
                    }
                    Filters.POPULAR -> {
                        if (paging) movies.value?.plus(api.getMoviesPopular(++page).results) else api.getMoviesPopular(++page).results
                    }
                    Filters.CRIME -> {
                        if (paging) movies.value?.plus(api.getMoviesGenre(80, ++page).results) else api.getMoviesGenre(80, ++page).results
                    }
                    Filters.DRAMA -> {
                        if (paging) movies.value?.plus(api.getMoviesGenre(18, ++page).results) else api.getMoviesGenre(18, ++page).results
                    }
                    Filters.COMEDY -> {
                        if (paging) movies.value?.plus(api.getMoviesGenre(35, ++page).results) else api.getMoviesGenre(35, ++page).results
                    }
                    Filters.ACTION -> {
                        if (paging) movies.value?.plus(api.getMoviesGenre(28, ++page).results) else api.getMoviesGenre(28, ++page).results
                    }
                    Filters.SUSPENSE -> {
                        if (paging) movies.value?.plus(api.getMoviesGenre(9648, ++page).results) else api.getMoviesGenre(9648, ++page).results
                    }
                    Filters.THRILLER -> {
                        if (paging) movies.value?.plus(api.getMoviesGenre(53, ++page).results) else api.getMoviesGenre(53, ++page).results
                    }
                    Filters.HORROR -> {
                        if (paging) movies.value?.plus(api.getMoviesGenre(27, ++page).results) else api.getMoviesGenre(27, ++page).results
                    }
                    Filters.SEARCH -> {
                        if (paging) movies.value?.plus(api.searchMovie("", ++page).results) else api.searchMovie("", ++page).results
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "getMovies: Error fetching movies: ${e.message}")
                e.printStackTrace()
            } finally {
                networkJob = null
            }
        }
    }

    fun addMovieToWatchlist(
        id: Int,
        title: String,
        description: String,
        image: String,
        isAdult: Boolean,
    ) {
        viewModelScope.launch {
            db.addToWatchList(
                WatchlistTable(id, title, description, image, isAdult)
            )
        }
    }

    fun searchMovies(keyword: String) {
        viewModelScope.launch {
            try {
                movies.value = api.searchMovie(keyword, 1).results
                Log.d(TAG, "searchMovies: Success with result ${movies.value}")
            } catch (e: Exception) {
                Log.d(TAG, "searchMovies: $e")
            }
        }
    }
}