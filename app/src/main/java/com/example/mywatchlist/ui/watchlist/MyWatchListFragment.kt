package com.example.mywatchlist.ui.watchlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mywatchlist.R
import com.example.mywatchlist.databinding.FragmentWatchlistBinding
import com.example.mywatchlist.ui.Actions
import com.google.android.material.snackbar.Snackbar
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
                    Actions.REMOVE -> {
                        viewModel.removeFromList(movieId)
                        Snackbar.make(binding.root, "Movie removed from Watchlist", Snackbar.LENGTH_SHORT).show()
                    }
                    Actions.GO_TO_DESCRIPTION -> {
                        val navigationAction = MyWatchListFragmentDirections
                                .actionMyWatchListFragmentToMovieDetailsFragment(movieId)
                        findNavController().navigate(navigationAction)
                    }
                }
            }
            binding.clearWatchlist.setOnClickListener {
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.setMessage(R.string.clear_all)
                alertDialog.setCancelable(false)
                alertDialog.setPositiveButton(android.R.string.yes){ _,_ ->
                    viewModel.clearAll()
                }
                alertDialog.setNegativeButton(android.R.string.cancel){dialog,_ ->
                    dialog.cancel()
                }
                alertDialog.show()
            }
        }
    }
}