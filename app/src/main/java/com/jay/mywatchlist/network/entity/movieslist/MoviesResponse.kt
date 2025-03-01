package com.jay.mywatchlist.network.entity.movieslist

data class MoviesResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)