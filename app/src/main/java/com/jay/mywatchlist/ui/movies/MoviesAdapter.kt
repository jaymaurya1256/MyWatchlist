package com.jay.mywatchlist.ui.movies

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.jay.mywatchlist.R
import com.jay.mywatchlist.databinding.ListItemMoviesBinding
import com.jay.mywatchlist.network.entity.movieslist.Movie
import com.jay.mywatchlist.util.Actions
import com.jay.mywatchlist.util.createCoilImageLoader
import com.jay.mywatchlist.util.toImageUrl

private const val TAG = "MoviesAdapter"


class MoviesDiff : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}


class MoviesAdapter(
    val context: Context,
    val nextPage: () -> Unit,
    val onClick: (Int, String, String, String, Boolean, Actions) -> Unit
) : ListAdapter<Movie, MoviesAdapter.ItemViewHolder>(MoviesDiff()) {
    class ItemViewHolder(val binding: ListItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.movieName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemMoviesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val movie = getItem(position)
        if (position > currentList.size - 6) {
            nextPage()
        }
        val imageLoader = createCoilImageLoader(context)
        with(holder.binding) {
            image.load(movie.poster_path?.toImageUrl(), imageLoader) {
                placeholder(CircularProgressDrawable(holder.binding.root.context).apply {
                    strokeWidth = 5f
                    centerRadius = 30f
                    start()
                })
                crossfade(true)
                crossfade(1000)
                error(R.drawable.image_load_error)
            }
            rating.text = movie.vote_average.toString().take(3)
            movieName.text = movie.title
            description.text = movie.overview
            moreOption.setOnClickListener {
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

                        R.id.visitWebMenuItem -> {
                            onClick(
                                movie.id,
                                movie.title!!,
                                movie.overview!!,
                                movie.poster_path!!,
                                movie.adult!!,
                                Actions.VISIT_WEB
                            )
                            true
                        }

                        else -> {true}
                    }
                }
            }
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

                    R.id.visitWebMenuItem -> {
                        onClick(
                            movie.id,
                            movie.title!!,
                            movie.overview!!,
                            movie.poster_path!!,
                            movie.adult!!,
                            Actions.VISIT_WEB
                        )
                        true
                    }

                    else -> {
                        true
                    }
                }
            }
            true
        }

    }

}