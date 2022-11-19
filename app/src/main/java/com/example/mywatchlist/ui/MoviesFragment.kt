package com.example.mywatchlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mywatchlist.ui.adapters.MoviesAdapter
import com.example.mywatchlist.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MoviesFragment"
@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private val viewModel : MoviesViewModel by viewModels()
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
        binding.recyclerViewMovies.layoutManager = GridLayoutManager(requireContext(),2)
        viewModel.movies.observe(viewLifecycleOwner) { list ->
            binding.recyclerViewMovies.adapter = MoviesAdapter(list){
                val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it)
                findNavController().navigate(action)
            }
        }
        viewModel.getMoviesFromWeb()
    }
}