package com.example.mywatchlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mywatchlist.R
import com.example.mywatchlist.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class ItemAdapterForMovies: RecyclerView.Adapter<ItemAdapterForMovies.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView = itemView.findViewById<ImageView>(R.id.image)
        val movieName = itemView.findViewById<TextView>(R.id.movieName)
        val movieDescription = itemView.findViewById<TextView>(R.id.other)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val holder = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent,false)
        return ItemViewHolder(holder)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.imageView.setImageResource(R.drawable.place_holder_image)
        holder.movieName.text = "Movie Name"
        holder.movieDescription.text = "this is the movie description for the selected movie thank you!"
    }

    override fun getItemCount(): Int {
        return 20
    }
}