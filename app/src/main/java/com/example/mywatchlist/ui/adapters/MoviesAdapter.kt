package com.example.mywatchlist.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mywatchlist.R
import com.example.mywatchlist.databinding.ListItemMoviesBinding
import com.example.mywatchlist.network.entity.Movie

class MoviesAdapter(private val listOfMovies: List<Movie>): RecyclerView.Adapter<MoviesAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ListItemMoviesBinding) : RecyclerView.ViewHolder(binding.root){
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
    }

    override fun getItemCount(): Int {
        return listOfMovies.size
    }
}