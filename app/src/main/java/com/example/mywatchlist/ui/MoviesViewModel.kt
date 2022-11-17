package com.example.mywatchlist.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.mywatchlist.network.api.RetrofitInstance
import com.example.mywatchlist.network.entity.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


private const val TAG = "MoviesViewModel"
@HiltViewModel
class MoviesViewModel @Inject constructor() : ViewModel(){
    var movies: MutableLiveData<List<Movie>>? = null
    fun getMoviesFromWeb(){
        viewModelScope.launch() {
            try {
                movies?.value = RetrofitInstance.movieAPI.getMovies().results
                Log.d(TAG, "getMovies: Success")
            } catch (e: Exception){
                Log.d(TAG, "getMovies: ${e.message}")
            }
        }
    }
}