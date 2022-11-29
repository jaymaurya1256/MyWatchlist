package com.example.mywatchlist.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
    private val viewModel: MoviesViewModel by activityViewModels()
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
        viewModel.getMoviesFromWeb()
        binding.recyclerViewMovies.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.listFilterIcon.setOnClickListener {
            val popupMenu = PopupMenu(this.requireContext(), binding.listFilterIcon)
            popupMenu.menuInflater.inflate(R.menu.filter_menu,popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.crime_filter -> {
                        viewModel.getMoviesFromWebGenre(80)
                        true
                    }
                    R.id.drama_filter -> {
                        viewModel.getMoviesFromWebGenre(18)
                        true
                    }
                    R.id.comedy_filter -> {
                        viewModel.getMoviesFromWebGenre(35)
                        true
                    }
                    R.id.action_filter -> {
                        Log.d(TAG, "onViewCreated: Click detected in action button")
                        viewModel.getMoviesFromWebGenre(28)
                        true
                    }
                    R.id.suspense_filter -> {
                        viewModel.getMoviesFromWebGenre(9648)
                        true
                    }
                    R.id.thriller_filter -> {
                        viewModel.getMoviesFromWebGenre(53)
                        true
                    }
                    R.id.horror_filter -> {
                        viewModel.getMoviesFromWebGenre(27)
                        true
                    }
                    R.id.top_filter -> {
                        viewModel.getMoviesFromWeb()
                        true
                    }
                    else -> {true}
                }
            }
        }
        binding.searchMovies.setOnClickListener {
            val action = MoviesFragmentDirections.actionMoviesFragmentToSearchFragment()
            findNavController().navigate(action)
        }

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
