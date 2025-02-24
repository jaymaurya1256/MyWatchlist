package com.jay.mywatchlist.util

import android.content.Context
import coil.ImageLoader
import coil.request.CachePolicy
import okhttp3.OkHttpClient

fun createCoilImageLoader(context: Context): ImageLoader {
    val okHttpClient = OkHttpClient.Builder()
        .dns(GoogleDns()) // Force Google DNS
        .build()

    return ImageLoader.Builder(context)
        .okHttpClient(okHttpClient)
        .diskCachePolicy(CachePolicy.ENABLED) // Enable caching
        .build()
}
