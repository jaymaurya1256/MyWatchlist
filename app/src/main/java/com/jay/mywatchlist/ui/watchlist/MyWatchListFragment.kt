package com.jay.mywatchlist.ui.watchlist

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.jay.mywatchlist.R
import com.jay.mywatchlist.database.WatchlistTable
import com.jay.mywatchlist.databinding.FragmentWatchlistBinding
import com.jay.mywatchlist.util.Actions
import com.google.android.material.snackbar.Snackbar
import com.jay.mywatchlist.util.adUnitId
import com.jay.mywatchlist.util.startAds
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MyWatchListFragment"
@AndroidEntryPoint
class MyWatchListFragment : Fragment() {
    private lateinit var binding: FragmentWatchlistBinding
    private val viewModel : WatchlistViewModel by viewModels()
    private val adRequest = AdRequest.Builder().build()


    // Get the ad size with screen width.
    private val adSize: AdSize
        get() {
            val displayMetrics = resources.displayMetrics
            val adWidthPixels =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val windowMetrics: WindowMetrics = requireActivity().windowManager.currentWindowMetrics
                    windowMetrics.bounds.width()
                } else {
                    displayMetrics.widthPixels
                }
            val density = displayMetrics.density
            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(requireActivity(), adWidth)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWatchlistBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create a new ad view.
        val adView = AdView(requireContext())
        adView.adUnitId = adUnitId
        adView.setAdSize(adSize)

        binding.adView.removeAllViews()
        binding.adView.addView(adView)

        adView.loadAd(adRequest)


        binding.recyclerViewWatchlist.layoutManager = GridLayoutManager(requireContext(), 1)
        viewModel.watchlist.observe(viewLifecycleOwner){
            if (it == emptyList<WatchlistTable>()) {
                binding.lottieNoResult.visibility = View.VISIBLE
                Log.d(TAG, "onViewCreated: list is empty")
            }else{
                binding.lottieNoResult.visibility = View.GONE
            }
            binding.recyclerViewWatchlist.adapter = WatchlistAdapter(it){ movieId, action ->
                when(action){
                    Actions.REMOVE -> {
                        viewModel.removeFromList(movieId)
                        Snackbar.make(binding.root, "Movie removed from Watchlist", Snackbar.LENGTH_SHORT).show()
                    }
                    Actions.GO_TO_DESCRIPTION -> startAds(requireContext(), requireActivity()) {
                        val navigationAction = MyWatchListFragmentDirections
                                .actionMyWatchListFragmentToMovieDetailsFragment(movieId)
                        findNavController().navigate(navigationAction)
                    }

                    Actions.ADD_TO_WATCHLIST -> {}
                    Actions.VISIT_WEB -> {}
                }
            }
            binding.clearWatchlist.setOnClickListener {
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.setMessage(R.string.clear_all)
                alertDialog.setCancelable(false)
                alertDialog.setPositiveButton(android.R.string.yes){ _,_ ->
                    viewModel.clearAll()
                }
                alertDialog.setNegativeButton(android.R.string.cancel){dialog,_ ->
                    dialog.cancel()
                }
                alertDialog.show()
            }
        }
    }
}