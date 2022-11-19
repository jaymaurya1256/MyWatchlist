package com.example.mywatchlist.network.api

import androidx.lifecycle.LiveData
import com.example.mywatchlist.network.entity.MoviesResponse
import retrofit2.http.GET

const val API_KEY = "8fa37f97887d4572adc080ab9662e405"

interface MoviesService {
    @GET("top_rated?api_key=$API_KEY")
    suspend fun getMovies(): MoviesResponse
}