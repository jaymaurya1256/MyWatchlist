 package com.example.mywatchlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mywatchlist.adapters.ViewPagerAdapter
import com.example.mywatchlist.databinding.ActivityMainBinding

 class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listOfFragment = listOf(Movies(),Watchlist())
        binding.viewPager.adapter = ViewPagerAdapter(listOfFragment, this)
    }
}