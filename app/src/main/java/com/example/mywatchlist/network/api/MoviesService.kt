package com.example.mywatchlist.network.api

import android.telecom.Call
import androidx.lifecycle.MutableLiveData
import com.example.mywatchlist.network.entity.moviedetails.MoviesDetails
import com.example.mywatchlist.network.entity.movieslist.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

const val API_KEY = "8fa37f97887d4572adc080ab9662e405"

interface MoviesService {
    @GET("top_rated?api_key=$API_KEY")
    suspend fun getMovies(): MoviesResponse
    @GET("{movieId}?api_key=${API_KEY}&language=en-US")
    suspend fun getDetail(@Path("movieId") movieId: Int): MoviesDetails
}