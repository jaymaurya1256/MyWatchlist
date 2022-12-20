package com.example.mywatchlist.ui.movies

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.example.mywatchlist.R
import com.example.mywatchlist.database.WatchlistTable
import com.example.mywatchlist.databinding.FragmentMovieDetailsBinding
import com.example.mywatchlist.util.toImageUrl
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.properties.Delegates

private const val TAG = "MovieDetailsFragment"

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var binding: FragmentMovieDetailsBinding
    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ${args.movieId}")
        viewModel.getMovieDetails(args.movieId)
        Log.d(TAG, "onViewCreated: function is called")
        with(binding) {

            viewModel.requestedMovie.observe(viewLifecycleOwner) { movieDetails ->

                Log.d(TAG, "onViewCreated: $movieDetails")
                if (movieDetails != null) {
                    title.text = movieDetails.title
                    tagLine.text = movieDetails.tagline
                    viewModel.getCast(movieDetails.id)
                    castRecyclerView.layoutManager =
                        GridLayoutManager(requireContext(), 1, RecyclerView.HORIZONTAL, false)
                    viewModel.castList.observe(viewLifecycleOwner) {
                        castRecyclerView.adapter = it.cast?.let { it1 -> CastAdapter(it1.take(10)) }
                    }
                    audienceRating.text =
                        getString(R.string.rating) + ": " + movieDetails.vote_average.toString()
                            .take(4)
                    releaseDate.text = "Released in: " + (movieDetails.release_date?.dropLast(6)
                        ?: movieDetails.release_date)
                    originalLang.text = "Language: " + movieDetails.original_language
                    scrollableDescriptionText.text = movieDetails.overview
                    var nameOfProductionCompany = "Produced By: "
                    val listOfStudios = movieDetails.production_companies
                    if (listOfStudios != null) {
                        for (i in listOfStudios) {
                            nameOfProductionCompany += i.name + "* "
                        }
                    } else {
                        nameOfProductionCompany += "Unknown"
                    }
                    studioName.text = nameOfProductionCompany
                    Log.d(
                        TAG,
                        "onViewCreated: MoviePosterURL is -> ${movieDetails.poster_path?.toImageUrl()}"
                    )
                    movieImage.load(movieDetails.poster_path?.toImageUrl()) {
                        crossfade(true)
                        crossfade(1000)
                        placeholder(CircularProgressDrawable(requireContext()).apply {
                            strokeWidth = 5f
                            centerRadius = 30f
                            start()
                        })
                        error(R.drawable.image_load_error)
                    }

                    visitWebFragmentDetail.setOnClickListener {
                        try {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.themoviedb.org/movie/${movieDetails.id}")
                            )
                            startActivity(intent)
                        } catch (e: Exception) {
                            Log.d(TAG, "onViewCreated: e")
                        }
                    }

                    addToWatchlistFragmentDetail.setOnClickListener {

                        if (movieDetails.id in viewModel.listOfAllIdOfSavedMovies.value!!) {
                            Log.d(TAG, "onViewCreated: Entered the if segment")
                            viewModel.removeFromWatchlist(movieDetails.id)
                            Log.d(TAG, "onViewCreated: movie removed from watchlist")
                            addToWatchlistFragmentDetail.setImageResource(R.drawable.ic_baseline_playlist_add_24)
                            Log.d(TAG, "onViewCreated: icon changed")
                            Snackbar.make(
                                binding.root,
                                "Movie removed from Watchlist",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.d(TAG, "onViewCreated: entered else part")
                            try {
                                Log.d(TAG, "onViewCreated: entered else - try part")
                                viewModel.addToWatchlist(
                                    movieDetails.id,
                                    movieDetails.title,
                                    movieDetails.overview,
                                    movieDetails.poster_path!!,
                                    movieDetails.adult
                                )
                                Log.d(TAG, "onViewCreated: movie added")
                                addToWatchlistFragmentDetail.setImageResource(R.drawable.ic_baseline_playlist_add_check_24)
                                Log.d(TAG, "onViewCreated: icon changed")
                                Snackbar.make(
                                    binding.root,
                                    "Movie added to Watchlist",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            } catch (e: Exception) {
                                Log.d(TAG, "onViewCreated: failed to add to watchlist")
                                Snackbar.make(
                                    binding.root,
                                    "Something went wrong (movie cannot be added to watchlist due to insufficient data)",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }


        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })
    }
}