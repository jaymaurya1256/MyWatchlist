package com.example.mywatchlist.ui.watchlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mywatchlist.databinding.FragmentWatchlistBinding
import com.example.mywatchlist.ui.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyWatchListFragment : Fragment() {
    private lateinit var binding: FragmentWatchlistBinding
    private val viewModel : WatchlistViewModel by viewModels()

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
        viewModel.watchlist.observe(viewLifecycleOwner){
            binding.recyclerViewWatchlist.layoutManager = GridLayoutManager(requireContext(), 1)
            binding.recyclerViewWatchlist.adapter = WatchlistAdapter(it){ movieId, action ->
                when(action){
                    Utils.REMOVE -> viewModel.removeFromList(movieId)
                    Utils.GOTODESCRIPTION -> {
                        val navigationAction = MyWatchListFragmentDirections
                                .actionMyWatchListFragmentToMovieDetailsFragment(movieId)
                        findNavController().navigate(navigationAction)
                    }
                }
            }
        }
    }
}