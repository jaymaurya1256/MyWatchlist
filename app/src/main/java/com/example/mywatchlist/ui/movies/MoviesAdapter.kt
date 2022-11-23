package com.example.mywatchlist.ui.movies

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mywatchlist.R
import com.example.mywatchlist.databinding.ListItemMoviesBinding
import com.example.mywatchlist.network.entity.movieslist.Movie
import com.example.mywatchlist.ui.Utils

private const val TAG = "MoviesAdapter"


class MoviesAdapter(private val listOfMovies: List<Movie>, val onClick: (Int, String, String, String, Boolean, Utils) -> Unit): RecyclerView.Adapter<MoviesAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ListItemMoviesBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.movieName
        val summary = binding.description
        val image = binding.image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemMoviesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val movie = listOfMovies[position]
        with(holder.binding) {
            image.load(BASE_URL_FOR_IMAGE+movie.poster_path){
                placeholder(R.drawable.place_holder_image)
                crossfade(true)
                crossfade(1000)
            }
            movieName.text = movie.title
            description.text = movie.overview
        }
        holder.binding.root.setOnClickListener {
            Log.d(TAG, "onBindViewHolder: onclick called")
            onClick(movie.id,"","","",false,Utils.GOTODESCRIPTION)
            Log.d(TAG, "onBindViewHolder: onclick completed")
        }
        holder.binding.root.setOnLongClickListener {
            Log.d(TAG, "onBindViewHolder: long click detected")
            val popupMenu = PopupMenu(it.context, it)
            popupMenu.menuInflater.inflate(R.menu.menu_movie_fragment, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.addToWatchlistMenuItem -> {
                        onClick(movie.id, movie.title, movie.overview, movie.poster_path,movie.adult, Utils.ADDTOWATCHLIST)
                        true
                    }
                    R.id.goToDescriptionMenuItem -> {
                        onClick(movie.id, movie.title, movie.overview, movie.poster_path,movie.adult, Utils.GOTODESCRIPTION)
                        true
                    }
                    R.id.visitWebMenuItem -> true
                    else -> {true}
                }
            }
            true
        }

    }

    override fun getItemCount(): Int {
        return listOfMovies.size
    }
}