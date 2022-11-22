package com.example.mywatchlist.ui.watchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.mywatchlist.R
import com.example.mywatchlist.database.WatchlistTable
import com.example.mywatchlist.databinding.ListItemWatchlistBinding
import com.example.mywatchlist.ui.Utils

class WatchlistAdapter(private val watchListTables: List<WatchlistTable>, private val onClick: (Int, Utils) -> Unit): RecyclerView.Adapter<WatchlistAdapter.ItemViewHolderWatchlist>() {
    class ItemViewHolderWatchlist(val binding: ListItemWatchlistBinding): RecyclerView.ViewHolder(binding.root){
        val title = binding.movieNameWatchlist
        val detail = binding.descriptionWatchlist
        val image = binding.imageWatchlist
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolderWatchlist {
        val binding = ListItemWatchlistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return ItemViewHolderWatchlist(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolderWatchlist, position: Int) {
        val movie =  watchListTables[position]
        with(holder.binding){
            movieNameWatchlist.text = movie.title
            descriptionWatchlist.text = movie.description
            imageWatchlist.setImageResource(R.drawable.place_holder_image)
        }
        holder.binding.root.setOnLongClickListener {
            val popupMenu = PopupMenu(holder.binding.root.context, holder.binding.root)
            popupMenu.menuInflater.inflate(R.menu.menu_watch_list, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.removeFromListMenuItem -> {
                        onClick(movie.id, Utils.REMOVE)
                        true
                    }
                    R.id.goToDescriptionFromWatchlistMenuItem -> {
                        onClick(movie.id, Utils.GOTODESCRIPTION)
                        true
                    }
                    else -> true
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return watchListTables.size
    }

}