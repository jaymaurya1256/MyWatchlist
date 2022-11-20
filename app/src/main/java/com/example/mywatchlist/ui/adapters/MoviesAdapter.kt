package com.example.mywatchlist.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mywatchlist.R
import com.example.mywatchlist.databinding.ListItemMoviesBinding
import com.example.mywatchlist.network.entity.Movie

class MoviesAdapter(private val listOfMovies: List<Movie>, val onClick: (Int) -> Unit): RecyclerView.Adapter<MoviesAdapter.ItemViewHolder>() {
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
            image.setImageResource(R.drawable.place_holder_image) //TODO : need to use coil here
            movieName.text = movie.title
            description.text = movie.overview
        }
        holder.binding.root.setOnClickListener {
            onClick(movie.id)
        }
        holder.binding.root.setOnLongClickListener {
            val popupMenu = PopupMenu(it.context, it)
            popupMenu.menuInflater.inflate(R.menu.menu_movie_fragment, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.addToWatchlistMenuItem -> {

                        true
                    }
                    R.id.goToDescriptionMenuItem -> {
                        onClick(movie.id)
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