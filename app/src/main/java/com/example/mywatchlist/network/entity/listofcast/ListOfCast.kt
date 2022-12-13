package com.example.mywatchlist.network.entity.listofcast

data class ListOfCast(
    val cast: List<Cast>?,
    val crew: List<Crew>?,
    val id: Int?
)