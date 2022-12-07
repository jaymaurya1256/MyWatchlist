package com.example.mywatchlist.ui

enum class Utils {
    GOTODESCRIPTION,
    ADDTOWATCHLIST,
    VISITWEB,
    REMOVE
}

class ListOfMoviesByCategories () {
    var topMovies: Boolean = true
    var popularMovies: Boolean = false
    var newMovies: Boolean = false
    var crimeMovies: Boolean = false
    var dramaMovies: Boolean = false
    var comedyMovies: Boolean = false
    var actionMovies: Boolean = false
    var suspenseMovies: Boolean = false
    var thrillerMovies: Boolean = false
    var horrorMovies: Boolean = false
    fun setEachToFalse(){
        var topMovies: Boolean = false
        var popularMovies: Boolean = false
        var newMovies: Boolean = false
        var crimeMovies: Boolean = false
        var dramaMovies: Boolean = false
        var comedyMovies: Boolean = false
        var actionMovies: Boolean = false
        var suspenseMovies: Boolean = false
        var thrillerMovies: Boolean = false
        var horrorMovies: Boolean = false
    }
}