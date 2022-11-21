package com.example.mywatchlist.ui.movies

import android.os.Bundle
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
import com.example.mywatchlist.databinding.FragmentMoviesContainerBinding
import dagger.hilt.android.AndroidEntryPoint

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

        with(binding){

            viewModel.movie.observe(viewLifecycleOwner) { movieDetails ->

                if (movieDetails != null) {
                    description.text = movieDetails.title
                    movieImage.setImageResource(R.drawable.place_holder_image)
                    scrollableDescriptionText.text = movieDetails.description

                    visitWebFragmentDetail.setOnClickListener {} // TODO: add functionality for web visit

                    addToWatchlistFragmentDetail.setOnClickListener {
                        viewModel.addToWatchlist(
                            movieDetails.id,
                            movieDetails.title,
                            movieDetails.description,
                            movieDetails.image
                        )
                    }
                }

            }
        }

        viewModel.getDetailOfMovie(args.movieId)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }
}