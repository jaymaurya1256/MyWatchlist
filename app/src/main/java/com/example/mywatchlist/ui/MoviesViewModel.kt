package com.example.mywatchlist.ui

import androidx.lifecycle.*
import com.example.mywatchlist.network.api.RetrofitInstance
import com.example.mywatchlist.network.entity.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor() : ViewModel(){
    var movies: MutableLiveData<List<Movie>>? = null
    fun getMovies(){
        viewModelScope.launch() {
            movies?.value = RetrofitInstance.movieAPI.getMovies().results
        }
    }
}