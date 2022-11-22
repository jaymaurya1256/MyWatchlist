package com.example.mywatchlist.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mywatchlist.databinding.FragmentMoviesBinding
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
        Log.d(TAG, "onViewCreated: before layout manager")
        binding.recyclerViewMovies.layoutManager = GridLayoutManager(requireContext(), 2)
        Log.d(TAG, "onViewCreated: after layout manager")
        Log.d(
            TAG,
            "onViewCreated: calling the function to featch movie from web"
        )
        viewModel.getMoviesFromWeb()
        Log.d(TAG, "onViewCreated: call completed")
        viewModel.movies.observe(viewLifecycleOwner) { list ->
            binding.recyclerViewMovies.adapter =
                MoviesAdapter(list) { movieId, title, description, image, action ->
                    when (action) {
                        "GoToDescription" -> {
                            val navigationAction =
                                MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                                    movieId
                                )
                            findNavController().navigate(navigationAction)
                        }

                        "AddToWatchlist" -> {
                            viewModel.addMovieToWatchlist(movieId, title, description, image)
                        }

                        else -> {// TODO: yet to be implemented }
                        }
                    }
                }
        }
    }
}