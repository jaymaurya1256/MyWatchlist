package com.jay.mywatchlist.di

import com.jay.mywatchlist.util.GoogleDns
import android.content.Context
import androidx.room.Room
import com.jay.mywatchlist.database.WatchlistDao
import com.jay.mywatchlist.database.WatchlistDatabase
import com.jay.mywatchlist.network.api.MoviesService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

const val BASE_URL = "https://api.themoviedb.org/3/"

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideOkHttpClient() =
        OkHttpClient.Builder().dns(GoogleDns()).addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()

    @Provides
    @Singleton
    fun retrofitInstanceProvider(moshi: Moshi, okHttpClient: OkHttpClient): MoviesService {
        return Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL).client(okHttpClient).build().create(MoviesService::class.java)
    }

    @Provides
    @Singleton
    fun dataBaseProvider(@ApplicationContext applicationContext: Context): WatchlistDatabase {
        return Room.databaseBuilder(
            applicationContext, WatchlistDatabase::class.java, "watch_list"
        ).build()
    }

    @Provides
    @Singleton
    fun moshiProvider(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideDao(db: WatchlistDatabase): WatchlistDao {
        return db.watchlistDao()
    }
}