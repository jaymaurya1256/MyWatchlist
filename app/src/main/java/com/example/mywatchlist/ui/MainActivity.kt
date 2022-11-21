 package com.example.mywatchlist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mywatchlist.ui.adapters.ViewPagerAdapter
import com.example.mywatchlist.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

 @AndroidEntryPoint
 class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val listOfFragment = listOf(HomeFragment(), HomeFragment2())
        binding.viewPager.adapter = ViewPagerAdapter(listOfFragment, this)


        //Log.d("MainActivity", "onCreate: ${RetrofitInstance.movieAPI.getMovies()}")
    }
}