package com.example.mywatchlist.ui.movies

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.example.mywatchlist.R
import com.example.mywatchlist.databinding.ListItemMoviesBinding
import com.example.mywatchlist.network.entity.movieslist.Movie
import com.example.mywatchlist.ui.Actions

private const val TAG = "MoviesAdapter"


class MoviesAdapter(
    private val listOfMovies: List<Movie>,
    val nextPage: () -> Unit,
    val onClick: (Int, String, String, String, Boolean, Actions) -> Unit,
) : RecyclerView.Adapter<MoviesAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ListItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.movieName
        val summary = binding.description
        val image = binding.image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemMoviesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val movie = listOfMovies[position]
        if (position > listOfMovies.size - 6) {
            nextPage()
        }
        with(holder.binding) {
            image.load(BASE_URL_FOR_IMAGE + movie.poster_path) {
                placeholder(CircularProgressDrawable(holder.binding.root.context).apply {
                    strokeWidth = 5f
                    centerRadius = 30f
                    start()
                })
                crossfade(true)
                crossfade(1000)
                error(R.drawable.image_load_error)
            }
            movieName.text = movie.title
            description.text = movie.overview
        }
        holder.binding.root.setOnClickListener {
            Log.d(TAG, "onBindViewHolder: onclick called")
            onClick(movie.id, "", "", "", false, Actions.GO_TO_DESCRIPTION)
            Log.d(TAG, "onBindViewHolder: onclick completed")
        }
        holder.binding.root.setOnLongClickListener {
            Log.d(TAG, "onBindViewHolder: long click detected")
            val popupMenu = PopupMenu(it.context, it)
            popupMenu.menuInflater.inflate(R.menu.menu_movie_fragment, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.addToWatchlistMenuItem -> {
                        onClick(
                            movie.id,
                            movie.title!!,
                            movie.overview!!,
                            movie.poster_path!!,
                            movie.adult!!,
                            Actions.ADD_TO_WATCHLIST
                        )
                        true
                    }

                    R.id.goToDescriptionMenuItem -> {
                        onClick(
                            movie.id,
                            movie.title!!,
                            movie.overview!!,
                            movie.poster_path!!,
                            movie.adult!!,
                            Actions.GO_TO_DESCRIPTION
                        )
                        true
                    }

                    R.id.visitWebMenuItem -> true
                    else -> {
                        true
                    }
                }
            }
            true
        }

    }

    override fun getItemCount(): Int {
        return listOfMovies.size
    }
}