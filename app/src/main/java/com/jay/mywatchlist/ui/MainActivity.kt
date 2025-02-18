 package com.jay.mywatchlist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jay.mywatchlist.databinding.ActivityMainBinding
import com.jay.mywatchlist.ui.movies.MoviesContainerFragment
import com.jay.mywatchlist.ui.watchlist.WatchlistContainerFragment
import dagger.hilt.android.AndroidEntryPoint

 @AndroidEntryPoint
 class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = ViewPagerAdapter(
            listOf(MoviesContainerFragment(), WatchlistContainerFragment()),
            this
        )
    }
}