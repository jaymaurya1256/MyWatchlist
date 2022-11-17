package com.example.mywatchlist.ui

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mywatchlist.database.DatabaseHolder
import com.example.mywatchlist.database.WatchlistDatabase
import com.example.mywatchlist.database.WatchlistTable
import com.example.mywatchlist.network.api.MoviesService
import com.example.mywatchlist.network.api.RetrofitInstance
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val BASE_URL = "https://api.themoviedb.org/3/movie/"

@HiltAndroidApp
class Watchlist: Application() {

    override fun onCreate() {
        super.onCreate()

        //Room initialization
        DatabaseHolder.DB = Room.databaseBuilder(this.applicationContext,
            WatchlistDatabase::class.java,
            "Watchlist_db"
        ).build()


        //Retrofit initialization
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        RetrofitInstance.movieAPI = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
            .create(MoviesService::class.java)

    }
}