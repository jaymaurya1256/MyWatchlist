package com.example.mywatchlist.ui.movies

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mywatchlist.R
import com.example.mywatchlist.databinding.FragmentSearchBinding
import com.example.mywatchlist.ui.Filters
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
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
            val viewFocus = requireActivity().currentFocus
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(viewFocus, 0)
        }

        binding.searchMovie.setOnClickListener {
            viewModel.currentFilter = Filters.SEARCH
            val searchText = binding.searchTextField.text.toString().trim()
            if (searchText != ""){
                Log.d(TAG, "onViewCreated: searchText contains keyword -> $searchText")
                val keyword = searchText
                viewModel.searchMovies(keyword)
                findNavController().popBackStack()
            }else{
                binding.searchTextField.error = "Enter the movie to search"
            }
        }
    }
}