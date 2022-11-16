package com.example.mywatchlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mywatchlist.ui.adapters.WatchlistAdapter
import com.example.mywatchlist.databinding.FragmentWatchlistBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyWatchListFragment : Fragment() {
    private lateinit var binding: FragmentWatchlistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWatchlistBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewWatchlist.adapter = WatchlistAdapter()
    }
}