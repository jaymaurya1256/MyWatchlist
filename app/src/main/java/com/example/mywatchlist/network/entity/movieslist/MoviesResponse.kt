package com.example.mywatchlist.network.entity.movieslist

import com.example.mywatchlist.network.entity.movieslist.Movie

data class MoviesResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)