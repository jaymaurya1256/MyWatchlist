package com.jay.mywatchlist.network.entity.genrefilteredmovies

import com.jay.mywatchlist.network.entity.movieslist.Movie

data class MoviesFilteredByGenres(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)