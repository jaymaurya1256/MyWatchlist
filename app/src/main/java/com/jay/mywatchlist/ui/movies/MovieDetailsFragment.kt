package com.jay.mywatchlist.ui.movies

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.jay.mywatchlist.R
import com.jay.mywatchlist.databinding.FragmentMovieDetailsBinding
import com.jay.mywatchlist.network.entity.moviedetails.MoviesDetails
import com.jay.mywatchlist.util.createCoilImageLoader
import com.jay.mywatchlist.util.shortSnackbar
import com.jay.mywatchlist.util.toImageUrl
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.getMovieDetails(args.movieId)
        viewModel.movieDetails.observe(viewLifecycleOwner) { movieDetails ->
            if (movieDetails != null) {
                setData(movieDetails)
            }

            binding.fabAddToWatchlist.setOnClickListener {
                viewModel.switchWatchlistStatus(movieDetails)
            }
        }

        viewModel.getMovieFromDB(args.movieId).observe(viewLifecycleOwner) { movieInDb ->
            if (movieInDb == null) {
                binding.fabAddToWatchlist.setImageResource(R.drawable.ic_baseline_playlist_add_24)
            } else {
                binding.fabAddToWatchlist.setImageResource(R.drawable.baseline_playlist_remove_24)
            }
        }

        viewModel.message.observe(viewLifecycleOwner) {
            binding.root.shortSnackbar(it)
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })
    }

    private fun setData(movieDetails: MoviesDetails) {
        with(binding) {
            title.text = movieDetails.title
            tagLine.text = movieDetails.tagline
            castRecyclerView.layoutManager =
                GridLayoutManager(requireContext(), 1, RecyclerView.HORIZONTAL, false)
            viewModel.castList.observe(viewLifecycleOwner) {
                castRecyclerView.adapter = it.cast?.let { it1 -> CastAdapter(it1.take(10), requireContext().applicationContext) }
            }
            audienceRating.text =
                getString(R.string.rating) + ": " + movieDetails.vote_average.toString().take(4)
            releaseDate.text = "Released in: " + (movieDetails.release_date?.dropLast(6)
                ?: movieDetails.release_date)
            originalLang.text = "Language: " + movieDetails.original_language
            scrollableDescriptionText.text = movieDetails.overview
            var nameOfProductionCompany = "Produced By: "
            val listOfStudios = movieDetails.production_companies
            if (listOfStudios != null) {
                for (studio in listOfStudios) {
                    nameOfProductionCompany += studio.name + "* "
                }
            } else {
                nameOfProductionCompany += "Unknown"
            }
            studioName.text = nameOfProductionCompany
            val imageLoader = createCoilImageLoader(requireContext().applicationContext)
            movieImage.load(movieDetails.poster_path?.toImageUrl(), imageLoader) {
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
        }
    }
}