package com.example.mywatchlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mywatchlist.ui.adapters.WatchlistAdapter
import com.example.mywatchlist.databinding.FragmentWatchlistBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyWatchListFragment : Fragment() {
    private lateinit var binding: FragmentWatchlistBinding
    val viewModel : WatchlistViewModel by viewModels()
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
        viewModel.addMovie()
        viewModel.watchlist.observe(viewLifecycleOwner){
            binding.recyclerViewWatchlist.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recyclerViewWatchlist.adapter = WatchlistAdapter(it)
        }
    }
}