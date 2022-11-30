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
import coil.load
import com.example.mywatchlist.R
import com.example.mywatchlist.databinding.FragmentMovieDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MovieDetailsFragment"
const val BASE_URL_FOR_IMAGE = "https://www.themoviedb.org/t/p/original"

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
                    scrollableDescriptionText.text = movieDetails.overview
                    Log.d(TAG, "onViewCreated: MoviePosterURL is -> ${BASE_URL_FOR_IMAGE+movieDetails.poster_path}")
                    movieImage.load(BASE_URL_FOR_IMAGE+movieDetails.poster_path){
                        crossfade(true)
                        crossfade(1000)
                        placeholder(R.drawable.place_holder_image)
                        error(R.drawable.image_load_error)
                    }

                    visitWebFragmentDetail.setOnClickListener {} // TODO: add functionality for web visit

                    addToWatchlistFragmentDetail.setOnClickListener {
                        viewModel.addToWatchlist(
                            movieDetails.id,
                            movieDetails.title,
                            movieDetails.overview,
                            movieDetails.poster_path,
                            movieDetails.adult
                        )
                        Snackbar.make(binding.root, "Movie added to Watchlist", Snackbar.LENGTH_SHORT).show()
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