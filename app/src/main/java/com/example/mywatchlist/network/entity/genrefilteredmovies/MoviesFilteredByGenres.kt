package com.example.mywatchlist.network.entity.genrefilteredmovies

import com.example.mywatchlist.network.entity.movieslist.Movie

data class MoviesFilteredByGenres(
    val page: Int,
    val movies: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)