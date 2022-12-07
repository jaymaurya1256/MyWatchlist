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
import com.example.mywatchlist.ui.ListOfMoviesByCategories
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
        val nextPage  = {
            viewModel.page.value = viewModel.page.value?.plus(1)
            if (viewModel.listOfMoviesByCategories.topMovies) {viewModel.getMoviesFromWebTopRated()}
            if (viewModel.listOfMoviesByCategories.newMovies) {viewModel.getMoviesFromWebNewReleases()}
            if (viewModel.listOfMoviesByCategories.popularMovies) {viewModel.getMoviesFromWebPopular()}
            if (viewModel.listOfMoviesByCategories.crimeMovies) {viewModel.getMoviesFromWebGenre(80)}
            if (viewModel.listOfMoviesByCategories.dramaMovies) {viewModel.getMoviesFromWebGenre(18)}
            if (viewModel.listOfMoviesByCategories.comedyMovies) {viewModel.getMoviesFromWebGenre(35)}
            if (viewModel.listOfMoviesByCategories.actionMovies) {viewModel.getMoviesFromWebGenre(28)}
            if (viewModel.listOfMoviesByCategories.suspenseMovies) {viewModel.getMoviesFromWebGenre(9648)}
            if (viewModel.listOfMoviesByCategories.thrillerMovies) {viewModel.getMoviesFromWebGenre(53)}
            if (viewModel.listOfMoviesByCategories.horrorMovies) {viewModel.getMoviesFromWebGenre(27)}
        }

        Log.d(TAG, "onViewCreated: Called the function get top rated")
        viewModel.getMoviesFromWebTopRated()
        Log.d(TAG, "onViewCreated: result of top rated ${viewModel.movies.value}")
        binding.recyclerViewMovies.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.listFilterIcon.setOnClickListener {
            val popupMenu = PopupMenu(this.requireContext(), binding.listFilterIcon)
            popupMenu.menuInflater.inflate(R.menu.filter_menu,popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.crime_filter -> {
                        with(viewModel.listOfMoviesByCategories){
                            setEachToFalse()
                            crimeMovies = true
                        }
                        viewModel.page.value = 0
                        viewModel.getMoviesFromWebGenre(80)
                        true
                    }
                    R.id.drama_filter -> {
                        with(viewModel.listOfMoviesByCategories){
                            setEachToFalse()
                            dramaMovies = true
                        }
                        viewModel.page.value = 0
                        viewModel.getMoviesFromWebGenre(18)
                        true
                    }
                    R.id.comedy_filter -> {
                        with(viewModel.listOfMoviesByCategories){
                            setEachToFalse()
                            comedyMovies = true
                        }
                        viewModel.page.value = 0
                        viewModel.getMoviesFromWebGenre(35)
                        true
                    }
                    R.id.action_filter -> {
                        with(viewModel.listOfMoviesByCategories){
                            setEachToFalse()
                            actionMovies = true
                        }
                        viewModel.page.value = 0
                        Log.d(TAG, "onViewCreated: Click detected in action button")
                        viewModel.getMoviesFromWebGenre(28)
                        true
                    }
                    R.id.suspense_filter -> {
                        with(viewModel.listOfMoviesByCategories){
                            setEachToFalse()
                            suspenseMovies = true
                        }
                        viewModel.page.value = 0
                        viewModel.getMoviesFromWebGenre(9648)
                        true
                    }
                    R.id.thriller_filter -> {
                        with(viewModel.listOfMoviesByCategories){
                            setEachToFalse()
                            thrillerMovies = true
                        }
                        viewModel.page.value = 0
                        viewModel.getMoviesFromWebGenre(53)
                        true
                    }
                    R.id.horror_filter -> {
                        with(viewModel.listOfMoviesByCategories){
                            setEachToFalse()
                            horrorMovies = true
                        }
                        viewModel.page.value = 0
                        viewModel.getMoviesFromWebGenre(27)
                        true
                    }
                    R.id.top_filter -> {
                        with(viewModel.listOfMoviesByCategories){
                            setEachToFalse()
                            topMovies = true
                        }
                        viewModel.page.value = 0
                        viewModel.getMoviesFromWebTopRated()
                        true
                    }
                    R.id.new_releases_filter -> {
                        with(viewModel.listOfMoviesByCategories){
                            setEachToFalse()
                            newMovies = true
                        }
                        viewModel.page.value = 0
                        viewModel.getMoviesFromWebNewReleases()
                        true
                    }
                    R.id.popular_filter -> {
                        with(viewModel.listOfMoviesByCategories){
                            setEachToFalse()
                            popularMovies = true
                        }
                        viewModel.page.value = 0
                        viewModel.getMoviesFromWebPopular()
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
                MoviesAdapter(list,nextPage) { movieId, title, description, image, isActive, action ->
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
