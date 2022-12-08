package com.example.mywatchlist.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mywatchlist.R
import com.example.mywatchlist.databinding.FragmentMoviesBinding
import com.example.mywatchlist.ui.Actions
import com.example.mywatchlist.ui.Filters
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MoviesFragment"

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private val viewModel: MoviesViewModel by activityViewModels()
    private lateinit var binding: FragmentMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewMovies.layoutManager = GridLayoutManager(requireContext(), 2)

        val adapter = MoviesAdapter(nextPage = { viewModel.getMoreMovies() }) { movieId, title, description, image, isActive, action ->
            when (action) {
                Actions.GO_TO_DESCRIPTION -> {
                    val navigationAction =
                        MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                            movieId
                        )
                    findNavController().navigate(navigationAction)
                }

                Actions.ADD_TO_WATCHLIST -> {
                    try {
                        viewModel.addMovieToWatchlist(
                            movieId,
                            title,
                            description,
                            image,
                            isActive
                        )
                        Snackbar.make(
                            binding.root,
                            "Movie added to Watchlist",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Snackbar.make(
                            binding.root,
                            "Something went wrong: $e",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

                else -> {
                    // TODO: yet to be implemented
                }
            }
        }

        binding.recyclerViewMovies.adapter = adapter

        binding.listFilterIcon.setOnClickListener {
            val popupMenu = PopupMenu(this.requireContext(), binding.listFilterIcon)
            popupMenu.menuInflater.inflate(R.menu.filter_menu, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.popular_filter -> {
                        viewModel.currentFilter = Filters.POPULAR
                        true
                    }
                    R.id.top_filter -> {
                        viewModel.currentFilter = Filters.TOP_RATED
                        true
                    }
                    R.id.new_releases_filter -> {
                        viewModel.currentFilter = Filters.NEW_RELEASES
                        true
                    }
                    R.id.crime_filter -> {
                        viewModel.currentFilter = Filters.CRIME
                        true
                    }
                    R.id.comedy_filter -> {
                        viewModel.currentFilter = Filters.COMEDY
                        true
                    }
                    R.id.action_filter -> {
                        viewModel.currentFilter = Filters.ACTION
                        true
                    }
                    R.id.drama_filter -> {
                        viewModel.currentFilter = Filters.DRAMA
                        true
                    }
                    R.id.suspense_filter -> {
                        viewModel.currentFilter = Filters.SUSPENSE
                        true
                    }
                    R.id.thriller_filter -> {
                        viewModel.currentFilter = Filters.THRILLER
                        true
                    }
                    R.id.horror_filter -> {
                        viewModel.currentFilter = Filters.HORROR
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
        }

        binding.searchMovies.setOnClickListener {
            val action = MoviesFragmentDirections.actionMoviesFragmentToSearchFragment()
            findNavController().navigate(action)
        }

        viewModel.movies.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }
}
