package com.example.mywatchlist.di.modules

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mywatchlist.database.WatchlistDatabase
import com.example.mywatchlist.network.api.MoviesService
import com.example.mywatchlist.ui.BASE_URL
import com.example.mywatchlist.ui.appContext
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    fun retrofitInstanceProvider(moshi : Moshi): MoviesService{
        return Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
            .create(MoviesService::class.java)
    }
    @Provides
    fun dataBaseProvider(): WatchlistDatabase{
        return Room.databaseBuilder(appContext,
            WatchlistDatabase::class.java, "watch_list")
            .build()
    }
    @Provides
    fun moshiProvider(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
}