package com.example.mywatchlist.ui.movies

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.OnBackPressedCallback
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mywatchlist.R
import com.example.mywatchlist.databinding.FragmentMoviesBinding
import com.example.mywatchlist.ui.Actions
import com.example.mywatchlist.ui.Filters
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

private const val TAG = "MoviesFragment"

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private val viewModel: MoviesViewModel by activityViewModels()
    private lateinit var binding: FragmentMoviesBinding
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (viewModel.currentFilter != Filters.POPULAR){
                viewModel.currentFilter = Filters.POPULAR
            }else{
                requireActivity().finish()
                exitProcess(0)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewMovies.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = MoviesAdapter( nextPage = { viewModel.getMoreMovies() }) { movieId, title, description, image, isActive, action ->
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

                Actions.VISIT_WEB -> {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/movie/$movieId"))
                        startActivity(intent)
                    }catch (e: Exception){
                        Log.d(TAG, "onViewCreated: e")
                    }
                }
                else -> {}
            }
        }

        binding.recyclerViewMovies.adapter = adapter

        binding.listFilterIcon.setOnClickListener {
            val popupMenu = PopupMenu(this.requireContext(), binding.listFilterIcon)
            popupMenu.menuInflater.inflate(R.menu.filter_menu, popupMenu.menu)
            when(viewModel.currentFilter){
                Filters.POPULAR ->{popupMenu.menu.findItem(R.id.popular_filter).isChecked = true}
                Filters.NEW_RELEASES ->{popupMenu.menu.findItem(R.id.new_releases_filter).isChecked = true}
                Filters.TOP_RATED ->{popupMenu.menu.findItem(R.id.top_filter).isChecked = true}
                Filters.CRIME ->{popupMenu.menu.findItem(R.id.crime_filter).isChecked = true}
                Filters.DRAMA ->{ popupMenu.menu.findItem(R.id.drama_filter).isChecked = true }
                Filters.COMEDY ->{popupMenu.menu.findItem(R.id.comedy_filter).isChecked = true}
                Filters.ACTION ->{popupMenu.menu.findItem(R.id.action_filter).isChecked = true}
                Filters.SUSPENSE ->{popupMenu.menu.findItem(R.id.suspense_filter).isChecked = true}
                Filters.THRILLER ->{popupMenu.menu.findItem(R.id.thriller_filter).isChecked = true}
                Filters.HORROR ->{popupMenu.menu.findItem(R.id.horror_filter).isChecked = true}
            }
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.popular_filter -> {
                        it.isChecked = true
                        viewModel.currentFilter = Filters.POPULAR
                        true
                    }
                    R.id.top_filter -> {
                        it.isChecked = true
                        viewModel.currentFilter = Filters.TOP_RATED
                        true
                    }
                    R.id.new_releases_filter -> {
                        it.isChecked = true
                        viewModel.currentFilter = Filters.NEW_RELEASES
                        true
                    }
                    R.id.crime_filter -> {
                        it.isChecked = true
                        viewModel.currentFilter = Filters.CRIME
                        true
                    }
                    R.id.comedy_filter -> {
                        it.isChecked = true
                        viewModel.currentFilter = Filters.COMEDY
                        true
                    }
                    R.id.action_filter -> {
                        it.isChecked = true
                        viewModel.currentFilter = Filters.ACTION
                        true
                    }
                    R.id.drama_filter -> {
                        it.isChecked = true
                        viewModel.currentFilter = Filters.DRAMA
                        true
                    }
                    R.id.suspense_filter -> {
                        it.isChecked = true
                        viewModel.currentFilter = Filters.SUSPENSE
                        true
                    }
                    R.id.thriller_filter -> {
                        it.isChecked = true
                        viewModel.currentFilter = Filters.THRILLER
                        true
                    }
                    R.id.horror_filter -> {
                        it.isChecked = true
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
            if (list.size == 0){
                binding.lottieNoResult.visibility = View.VISIBLE
                binding.recyclerViewMovies.visibility = View.GONE
            }else{
                adapter.submitList(list)
                binding.lottieNoResult.visibility = View.GONE
                binding.recyclerViewMovies.visibility = View.VISIBLE
            }
        }
    }
}

