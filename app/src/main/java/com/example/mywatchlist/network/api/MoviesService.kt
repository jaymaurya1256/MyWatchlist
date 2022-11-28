package com.example.mywatchlist.network.api

import com.example.mywatchlist.network.entity.genrefilteredmovies.MoviesFilteredByGenres
import com.example.mywatchlist.network.entity.moviedetails.MoviesDetails
import com.example.mywatchlist.network.entity.movieslist.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "8fa37f97887d4572adc080ab9662e405"

interface MoviesService {
    @GET("movie/top_rated?api_key=$API_KEY")
    suspend fun getMovies(): MoviesResponse

    @GET("discover/movie?api_key=${API_KEY}&with_genres={genreId}")
    suspend fun getMoviesGenre(@Query("genreId") genreId: Int): MoviesFilteredByGenres

    @GET("movie/{movieId}?api_key=${API_KEY}&language=en-US")
    suspend fun getDetail(@Path("movieId") movieId: Int): MoviesDetails
}