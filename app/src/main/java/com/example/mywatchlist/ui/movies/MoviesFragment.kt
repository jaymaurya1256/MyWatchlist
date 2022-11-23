package com.example.mywatchlist.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mywatchlist.R
import com.example.mywatchlist.databinding.FragmentMoviesBinding
import com.example.mywatchlist.ui.Utils
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MoviesFragment"

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var binding: FragmentMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listFilterIcon.setOnClickListener {
            val popupMenu = PopupMenu(this.requireContext(), binding.listFilterIcon)
            popupMenu.menuInflater.inflate(R.menu.filter_menu,popupMenu.menu)
            popupMenu.show()
        }
        Log.d(TAG, "onViewCreated: before layout manager")
        binding.recyclerViewMovies.layoutManager = GridLayoutManager(requireContext(), 2)
        Log.d(TAG, "onViewCreated: after layout manager")
        Log.d(
            TAG,
            "onViewCreated: calling the function to fetch movie from web"
        )
        viewModel.getMoviesFromWeb()
        Log.d(TAG, "onViewCreated: call completed")
        viewModel.movies.observe(viewLifecycleOwner) { list ->
            binding.recyclerViewMovies.adapter =
                MoviesAdapter(list) { movieId, title, description, image, isActive, action ->
                    when (action) {
                        Utils.GOTODESCRIPTION -> {
                            val navigationAction =
                                MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                                    movieId
                                )
                            findNavController().navigate(navigationAction)
                        }

                        Utils.ADDTOWATCHLIST -> {
                            try {
                                viewModel.addMovieToWatchlist(movieId, title, description, image, isActive)
                                Snackbar.make(binding.root, "Movie added to Watchlist", Snackbar.LENGTH_SHORT).show()
                            }catch (e: Exception){
                                Snackbar.make(binding.root, "Something went wrong: $e", Snackbar.LENGTH_SHORT).show()
                            }
                        }

                        else -> {}          // TODO: yet to be implemented
                    }
                }
        }
    }
}
