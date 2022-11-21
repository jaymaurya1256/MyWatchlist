package com.example.mywatchlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mywatchlist.R
import com.example.mywatchlist.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private val viewModel: MovieDetailViewModel by viewModels()
    private val binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieDetails = viewModel.getDetailOfMovie(args.movieId)

        with(binding){
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

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }
}