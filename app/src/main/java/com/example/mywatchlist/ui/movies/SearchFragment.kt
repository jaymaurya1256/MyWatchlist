package com.example.mywatchlist.ui.movies

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mywatchlist.databinding.FragmentSearchBinding
import com.example.mywatchlist.ui.Filters
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "SearchFragment"

class SearchFragment : BottomSheetDialogFragment() {
    lateinit var binding : FragmentSearchBinding
    private val viewModel: MoviesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(200)
            binding.searchTextField.requestFocus()
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(binding.searchTextField, InputMethodManager.SHOW_FORCED)
        }

        binding.searchMovie.setOnClickListener {
            viewModel.currentFilter = Filters.SEARCH
            val searchText = binding.searchTextField.text.toString().trim()
            if (searchText != ""){
                Log.d(TAG, "onViewCreated: searchText contains keyword -> $searchText")
                viewModel.searchMovies(searchText)
                findNavController().popBackStack()
            }else{
                binding.searchTextField.error = "Enter the movie to search"
            }
        }
    }
}