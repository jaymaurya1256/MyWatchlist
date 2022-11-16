package com.example.mywatchlist.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mywatchlist.R
import com.example.mywatchlist.database.WatchlistTable
import com.example.mywatchlist.databinding.ListItemMoviesBinding
import com.example.mywatchlist.databinding.ListItemWatchlistBinding

class WatchlistAdapter(val watchListTables: List<WatchlistTable>): RecyclerView.Adapter<WatchlistAdapter.ItemViewHolderWatchlist>() {
    class ItemViewHolderWatchlist(val binding: ListItemWatchlistBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolderWatchlist {
        val binding = ListItemWatchlistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return ItemViewHolderWatchlist(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolderWatchlist, position: Int) {
        with(holder.binding){
            movieNameWatchlist.text = watchListTables[position].title
            descriptionWatchlist.text = watchListTables[position].description
            imageWatchlist.setImageResource(R.drawable.place_holder_image)
        }
    }

    override fun getItemCount(): Int {
        return watchListTables.size
    }

}