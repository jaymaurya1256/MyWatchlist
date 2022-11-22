package com.example.mywatchlist.ui.movies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mywatchlist.R
import com.example.mywatchlist.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MovieDetailsFragment"
@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var binding: FragmentMovieDetailsBinding
    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: ${args.movieId}")
        viewModel.getMovieDetails(args.movieId)
        Log.d(TAG, "onViewCreated: function is called")
        with(binding){

            viewModel.requestedMovie.observe(viewLifecycleOwner) { movieDetails ->

                Log.d(TAG, "onViewCreated: $movieDetails")
                if (movieDetails != null) {
                    title.text = movieDetails.title
                    movieImage.setImageResource(R.drawable.place_holder_image)
                    scrollableDescriptionText.text = movieDetails.overview

                    visitWebFragmentDetail.setOnClickListener {} // TODO: add functionality for web visit

                    addToWatchlistFragmentDetail.setOnClickListener {
                        viewModel.addToWatchlist(
                            movieDetails.id,
                            movieDetails.title,
                            movieDetails.overview,
                            movieDetails.poster_path
                        )
                    }
                }
            }
        }


        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }
}